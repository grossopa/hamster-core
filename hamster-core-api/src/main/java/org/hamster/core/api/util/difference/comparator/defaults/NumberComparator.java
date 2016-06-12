/**
 * 
 */
package org.hamster.core.api.util.difference.comparator.defaults;

import java.lang.reflect.Method;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.common.primitives.Doubles;

/**
 * Including compare support for primitive and non-primitive numbers
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class NumberComparator extends AbstractPropertyComparator {

    /**
     * list of primitive number types
     */
    public static final Set<String> PRIMITIVES = ImmutableSet.of("short", "int", "long", "float", "double");

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.comparator.PropertyComparator#canCompare(java.lang.String, java.lang.reflect.Method)
     */
    @Override
    public boolean canCompare(String property, Method getterMethod) {
        Class<?> returnType = getterMethod.getReturnType();
        return Number.class.isAssignableFrom(returnType) || (returnType.isPrimitive() && PRIMITIVES.contains(returnType.getName()));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.comparator.defaults.AbstractPropertyComparator#doCompare(java.lang.Object, java.lang.Object)
     */
    @Override
    protected int doCompare(Object o1, Object o2) {
        Number n1 = (Number) o1;
        Number n2 = (Number) o2;
        return Doubles.compare(n1.doubleValue(), n2.doubleValue());
    }

}
