/**
 * 
 */
package org.hamster.core.util.comparator;

import java.util.Comparator;

import org.hamster.core.api.model.base.OrderIfc;

/**
 * sort by order ASC
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class OrderEntityComparator<T> implements Comparator<OrderIfc<T>> {

    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(OrderIfc<T> o1, OrderIfc<T> o2) {
        Integer result = ComparatorUtils.obviateNull(o1, o2);
        if (result != null) {
            return result;
        }

        Integer order = ComparatorUtils.obviateNull(o1.getOrder(), o2.getOrder());
        if (order != null) {
            return order;
        } else {
            return o1.getOrder().compareTo(o2.getOrder());
        }
    }

}
