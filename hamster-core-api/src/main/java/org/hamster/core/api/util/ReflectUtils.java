/**
 * 
 */
package org.hamster.core.api.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.MethodCallback;
import org.springframework.util.ReflectionUtils.MethodFilter;

import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

/**
 * reflect utilities in addition for {@link ReflectionUtils}
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">grossopaforever@gmail.com</a>
 * @version May 13, 2014 5:40:47 PM
 */
public final class ReflectUtils {
    
    private static final Logger log = LoggerFactory.getLogger(ReflectUtils.class);

    public static final String GETTER_GET = "get";
    public static final String GETTER_IS = "is";
    public static final String SETTER_SET = "set";

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
     * search getter methods with include/exclude
     * 
     * @param clazz
     * @param exclude
     *            exclude/include properties
     * @param properties
     *            properties to be excluded/included
     * @return
     */
    public static Map<String, Method> findGetterMethods(Class<?> clazz, boolean exclude, String[] properties) {
        return findFieldMethodByPrefix(clazz, new String[]{GETTER_GET, GETTER_IS}, exclude, properties);
    }
    
    /**
     * search all setter methods of a class
     * 
     * @param clazz
     * @return
     */
    public static Map<String, Method> findSetterMethods(Class<?> clazz) {
        return findSetterMethods(clazz, true, null);
    }
    
    /**
     * search setter methods with include/exclude
     * 
     * @param clazz
     * @param exclude
     *            exclude/include properties
     * @param properties 
     *            properties to be excluded/included
     * @return
     */
    public static Map<String, Method> findSetterMethods(Class<?> clazz, boolean exclude, String[] properties) {
        return findFieldMethodByPrefix(clazz, new String[]{SETTER_SET}, exclude, properties);
    }
    
    /**
     * search all getter/setter pairs of a class
     * 
     * @param clazz
     * @return
     */
    public static Map<String, Pair<Method, Method>> findGetterSetterMethods(Class<?> clazz) {
        return findGetterSetterMethods(clazz, true, null);
    }
    
    /**
     * search getter/setter pairs with include/exclude
     * 
     * @param clazz
     * @param exclude exclude/include properties
     * @param properties properties to be excluded/included
     * @return
     */
    public static Map<String, Pair<Method, Method>> findGetterSetterMethods(Class<?> clazz, boolean exclude, String[] properties) {
        if (clazz == null) {
            return null;
        }
        Map<String, Pair<Method, Method>> result = Maps.newHashMap();
        Map<String, Method> getters = findGetterMethods(clazz, exclude, properties);
        Map<String, Method> setters = findSetterMethods(clazz, exclude, properties);
        for (Map.Entry<String, Method> getterEntry : getters.entrySet()) {
            String key = getterEntry.getKey();
            Method setterMethod = setters.get(key);
            if (setterMethod != null) {
                result.put(key, ImmutablePair.of(getterEntry.getValue(), setterMethod));
            }
        }
        return result;
    }
    
    /**
     * find all methods by name
     * 
     * @param clazz
     * @param methodName
     * @return 
     */
    public static Set<Method> findMethodsByName(final Class<?> clazz, final String methodName) {
        Assert.notNull(clazz);
        Assert.notNull(methodName);
        Set<Method> result = Sets.newHashSet();
        for (Method method : clazz.getMethods()) {
            if (method.getName().equals(methodName)) {
                result.add(method);
            }
        }
        return result;
    }
    
    /**
     * find the executable method by params
     * 
     * @param methods
     * @param params
     * @return
     */
    private static Method findExecutableMethod(Iterable<Method> methods, Object... params) {
        for (Method method : methods) {
            if (isExecutableMethod(method, params)) {
                return method;
            }
        }
        return null;
        
    }
    
    /**
     * determine if method is executable for input params
     * 
     * @param method
     * @param params
     * @return 
     */
    private static boolean isExecutableMethod(Method method, Object... params) {
        if (method.getParameterTypes().length != params.length) {
            return false;
        }
        
        // evaluate params
        for (int i = 0; i < params.length; i++) {
            Object param = params[i];
            if (param == null) {
                // null is applicable for any types
                continue;
            }
            Class<?> methodParameterType = method.getParameterTypes()[i];
            Class<?> paramType = param.getClass();
            if (methodParameterType.isAssignableFrom(paramType)) {
                continue;
            }
            // not applicable
            return false;
        }
        return true;
    }
    

