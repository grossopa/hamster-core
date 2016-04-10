/**
 * 
 */
package org.hamster.core.api.util.difference;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.merger.DiffMerger;
import org.hamster.core.api.util.difference.model.DiffObjectVO;
import org.hamster.core.api.util.difference.walker.DiffWalker;

import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DiffChecker<K, T> {
    
    @Getter
    @Setter
    private String idProperty;
    
    @Getter
    @Setter
    private boolean exclude;
    
    @Getter
    @Setter
    private String[] properties;
    
    @Setter
    private DiffWalker<K, T> walker;
    
    @Setter
    private DiffMerger<K, T> merger;
    
    
    public void check(Class<T> clazz, Collection<T> sourceColl, Collection<T> targetColl) throws DiffCheckerException {
        Map<K, T> sourceMap = toMap(clazz, sourceColl);
        Map<String, Pair<Method, Method>> methods = findProperties(clazz);
        
        // ensure walker and merger are valid
        precondition();
        
        // find added elements
        Collection<T> addedColl = walker.walkForAdded(sourceMap, targetColl);
        
        // find removed elements
        Map<K, T> removedColl = walker.walkForRemoved(sourceMap, targetColl);
        
        // find changed elements
        for (T target : targetColl) {
            K id = invokeId(methods, target);
            if (sourceMap.containsKey(id)) {
                DiffObjectVO objectVO = walker.walkProperty(methods, sourceMap.get(id), target);
                if (objectVO != null && objectVO.getPropertyList().size() > 0) {
                    
                }
            }
        }
        
        
        
        
    }
    
    protected void precondition() {
        if (walker == null) {
            
        }
        if (merger == null) {
            
        }
    }
    
    protected K invokeId(Map<String, Pair<Method, Method>> methods, T object) {
        Method method = methods.get(idProperty).getLeft();
        return ReflectUtils.tryInvoke(method, object);
    }
    
    protected Map<String, Pair<Method, Method>> findProperties(Class<T> clazz) {
        Set<String> temp = Sets.newHashSet(properties);
        if (exclude) {
            temp.add(idProperty);
        }
        return ReflectUtils.findGetterSetterMethods(clazz, exclude, temp.toArray(new String[]{}));
    }
    
    protected Map<K, T> toMap(Class<T> clazz, Collection<T> sourceColl) throws DiffCheckerException {
        try {
            return ReflectUtils.toMap(sourceColl, clazz, idProperty);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new DiffCheckerException("Failed to convert collection to map.", e);
        }
    }
}
