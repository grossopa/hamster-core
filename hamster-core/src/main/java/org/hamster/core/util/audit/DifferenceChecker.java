/**
 * 
 */
package org.hamster.core.util.audit;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.util.audit.auditors.BooleanDifferenceComparator;
import org.hamster.core.util.audit.auditors.NumberDifferenceComparator;
import org.hamster.core.util.audit.auditors.ObjectDifferenceComparator;
import org.hamster.core.util.audit.auditors.StringDifferenceComparator;
import org.hamster.core.util.audit.auditors.base.DifferenceComparator;
import org.hamster.core.util.audit.model.DifferenceCollVO;
import org.hamster.core.util.audit.model.DifferenceObjectVO;
import org.hamster.core.util.audit.model.DifferenceType;
import org.hamster.core.util.audit.model.DifferenceVO;
import org.hibernate.Hibernate;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.Data;

/**
 * find differences between two objects/collections
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DifferenceChecker {

    public static final String ID = "id";

    private Config config;

    public DifferenceChecker(Config config) {
        this.config = config;
    }

    public <T> DifferenceCollVO check(Collection<T> leftColl, Collection<T> rightColl) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DifferenceCollVO collVO = new DifferenceCollVO();
        return doCheck(collVO, leftColl, rightColl);
    }

    protected <T> DifferenceCollVO doCheck(DifferenceCollVO result, Iterable<T> leftColl, Iterable<T> rightColl) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        String idProperty = StringUtils.defaultString(config.getIdProperty(), ID);

        // avoid triggering the lazy loading
        if (!Hibernate.isInitialized(leftColl) || !Hibernate.isInitialized(rightColl)) {
            return result;
        }

        // both are empty
        if (Iterables.isEmpty(leftColl) && Iterables.isEmpty(rightColl)) {
            return result;
        }

        T sample = Iterables.isEmpty(leftColl) ? rightColl.iterator().next() : leftColl.iterator().next();

        // here we try to get a class from object of a presence bag so anyway the object is initialized
        Set<String> properties = Sets.newHashSet(config.getProperties());
        if (!config.isExclude()) {
            properties.add(idProperty);
        }
        Map<String, Method> getters = ReflectUtils.findGetterMethods(Hibernate.getClass(sample), config.isExclude(), properties.toArray(new String[] {}));

        result.getProperties().clear();
        result.getProperties().addAll(getters.keySet());

        Map<Object, T> leftMap = ReflectUtils.toMap(leftColl, getters.get(idProperty));
        Map<Object, T> rightMap = ReflectUtils.toMap(rightColl, getters.get(idProperty));

        // removed record
        for (Map.Entry<Object, T> leftEntry : leftMap.entrySet()) {
            if (!rightMap.containsKey(leftEntry.getKey())) {
                result.getObjectList().add(doDifferenceOnObject(leftEntry.getValue(), null, getters));
            }
        }

        // added record
        for (Map.Entry<Object, T> rightEntry : rightMap.entrySet()) {
            if (!leftMap.containsKey(rightEntry.getKey())) {
                result.getObjectList().add(doDifferenceOnObject(null, rightEntry.getValue(), getters));
            }
        }

        // changed record
        for (Map.Entry<Object, T> leftEntry : leftMap.entrySet()) {
            if (rightMap.containsKey(leftEntry.getKey())) {
                DifferenceObjectVO objectVO = doDifferenceOnObject(leftEntry.getValue(), rightMap.get(leftEntry.getKey()), getters);
                if (objectVO.getPropertyList().size() != 0) {
                    result.getObjectList().add(objectVO);
                }
            }
        }

        return result;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    protected <T> DifferenceObjectVO doDifferenceOnObject(T leftObject, T rightObject, Map<String, Method> getters) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DifferenceObjectVO result;
        if (leftObject == null) {
            result = new DifferenceObjectVO();
        } else if (rightObject == null) {
            result = new DifferenceObjectVO(DifferenceType.REMOVAL, getters.get(config.idProperty).invoke(leftObject));
        } else {
            result = new DifferenceObjectVO(DifferenceType.CHANGE, getters.get(config.idProperty).invoke(leftObject));
        }

        for (Map.Entry<String, Method> getterEntry : getters.entrySet()) {
            String property = getterEntry.getKey();
            Method getter = getterEntry.getValue();
            if (config.exclude && config.properties.contains(property)) {
                continue;
            } else if (!config.exclude && !config.properties.contains(property)) {
                continue;
            }

            if (!getter.isAccessible()) {
                getter.setAccessible(true);
            }
            if (Iterable.class.isAssignableFrom(getter.getReturnType())) {
                Iterable leftColl = ReflectUtils.tryInvoke(getter, leftObject);
                Iterable rightColl = ReflectUtils.tryInvoke(getter, rightObject);
                DifferenceCollVO collVO = new DifferenceCollVO();
                collVO.setProperty(property);
                this.doCheck(collVO, leftColl, rightColl);
                if (collVO.getObjectList().size() > 0) {
                    result.getPropertyList().put(property, collVO);
                }
            }

            // one of the two are null
            if (leftObject == null || rightObject == null) {
                DifferenceVO vo = new DifferenceVO();
                vo.setNewValue(leftObject == null ? getter.invoke(rightObject) : null);
                vo.setOldValue(leftObject == null ? null : getter.invoke(leftObject));
                vo.setProperty(property);
                result.getPropertyList().put(property, vo);
            } else {
                // both are not null
                for (DifferenceComparator auditor : config.getComparators()) {
                    if (auditor.canCompare(getter.getReturnType())) {
                        Object leftValue = getter.invoke(leftObject);
                        Object rightValue = getter.invoke(rightObject);
                        boolean compareResult = auditor.compare(leftValue, rightValue, leftObject, rightObject);
                        if (!compareResult) {
                            DifferenceVO vo = new DifferenceVO();
                            vo.setProperty(property);
                            vo.setOldValue(leftValue);
                            vo.setNewValue(rightValue);
                            result.getPropertyList().put(property, vo);
                        }
                        break;
                    }
                }
            }
        }

        return result;
    }

    @Data
    public static class Config {
        private String idProperty = ID;
        private boolean exclude = true;
        private boolean merge = false;
        private Set<String> properties = Sets.newHashSet();
        private List<DifferenceComparator<?>> comparators;

        public Config() {
            this.comparators = Lists.newArrayList();
            comparators.add(new BooleanDifferenceComparator());
            comparators.add(new NumberDifferenceComparator());
            comparators.add(new StringDifferenceComparator());
            comparators.add(new ObjectDifferenceComparator());
        }
    }
}