    /**
     * find Field Method by prefix
     * 
     * @param clazz
     * @param prefixList
     * @param exclude
     * @param properties
     * @return key-value pair for propertyName-Method
     */
    public static Map<String, Method> findFieldMethodByPrefix(final Class<?> clazz, final String[] prefixList, final boolean exclude, final String[] properties) {
        if (clazz == null) {
            return null;
        }

        final Map<String, Method> result = Maps.newHashMap();
        ReflectionUtils.doWithMethods(clazz, new MethodCallback() {

            /*
             * (non-Javadoc)
             * @see org.springframework.util.ReflectionUtils.MethodCallback#doWith(java.lang.reflect.Method)
             */
            @Override
            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
                String name = method.getName();
                for (String prefix : prefixList) {
                    if (name.startsWith(prefix)) {
                        String propertyName = StringUtils.uncapitalize(name.substring(prefix.length()));
                        if (exclude && ArrayUtils.contains(properties, propertyName)) {
                            continue;
                        } else if (!exclude && !ArrayUtils.contains(properties, propertyName)) {
                            continue;
                        }

                        result.put(propertyName, method);
                        break;
                    }
                }
            }
        }, new DefaultMethodFilter());
        return result;
    }
    
    /**
     * DefaultMethodFilter to filter non-public methods and getClass
     * 
     * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
     * @since 1.0
     */
    private static class DefaultMethodFilter implements MethodFilter {
        
        /*
         * (non-Javadoc)
         * 
         * @see org.springframework.util.ReflectionUtils.MethodFilter#matches(java.lang.reflect.Method)
         */
        @Override
        public boolean matches(Method method) {
            return Modifier.isPublic(method.getModifiers()) && !"getClass".equals(method.getName());
        }
    }
    
    /**
     * convert a collection to map
     * 
     * @param coll
     * @param clazz
     * @param idProperty
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <K, T> Map<K, T> toMap(Iterable<T> coll, Class<T> clazz, String idProperty) throws IllegalAccessException, InvocationTargetException {
        Map<String, Method> methods = findGetterMethods(clazz, false, new String[]{idProperty});
        return toMap(coll, methods.get(idProperty));
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
    @SuppressWarnings("unchecked")
    public static <K, T> Map<K, T> toMap(Iterable<T> coll, Method idGetterMethod) throws IllegalAccessException, InvocationTargetException {
        Map<K, T> result = Maps.newHashMap();
        if (coll == null || Iterables.isEmpty(coll)) {
            return result;
        }
        if (!idGetterMethod.isAccessible()) {
            idGetterMethod.setAccessible(true);
        }
        for (T object : coll) {
            K idVal = (K) idGetterMethod.invoke(object);
            result.put(idVal, object);
        }
        return result;
    }

    /**
     * try to invoke a method, return null for any exceptions
     * 
     * @param method
     * @param object
     * @param args
     * @return value / null
     */
    public static <T> T tryInvoke(Method method, Object object, Object... args) {
        boolean methodAccessible = false;
        try {
            methodAccessible = method.isAccessible();
            if (!methodAccessible) {
                method.setAccessible(true);
            }
            return invoke(method, object, args);
        } catch (Exception e) {
            log.info("Invoke method failed " + method + " for Object " + object, e);
            return null;
        } finally {
            if (method != null && !methodAccessible) {
                method.setAccessible(methodAccessible);
            }
        }
    }
    
    /**
     * try to invoke a method by method name, return null for any exceptions
     * 
     * @param methodName
     * @param object
     * @param args
     * @return value / null
     */
    public static <T> T tryInvoke(String methodName, Object object, Object... args) {
        try {
            Set<Method> methods = findMethodsByName(object.getClass(), methodName);
            Method method = findExecutableMethod(methods, args);
            return tryInvoke(method, object, args);
        } catch (Exception e) {
            log.info("Invoke method failed: " + methodName + " for Object " + object, e);
            return null;
        }
    }
    
    /**
     * invoke a method
     * 
     * @param method
     * @param object
     * @param args
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @SuppressWarnings("unchecked")
    public static <T> T invoke(Method method, Object object, Object... args) throws IllegalAccessException, InvocationTargetException {
        return (T) method.invoke(object, args);
    }
    
    /**
     * invoke a method by method name
     * 
     * @param methodName
     * @param object
     * @param args
     * @return
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public static <T> T invoke(String methodName, Object object, Object... args) throws IllegalAccessException, InvocationTargetException {
        Assert.notNull(methodName);
        Assert.notNull(object);
        Set<Method> methods = findMethodsByName(object.getClass(), methodName);
        Method method = findExecutableMethod(methods, args);
        if (method == null) {
            throw new NullPointerException(MessageFormat.format("Cannot find method {0} of Class {1}.", methodName, object.getClass().getName()));
        }
        return invoke(method, object, args);
    }

}
