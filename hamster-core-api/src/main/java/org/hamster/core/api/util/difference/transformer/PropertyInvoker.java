/**
 * 
 */
package org.hamster.core.api.util.difference.transformer;

import java.lang.reflect.Method;

/**
 * invoke property with given getter/setter methods
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface PropertyInvoker<T> {

    /**
     * 
     * 
     * @param property
     * @param getterMethod
     * @param object
     * @return the value
     */
    Object invoke(String property, Method getterMethod, T object);
}
