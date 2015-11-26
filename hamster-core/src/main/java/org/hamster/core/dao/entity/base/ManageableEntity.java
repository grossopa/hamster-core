/**
 * 
 */
package org.hamster.core.dao.entity.base;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hamster.core.api.model.base.ManageableIfc;
import org.hibernate.validator.constraints.Length;

/**
 * Default implementation of ManageableEntity
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@MappedSuperclass
public abstract class ManageableEntity extends StatusEntity implements ManageableIfc<Long> {
    /**
     * create by column name
     */
    public static final String COL_CREATED_BY = "_created_by";

    /**
     * created on column name
     */
    public static final String COL_CREATED_ON = "_created_on";

    /**
     * updated by column name
     */
    public static final String COL_UPDATED_BY = "_updated_by";

    /**
     * updated on column name
     */
    public static final String COL_UPDATED_ON = "_updated_on";

    @Length(max = 100)
    @Column(name = COL_CREATED_BY, length = 100, nullable = true)
    private String createdBy;
    
    @Column(name = COL_CREATED_ON, nullable = true)
    private Date createdOn;
    
    @Length(max = 100)
    @Column(name = COL_CREATED_BY, length = 100, nullable = true)
    private String updatedBy;
    
    @Column(name = COL_CREATED_ON, nullable = true)
    private Date updatedOn;

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#getCreatedBy()
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#getCreatedOn()
     */
    public Date getCreatedOn() {
        return createdOn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#getUpdatedBy()
     */
    public String getUpdatedBy() {
        return updatedBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#getUpdatedOn()
     */
    public Date getUpdatedOn() {
        return updatedOn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#setCreatedBy(java.lang.String)
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#setCreatedOn(java.util.Date)
     */
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#setUpdatedBy(java.lang.String)
     */
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.ManageableEntityIfc#setUpdatedOn(java.util.Date)
     */
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

}
