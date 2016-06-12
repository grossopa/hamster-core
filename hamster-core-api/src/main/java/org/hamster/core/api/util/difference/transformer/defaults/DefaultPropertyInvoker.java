/**
 * 
 */
package org.hamster.core.api.util.difference.transformer.defaults;

import java.lang.reflect.Method;

import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.transformer.PropertyInvoker;

/**
 * to get value by getter method
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultPropertyInvoker<T> implements PropertyInvoker<T> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.transformer.PropertyInvoker#invoke(java.lang.String, org.apache.commons.lang3.tuple.Pair, java.lang.Object)
     */
    @Override
    public Object invoke(String property, Method method, T object) {
        return ReflectUtils.tryInvoke(method, object);
    }

}
