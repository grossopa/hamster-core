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
import org.hamster.core.util.audit.auditors.BooleanAuditor;
import org.hamster.core.util.audit.auditors.NumberAuditor;
import org.hamster.core.util.audit.auditors.ObjectAuditor;
import org.hamster.core.util.audit.auditors.StringAuditor;
import org.hamster.core.util.audit.auditors.base.Auditor;
import org.hamster.core.util.audit.model.DifferenceCollVO;
import org.hamster.core.util.audit.model.DifferenceObjectVO;
import org.hamster.core.util.audit.model.DifferenceType;
import org.hamster.core.util.audit.model.DifferenceVO;
import org.hamster.core.utils.ReflectUtils;
import org.hibernate.Hibernate;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * find differences between two objects/collections
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ModificationAuditor {

    public static final String ID = "id";

    private AuditorConfig config;

    public ModificationAuditor(AuditorConfig config) {
        this.config = config;
    }

    public <T> DifferenceCollVO difference(Collection<T> leftColl, Collection<T> rightColl) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        DifferenceCollVO collVO = new DifferenceCollVO();
        return doDifference(collVO, leftColl, rightColl);
    }

    protected <T> DifferenceCollVO doDifference(DifferenceCollVO result, Iterable<T> leftColl, Iterable<T> rightColl) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

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
                this.doDifference(collVO, leftColl, rightColl);
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
                for (Auditor auditor : config.auditors) {
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

    public static class AuditorConfig {
        private String idProperty = ID;
        private boolean exclude = true;
        private Set<String> properties = Sets.newHashSet();
        private boolean merge = false;
        private List<? extends Auditor> auditors = Lists.newArrayList(new BooleanAuditor(), new NumberAuditor(), new StringAuditor(), new ObjectAuditor());

        /**
         * @return the idProperty
         */
        public String getIdProperty() {
            return idProperty;
        }

        /**
         * @return the exclude
         */
        public boolean isExclude() {
            return exclude;
        }

        /**
         * @return the properties
         */
        public Set<String> getProperties() {
            return properties;
        }

        /**
         * @return the auditors
         */
        public List<? extends Auditor> getAuditors() {
            return auditors;
        }

        /**
         * @param idProperty
         *            the idProperty to set
         */
        public void setIdProperty(String idProperty) {
            this.idProperty = idProperty;
        }

        /**
         * @param exclude
         *            the exclude to set
         */
        public void setExclude(boolean exclude) {
            this.exclude = exclude;
        }

        /**
         * @param properties
         *            the properties to set
         */
        public void setProperties(Set<String> properties) {
            this.properties = properties;
        }

        /**
         * @param auditors
         *            the auditors to set
         */
        public void setAuditors(List<Auditor> auditors) {
            this.auditors = auditors;
        }

        /**
         * @return the merge
         */
        public boolean isMerge() {
            return merge;
        }

        /**
         * @param merge
         *            the merge to set
         */
        public void setMerge(boolean merge) {
            this.merge = merge;
        }

    }
}
