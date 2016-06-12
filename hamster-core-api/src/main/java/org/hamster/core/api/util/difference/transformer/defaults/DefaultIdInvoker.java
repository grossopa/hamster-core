/**
 * 
 */
package org.hamster.core.api.util.difference.transformer.defaults;

import java.lang.reflect.Method;
import java.util.Map;

import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.transformer.IdInvoker;

/**
 * get id from object by idProperty
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultIdInvoker<K, T> implements IdInvoker<K, T> {

    private final String idProperty;

    public DefaultIdInvoker(String idProperty) {
        this.idProperty = idProperty;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.transformer.IdInvoker#invoke(java.util.Map, java.lang.Object)
     */
    @Override
    public K invoke(Map<String, Method> methods, T object) {
        Method method = methods.get(idProperty);
        return ReflectUtils.tryInvoke(method, object);
    }

}
