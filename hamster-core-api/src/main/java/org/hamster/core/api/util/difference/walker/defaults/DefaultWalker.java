/**
 * 
 */
package org.hamster.core.api.util.difference.walker.defaults;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hamster.core.api.util.difference.comparator.PropertyComparator;
import org.hamster.core.api.util.difference.transformer.IdInvoker;
import org.hamster.core.api.util.difference.transformer.PropertyInvoker;
import org.hamster.core.api.util.difference.walker.Walker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * Default walker to walker for added/removed and changed of a collection
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultWalker<K, T> implements Walker<K, T> {

    private final IdInvoker<K, T> idInvoker;

    private final PropertyInvoker<T> propertyInvoker;

    private final List<PropertyComparator> propertyComparators;

    public DefaultWalker(IdInvoker<K, T> idInvoker, PropertyInvoker<T> propertyInvoker, List<PropertyComparator> propertyComparators) {
        this.idInvoker = idInvoker;
        this.propertyInvoker = propertyInvoker;
        this.propertyComparators = propertyComparators;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.walker.Walker#walkForAdded(java.util.Map, java.util.Collection)
     */
    @Override
    public Collection<T> walkForAdded(Map<K, T> sourceMap, Collection<T> targetColl, Map<String, Method> methods) {
        List<T> result = Lists.newArrayList();
        for (T target : targetColl) {
            K id = idInvoker.invoke(methods, target);
            if (id == null || !sourceMap.containsKey(id)) {
                result.add(target);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.walker.Walker#walkForRemoved(java.util.Map, java.util.Collection)
     */
    @Override
    public Map<K, T> walkForRemoved(Map<K, T> sourceMap, Collection<T> targetColl, Map<String, Method> methods) {
        Map<K, T> result = Maps.newHashMap();
        Set<K> keys = Sets.newHashSet();
        for (T target : targetColl) {
            K id = idInvoker.invoke(methods, target);
            keys.add(id);
        }
        for (Map.Entry<K, T> sourceEntry : sourceMap.entrySet()) {
            if (!keys.contains(sourceEntry.getKey())) {
                result.put(sourceEntry.getKey(), sourceEntry.getValue());
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.walker.Walker#walkProperty(java.lang.Object, java.lang.Object, java.util.Map)
     */
    @Override
    public Set<String> walkProperty(T source, T target, Map<String, Method> methods) {
        Set<String> result = Sets.newHashSet();

        for (Map.Entry<String, Method> methodEntry : methods.entrySet()) {
            String property = methodEntry.getKey();
            Method method = methodEntry.getValue();

            Object sourceVal = propertyInvoker.invoke(property, method, source);
            Object targetVal = propertyInvoker.invoke(property, method, target);

            for (PropertyComparator comparator : propertyComparators) {
                if (comparator.canCompare(property, method)) {
                    if (comparator.compare(sourceVal, targetVal) != 0) {
                        result.add(property);
                    }
                    break;
                }
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.walker.Walker#walkCollection(java.lang.Object, java.lang.Object, java.util.Map)
     */
    @Override
    public Set<String> walkCollection(T source, T target, Map<String, Method> methods) {
        return null;
    }

}
