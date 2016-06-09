/**
 * 
 */
package org.hamster.core.api.util.difference.comparator.defaults;

import java.lang.reflect.Method;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ObjectComparator extends AbstractPropertyComparator {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#doCompare(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public int doCompare(Object o1, Object o2) {
        if (o1 instanceof Comparable) {
            return ((Comparable) o1).compareTo(o2);
        } else if (o2 instanceof Comparable) {
            return ((Comparable) o2).compareTo(o1);
        } else {
            // here we don't care the order
            return o1.equals(o2) ? 0 : -1;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.comparator.PropertyComparator#canCompare(java.lang.String, java.lang.reflect.Method)
     */
    @Override
    public boolean canCompare(String property, Method getterMethod) {
        Class<?> returnType = getterMethod.getReturnType();
        return !Iterable.class.isAssignableFrom(returnType);
    }

}
