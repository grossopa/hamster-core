/**
 * 
 */
package org.hamster.core.api.util.difference.transformer;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * to get id from object
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @param T type of object
 * @param I type of id
 * @since 1.0
 */
public interface IdInvoker<K, T> {
    
    /**
     * to get id/generate hash code from object
     * 
     * @param idProperty
     * @param methods
     * @param object
     * @return
     */
    K invoke(Map<String, Method> methods, T object);
}
