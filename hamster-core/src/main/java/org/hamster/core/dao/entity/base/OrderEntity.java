/**
 * 
 */
package org.hamster.core.dao.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hamster.core.api.model.base.OrderIfc;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@MappedSuperclass
public abstract class OrderEntity extends IdEntity implements OrderIfc<Long> {

    public static final String PROP_ORDER = "order";
    public static final String COL_ORDER = "_order";

    @Column(name = COL_ORDER, nullable = true)
    private Integer order;

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.dao.entity.OrderEntityIfc#getOrder()
     */
    @Override
    public Integer getOrder() {
        return this.order;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.dao.entity.OrderEntityIfc#setOrder(java.lang.Integer)
     */
    @Override
    public void setOrder(Integer order) {
        this.order = order;
    }

}
