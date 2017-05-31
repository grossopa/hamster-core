/**
 * 
 */
package org.hamster.core.dao.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.hamster.core.api.consts.StatusType;
import org.hamster.core.api.model.base.StatusIfc;
import org.hibernate.validator.constraints.Length;

/**
 * Default implementation of StatusEntity. Maps to the _status column.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@MappedSuperclass
public abstract class StatusEntity extends IdEntity implements StatusIfc<Long> {
    
    public static final String PROP_STATUS = "status";
    public static final String COL_STATUS = "_status";
    
    @Length(max = 20)
    @Size(min = 1)
    @Column(name = COL_STATUS, length = 20, nullable = false)
    private String status;

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.StatusEntityIfc#getStatus()
     */
    @Override
    public String getStatus() {
        return this.status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.entity.StatusEntityIfc#setStatus(java.lang.String)
     */
    @Override
    public void setStatus(String status) {
        this.status = status;
    }
    
    @PrePersist
    public void prePersist() {
        if(StringUtils.isBlank(this.status)) {
            this.status = StatusType.ACTIVE;
        }
    }


}
