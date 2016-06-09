/**
 * 
 */
package org.hamster.core.api.util.difference.comparator.defaults;

import org.hamster.core.api.util.ComparatorUtils;
import org.hamster.core.api.util.difference.comparator.PropertyComparator;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractPropertyComparator implements PropertyComparator {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Object o1, Object o2) {
        Integer result = ComparatorUtils.obviateNull(o1, o2);
        if (result != null) {
            return result;
        }
        return doCompare(o1, o2);
    }

    /**
     * compare the objects
     * 
     * @param o1
     *            non-null object1
     * @param o2
     *            non-null object2
     * @return 0 for equals and 1/-1 for not equals
     */
    protected abstract int doCompare(Object o1, Object o2);
}
