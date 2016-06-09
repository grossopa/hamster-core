/**
 * 
 */
package org.hamster.core.api.util.difference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.DiffChecker.Config;
import org.hamster.core.api.util.difference.DiffChecker.ExecutorResult;
import org.hamster.core.api.util.difference.comparator.PropertyComparator;
import org.hamster.core.api.util.difference.comparator.defaults.NumberComparator;
import org.hamster.core.api.util.difference.comparator.defaults.ObjectComparator;
import org.hamster.core.api.util.difference.merger.Merger;
import org.hamster.core.api.util.difference.model.DiffObjectVO;
import org.hamster.core.api.util.difference.model.mapper.DiffObjectVOMapper;
import org.hamster.core.api.util.difference.transformer.IdInvoker;
import org.hamster.core.api.util.difference.transformer.PropertyInvoker;
import org.hamster.core.api.util.difference.transformer.defaults.DefaultIdInvoker;
import org.hamster.core.api.util.difference.transformer.defaults.DefaultPropertyInvoker;
import org.hamster.core.api.util.difference.walker.Walker;
import org.hamster.core.api.util.difference.walker.defaults.DefaultWalker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DiffChecker<K, T> {

    @Getter
    @Setter
    private String idProperty;

    @Getter
    @Setter
    private boolean exclude = true;

    @Getter
    @Setter
    private String[] properties;

    @Getter
    @Setter
    private Walker<K, T> walker;

    @Getter
    @Setter
    private Merger<K, T> merger;

    @Getter
    @Setter
    private IdInvoker<K, T> idInvoker;

    @Getter
    @Setter
    private PropertyInvoker<K, T> propertyInvoker;

    @Getter
    @Setter
    private List<PropertyComparator> propertyComparators;

    public List<DiffObjectVO> check(Class<T> clazz, Collection<T> sourceColl, Collection<T> targetColl) throws DiffCheckerException {

        // ensure walker and merger are valid
        Config<K, T> config = validateConfig();

        // find all different items
        DiffCheckerExecutor<K, T> executor = new DiffCheckerExecutor<K, T>();
        executor.setConfig(config);
        ExecutorResult<K, T> result = executor.doCheck(clazz, sourceColl, targetColl);

        // map result into list of {@link DiffObjectVO}
        DiffObjectVOMapper<K, T> objectVOMapper = new DiffObjectVOMapper<K, T>(config.getIdInvoker(), config.getPropertyInvoker(),
                result.getMethods(), config.getPropertyComparators());
        List<DiffObjectVO> addedDiffObjects = objectVOMapper.mapAddedColl(result.getAddedColl());
        List<DiffObjectVO> removedDiffObjects = objectVOMapper.mapRemovedColl(result.getRemovedColl());
        List<DiffObjectVO> changedDiffObjects = objectVOMapper.mapChangedColl(result.getChangedColl());

        List<DiffObjectVO> listResult = Lists.newArrayList();
        listResult.addAll(addedDiffObjects);
        listResult.addAll(removedDiffObjects);
        listResult.addAll(changedDiffObjects);
        return listResult;
    }

    protected Config<K, T> validateConfig() {
        Config<K, T> config = new Config<K, T>();
        config.setExclude(exclude);
        config.setIdInvoker(idInvoker);
        config.setIdProperty(idProperty);
        config.setMerger(merger);
        config.setProperties(properties);
        config.setPropertyComparators(propertyComparators);
        config.setPropertyInvoker(propertyInvoker);
        config.setWalker(walker);

        if (StringUtils.isBlank(idProperty)) {
            config.setIdProperty("id");
        }

        if (idInvoker == null) {
            config.setIdInvoker(new DefaultIdInvoker<K, T>(config.getIdProperty()));
        }

        if (propertyInvoker == null) {
            config.setPropertyInvoker(new DefaultPropertyInvoker<K, T>());
        }

        if (propertyComparators == null) {
            config.setPropertyComparators(Lists.<PropertyComparator> newArrayList());
        }

        // add default ones
        config.getPropertyComparators().add(new NumberComparator());
        config.getPropertyComparators().add(new ObjectComparator());

        if (walker == null) {
            config.setWalker(new DefaultWalker<K, T>(config.getIdInvoker(), config.getPropertyComparators()));
        }

        if (merger == null) {
            // dont have yet
            config.setMerger(merger);
        }

        if (properties == null) {
            config.setProperties(new String[] {});
        }

        return config;
    }

    @Getter
    @Setter
    public static class Config<K, T> {

        private String idProperty;

        private boolean exclude = true;

        private String[] properties;

        private Walker<K, T> walker;

        private Merger<K, T> merger;

        private IdInvoker<K, T> idInvoker;

        private PropertyInvoker<K, T> propertyInvoker;

        private List<PropertyComparator> propertyComparators;
    }

    @Getter
    @Setter
    static class ExecutorResult<K, T> {

        private Map<String, Method> methods;

        private Collection<T> addedColl;

        private Map<K, T> removedColl;

        private Map<K, Pair<T, T>> changedColl;
    }

}

class DiffCheckerExecutor<K, T> {

    @Setter
    private Config<K, T> config;

    public ExecutorResult<K, T> doCheck(Class<T> clazz, Collection<T> sourceColl, Collection<T> targetColl) throws DiffCheckerException {
        Map<K, T> sourceMap = toMap(clazz, sourceColl);

        Map<String, Method> methods = findProperties(clazz);

        // find added elements
        Collection<T> addedColl = config.getWalker().walkForAdded(sourceMap, targetColl, methods);

        // find removed elements
        Map<K, T> removedColl = config.getWalker().walkForRemoved(sourceMap, targetColl, methods);

        Map<K, Pair<T, T>> changedColl = Maps.newHashMap();

        // find changed elements
        for (T target : targetColl) {
            K id = config.getIdInvoker().invoke(methods, target);
            if (sourceMap.containsKey(id)) {
                T source = sourceMap.get(id);
                Set<String> changedProperties = config.getWalker().walkProperty(source, target, methods);
                if (CollectionUtils.isNotEmpty(changedProperties)) {
                    changedColl.put(id, new ImmutablePair<T, T>(source, target));
                }
            }
        }

        ExecutorResult<K, T> result = new ExecutorResult<K, T>();
        result.setMethods(methods);
        result.setAddedColl(addedColl);
        result.setRemovedColl(removedColl);
        result.setChangedColl(changedColl);
        return result;
    }

    protected Map<String, Method> findProperties(Class<T> clazz) {
        Set<String> temp = Sets.newHashSet(config.getProperties());
        if (!config.isExclude()) {
            temp.add(config.getIdProperty());
        }
        return ReflectUtils.findGetterMethods(clazz, config.isExclude(), temp.toArray(new String[] {}));
    }

    protected Map<K, T> toMap(Class<T> clazz, Collection<T> sourceColl) throws DiffCheckerException {
        try {
            return ReflectUtils.toMap(sourceColl, clazz, config.getIdProperty());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DiffCheckerException("Failed to convert collection to map.", e);
        }
    }
}
