/**
 * 
 */
package org.hamster.core.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
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
public final class ReflectUtils {

    public static final String GETTER_GET = "get";
    public static final String GETTER_IS = "is";

    private ReflectUtils() {
    }

    /**
     * search all getter methods of a class
     * 
     * @param clazz
     * @return
     */
    public static Map<String, Method> findGetterMethods(Class<?> clazz) {
        return findGetterMethods(clazz, true, null);
    }
    
    /**
     * get getter methods with include/exclude 
     * 
     * @param clazz
     * @param exclude exclude/include properties
     * @param properties properties to be excluded/included
     * @return
     */
    public static Map<String, Method> findGetterMethods(Class<?> clazz, boolean exclude, String[] properties) {
        if (clazz == null) {
            return null;
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
            propertyName = StringUtils.uncapitalize(propertyName);
            
            if (exclude && ArrayUtils.contains(properties, propertyName)) {
                continue;
            } else if (!exclude && !ArrayUtils.contains(properties, propertyName)) {
                continue;
            }
            
            if (propertyName != null) {
                result.put(propertyName, method);
            }
        }
        return result;
    }
    
    public static Map<String, Method> findMethodByPrefix(Class<?> clazz, String[] prefixList, boolean exclude, String[] properties) {
        if (clazz == null) {
            return null;
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
            propertyName = StringUtils.uncapitalize(propertyName);
            
            if (exclude && ArrayUtils.contains(properties, propertyName)) {
                continue;
            } else if (!exclude && !ArrayUtils.contains(properties, propertyName)) {
                continue;
            }
            
            if (propertyName != null) {
                result.put(propertyName, method);
            }
        }
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
    public static <T> Map<Object, T> toMap(Iterable<T> coll, Method idGetterMethod) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
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
    public static <T> T tryInvoke(Method method, Object object, Object... args) {
        try {
            method.setAccessible(true);
            return (T) method.invoke(object, args);
        } catch (Exception e) {
            return null;
        }
    }

}
