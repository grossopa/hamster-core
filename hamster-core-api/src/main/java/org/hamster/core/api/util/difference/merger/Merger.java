/**
 * 
 */
package org.hamster.core.api.util.difference.merger;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface Merger<K, T> {
    
    /**
     * merge added
     * 
     * @param sourceColl
     * @param addedColl
     */
    void mergeAdded(Collection<T> sourceColl, Collection<T> addedColl);
    
    /**
     * merge removed
     * 
     * @param sourceColl
     * @param removedColl
     */
    void mergeRemoved(Collection<T> sourceColl, Map<K, T> removedMap);
    
    /**
     * merge changed
     * 
     * @param method
     * @param source
     * @param changed
     */
    void mergeChanged(Map<String, Pair<Method, Method>> method, Set<String> changedProperties, T source, T changed);
}
