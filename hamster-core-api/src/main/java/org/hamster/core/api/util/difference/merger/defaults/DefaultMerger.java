/**
 * 
 */
package org.hamster.core.api.util.difference.merger.defaults;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.merger.Merger;
import org.hamster.core.api.util.difference.transformer.PropertyInvoker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultMerger<K, T> implements Merger<K, T> {

    private static final Logger log = LoggerFactory.getLogger(DefaultMerger.class);
    
    private final PropertyInvoker<T> propertyInvoker;
    
    public DefaultMerger(PropertyInvoker<T> propertyInvoker) {
        this.propertyInvoker = propertyInvoker;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.merger.Merger#mergeAdded(java.util.Collection, java.util.Collection)
     */
    @Override
    public void mergeAdded(Collection<T> sourceColl, Collection<T> addedColl) {
        sourceColl.addAll(addedColl);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.merger.Merger#mergeRemoved(java.util.Collection, java.util.Map)
     */
    @Override
    public void mergeRemoved(Collection<T> sourceColl, Map<K, T> removedMap) {
        sourceColl.removeAll(removedMap.values());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.merger.Merger#mergeChanged(org.apache.commons.lang3.tuple.Pair, java.lang.Object, java.lang.Object)
     */
    @Override
    public void mergeChanged(Map<String, Pair<Method, Method>> methods, Set<String> changedProperties, T source, T target) {
        for (String property : changedProperties) {
            if (!methods.containsKey(property)) {
                log.info(MessageFormat.format("No Setter found for property \"{0}\"", property));
                continue;
            }

            Method getter = methods.get(property).getLeft();
            Method setter = methods.get(property).getRight();
            boolean isGetterAccessible = getter.isAccessible();
            boolean isSetterAccessible = setter.isAccessible();

            try {
                getter.setAccessible(true);
                setter.setAccessible(true);
                Object targetValue = propertyInvoker.invoke(property, getter, target);
                ReflectUtils.invoke(setter, source, targetValue);
            } catch (IllegalAccessException | InvocationTargetException e) {
                log.info(MessageFormat.format("Invoke property \"{0}\" failed.", property), e);
            } finally {
                getter.setAccessible(isGetterAccessible);
                setter.setAccessible(isSetterAccessible);
            }
        }
    }

}
