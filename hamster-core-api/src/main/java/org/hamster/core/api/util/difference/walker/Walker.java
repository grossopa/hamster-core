/**
 * 
 */
package org.hamster.core.api.util.difference.walker;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * iterate collections
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface Walker<K, T> {
    /**
     * find all added records
     * 
     * @param sourceMap
     * @param targetColl
     * @return
     */
    Collection<T> walkForAdded(Map<K, T> sourceMap, Collection<T> targetColl, Map<String, Method> methods);

    /**
     * find all removed records
     * 
     * @param sourceMap
     * @param targetColl
     * @return
     */
    Map<K, T> walkForRemoved(Map<K, T> sourceMap, Collection<T> targetColl, Map<String, Method> methods);

    /**
     * find different properties for merging
     * 
     * @param methods
     * @param source
     * @param target
     * @return different property name
     */
    Set<String> walkProperty(T source, T target, Map<String, Method> methods);
}
