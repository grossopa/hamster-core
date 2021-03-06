/**
 * 
 */
package org.hamster.core.dao.util;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.hamster.core.api.consts.StatusType;
import org.hamster.core.api.model.base.ManageableIfc;

/**
 * entity related utils
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class EntityUtils {
    
    private EntityUtils() {
        // hidden constructor
    }

    /**
     * update modify info including createdBy, createdOn, updatedBy, updatedOn with current timestamp
     * 
     * @param entity
     * @param user
     */
    public static final <T> void updateModifyInfo(ManageableIfc<T> entity, String user) {
        updateModifyInfo(entity, user, new Date());
    }

    /**
     * update modify info including createdBy, createdOn, updatedBy, updatedOn
     * 
     * @param entity
     * @param user
     * @param time
     */
    public static final <T> void updateModifyInfo(ManageableIfc<T> entity, String user, Date time) {
        if (StringUtils.isEmpty(entity.getCreatedBy()) || entity.getCreatedOn() == null) {
            entity.setCreatedBy(user);
            entity.setCreatedOn(time);
        }
        entity.setUpdatedBy(user);
        entity.setUpdatedOn(time);

        updateStatus(entity);
    }

    /**
     * update status to active if empty
     * 
     * @param entity
     */
    public static final <T> void updateStatus(ManageableIfc<T> entity) {
        if (StringUtils.isEmpty(entity.getStatus())) {
            entity.setStatus(StatusType.ACTIVE);
        }
    }

    /**
     * copy modify info
     * 
     * @param target
     * @param source
     */
    public static final <T> void copyModifyInfo(ManageableIfc<T> target, ManageableIfc<T> source) {
        if (target != null && source != null) {
            target.setCreatedBy(source.getCreatedBy());
            target.setCreatedOn(source.getCreatedOn());
            target.setId(source.getId());
            target.setStatus(source.getStatus());
            target.setUpdatedBy(source.getUpdatedBy());
            target.setUpdatedOn(source.getUpdatedOn());
        }
    }

}
