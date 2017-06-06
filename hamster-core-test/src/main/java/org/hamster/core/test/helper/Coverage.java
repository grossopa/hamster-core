/**
 * 
 */
package org.hamster.core.test.helper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

/**
 * to provide coverage methods
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class Coverage {

    private Coverage() {
        throw new AssertionError();
    }

    /**
     * to formalize the util class and provide coverage for the default private constructor
     * 
     * @param clazz
     *            the util class to be test
     */
    public static final void coverUtilConstructor(Class<?> clazz) {
        Constructor<?> constructor = null;
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            Asserts.assertEquals("Utility class should only have one private constructor", 1, constructors.length);
            constructor = constructors[0];
            final Constructor<?> _con = constructor;
            Asserts.assertTrue("Utility class should only have one private constructor",
                    Modifier.isPrivate(_con.getModifiers()));
            Asserts.assertEquals(0, _con.getParameterCount());
            Asserts.assertThrown(InvocationTargetException.class, () -> {
                _con.setAccessible(true);
                _con.newInstance();
            });
        } finally {
            if (constructor != null) {
                constructor.setAccessible(false);
            }
        }
    }
    
    /**
     * cover getter / setter methods
     * 
     * @param clazz
     */
    public static final void coverJavaBean(Class<?> clazz) {
    }
}
