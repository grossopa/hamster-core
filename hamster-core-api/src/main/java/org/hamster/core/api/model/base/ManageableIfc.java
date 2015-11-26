/**
 * 
 */
package org.hamster.core.api.model.base;

import java.util.Date;



/**
 * Manageable Entity contains the creation info and last update info. this is not a replacement of audit system, it
 * contains basic management information for each row.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface ManageableIfc<T> extends IdIfc<T>, StatusIfc<T> {

    /**
     * @return the people id who created it.
     */
    String getCreatedBy();

    /**
     * @return the date of creation
     */
    Date getCreatedOn();

    /**
     * @return the people id who last updated it.
     */
    String getUpdatedBy();

    /**
     * @return the date of last update
     */
    Date getUpdatedOn();

    /**
     * @param createdBy
     */
    void setCreatedBy(String createdBy);

    /**
     * @param createdOn
     */
    void setCreatedOn(Date createdOn);

    /**
     * @param updatedBy
     */
    void setUpdatedBy(String updatedBy);

    /**
     * @param updatedOn
     */
    void setUpdatedOn(Date updatedOn);

}
