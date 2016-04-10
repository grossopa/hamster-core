/**
 * 
 */
package org.hamster.core.api.util.difference.walker;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.difference.model.DiffObjectVO;

/**
 * iterate collections
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface DiffWalker<K, T> {
    /**
     * find all added records
     * 
     * @param sourceMap
     * @param targetColl
     * @return
     */
    Collection<T> walkForAdded(Map<K, T> sourceMap, Collection<T> targetColl);
    
    /**
     * find all removed records
     * 
     * @param sourceMap
     * @param targetColl
     * @return
     */
    Map<K, T> walkForRemoved(Map<K, T> sourceMap, Collection<T> targetColl);
    
    /**
     * 
     * @param methods
     * @param source
     * @param target
     * @return null / DiffObjectVO
     */
    DiffObjectVO walkProperty(Map<String, Pair<Method, Method>> methods, T source, T target);
}
