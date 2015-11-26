/**
 * 
 */
package org.hamster.core.api.model.base;



/**
 * Define a _order column for ordering and sorting.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface OrderIfc<T> extends IdIfc<T> {

    /**
     * 
     * @return the order
     */
    Integer getOrder();

    /**
     * set the order
     * 
     * @param order
     */
    void setOrder(Integer order);
}
