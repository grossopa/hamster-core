/**
 * 
 */
package org.hamster.core.api.util.difference.comparator;

import java.lang.reflect.Method;
import java.util.Comparator;

/**
 * compare properties
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface PropertyComparator extends Comparator<Object> {

    /**
     * determine if this comparator is suitable for execution, if return false then it will go to next comparator, if yes then will execute the {@link #compare(Object, Object)} method and break the
     * loop.
     * 
     * @param property
     * @param getterMethod
     * @return 
     */
    boolean canCompare(String property, Method getterMethod);
}
