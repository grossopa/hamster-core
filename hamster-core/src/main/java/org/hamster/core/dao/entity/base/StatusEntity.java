/**
 * 
 */
package org.hamster.core.dao.entity.base;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hamster.core.api.model.base.StatusIfc;
import org.hibernate.annotations.Where;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Default implementation of StatusEntity. Maps to the _status column.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@MappedSuperclass
@Where(clause = "status == 'ACTIVE'")
public abstract class StatusEntity extends IdEntity implements StatusIfc<Long> {

    public static final String PROP_STATUS = "status";
    public static final String COL_STATUS = "_status";

    @Length(max = 20)
    @NotBlank
    @Column(name = COL_STATUS, length = 20, nullable = true, columnDefinition = "default 'ACTIVE'")
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

}
