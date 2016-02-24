/**
 * 
 */
package org.hamster.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ReflectionUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;

/**
 * 
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">grossopaforever@gmail.com</a>
 * @version May 13, 2014 5:40:47 PM
 */
public class ReflectUtils {

    public static final String GETTER_GET = "get";
    public static final String GETTER_IS = "is";

    public static final Map<Class<?>, Map<String, Method>> CACHE = Maps.newConcurrentMap();

    private ReflectUtils() {
    }

    /**
     * search all getter methods of a class
     * 
     * @param clazz
     * @return
     */
    public static final Map<String, Method> findGetterMethods(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }

        if (CACHE.containsKey(clazz)) {
            return CACHE.get(clazz);
        }

        Map<String, Method> result = Maps.newHashMap();

        Method[] methods = ReflectionUtils.getAllDeclaredMethods(clazz);
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            if ("getClass".equals(method.getName())) {
                continue;
            }
            String name = method.getName();
            String propertyName = null;
            if (name.startsWith(GETTER_GET)) {
                propertyName = name.substring(GETTER_GET.length());
            } else if (name.startsWith(GETTER_IS)) {
                propertyName = name.substring(GETTER_IS.length());
            }
            if (propertyName != null) {
                result.put(StringUtils.uncapitalize(propertyName), method);
            }
        }
        CACHE.put(clazz, result);
        return result;
    }

    /**
     * convert a collection to map
     * 
     * @param coll
     * @param idGetterMethod
     * @return
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public static final <T> Map<Object, T> toMap(Iterable<T> coll, Method idGetterMethod) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<Object, T> result = Maps.newHashMap();
        if (Iterables.isEmpty(coll)) {
            return result;
        }
        for (T object : coll) {
            if (!idGetterMethod.isAccessible()) {
                idGetterMethod.setAccessible(true);
            }
            Object idVal = idGetterMethod.invoke(object);
            result.put(idVal, object);
        }
        return result;
    }
    
    @SuppressWarnings("unchecked")
    public static final <T> T tryInvoke(Method method, Object object, Object... args) {
        try {
            method.setAccessible(true);
            return (T) method.invoke(object, args);
        } catch (Exception e) {
            return null;
        }
    }

}
