/**
 * 
 */
package org.hamster.core.api.model.base;



/**
 * Define a _status column for soft deletion, history recording. Extensibility is also supported since developer can
 * store any string other than predefined StatusType.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface StatusIfc<T> extends IdIfc<T> {
    
    /**
     * the status column
     * 
     * @see org.hamster.core.dao.consts.StatusEntityType
     * @return the status
     */
    String getStatus();

    /**
     * the status to set
     * 
     * @param status
     */
    void setStatus(String status);
}
