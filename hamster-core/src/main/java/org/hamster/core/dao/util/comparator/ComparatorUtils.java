/**
 * 
 */
package org.hamster.core.dao.util.comparator;

/**
 * contains utils for comparator
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public final class ComparatorUtils {

    /**
     * check null.
     * 
     * @param o1
     * @param o2
     * @return 1 if o1 is null, -1 if o2 is null, 0 if both nulls and null if both not null
     */
    public static Integer obviateNull(Object o1, Object o2) {
        if (o1 == null) {
            return o2 == null ? 0 : 1;
        } else if (o2 == null) {
            return -1;
        }

        return null;
    }
}
