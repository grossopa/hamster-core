/**
 * 
 */
package org.hamster.core.api.util.difference.internal.executors;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.DiffChecker;
import org.hamster.core.api.util.difference.DiffChecker.Config;
import org.hamster.core.api.util.difference.DiffCheckerException;
import org.hamster.core.api.util.difference.internal.model.ExecutorResult;
import org.hamster.core.api.util.difference.model.DiffObjectVO;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Executor for {@link DiffChecker}
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class CheckerExecutor<K, T> {

    private final Config<K, T> config;

    /**
     * Constructor
     * 
     * @param config
     */
    public CheckerExecutor(Config<K, T> config) {
        this.config = config;
    }

    /**
     * check logic
     * 
     * @param clazz
     *            the class of these objects
     * @param sourceColl
     *            source collection
     * @param targetColl
     *            target collection
     * @return list of {@link DiffObjectVO} contains the details of difference
     * @throws DiffCheckerException
     */
    public ExecutorResult<K, T> doCheck(Class<T> clazz, Collection<T> sourceColl, Collection<T> targetColl) throws DiffCheckerException {
        Map<String, Method> methods = findProperties(clazz);

        Map<K, T> sourceMap = toMap(methods, sourceColl);

        // find added elements
        Collection<T> addedColl = config.getWalker().walkForAdded(sourceMap, targetColl, methods);

        // find removed elements
        Map<K, T> removedColl = config.getWalker().walkForRemoved(sourceMap, targetColl, methods);

        // find changed elements
        Map<K, Pair<T, T>> changedColl = Maps.newHashMap();
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

    /**
     * find interested getter methods from class
     * 
     * @param clazz
     * @return property/method pair
     */
    protected Map<String, Method> findProperties(Class<T> clazz) {
        Set<String> temp = Sets.newHashSet(config.getProperties());
        if (!config.isExclude()) {
            // id property must be included
            temp.add(config.getIdProperty());
        }
        return ReflectUtils.findGetterMethods(clazz, config.isExclude(), temp.toArray(new String[] {}));
    }

    /**
     * Convert collection into map
     * 
     * @param clazz
     * @param sourceColl
     * @return
     */
    protected Map<K, T> toMap(Map<String, Method> methods, Collection<T> sourceColl) {
        Map<K, T> result = Maps.newHashMap();
        for (T source : sourceColl) {
            K id = config.getIdInvoker().invoke(methods, source);
            result.put(id, source);
        }

        return result;
    }
}