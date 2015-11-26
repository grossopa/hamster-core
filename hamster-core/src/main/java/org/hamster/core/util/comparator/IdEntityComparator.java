/**
 * 
 */
package org.hamster.core.util.comparator;

import java.util.Comparator;

import org.hamster.core.api.model.base.IdIfc;

/**
 * order by id asc
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class IdEntityComparator<T> implements Comparator<IdIfc<T>> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(IdIfc<T> o1, IdIfc<T> o2) {
        Integer result = ComparatorUtils.obviateNull(o1, o2);
        if (result != null) {
            return result;
        }
        
        T i1 = o1.getId();
        T i2 = o2.getId();
        result = ComparatorUtils.obviateNull(i1, i2);
        if (result != null) {
            return result;
        }
        return String.valueOf(i1).compareToIgnoreCase(String.valueOf(i2));
    }

}
