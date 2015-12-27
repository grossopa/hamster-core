/**
 * 
 */
package org.hamster.core.dao.entity.base;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hamster.core.api.model.base.IdIfc;

/**
 * Default implementation of IdEntity.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@MappedSuperclass
public abstract class IdEntity implements IdIfc<Long> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.IdEntityIfc#getId()
     */
    @Override
    public Long getId() {
        return this.id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.IdEntityIfc#setId(java.lang.Long)
     */
    @Override
    public void setId(Long id) {
        this.id = id;
    }

}
