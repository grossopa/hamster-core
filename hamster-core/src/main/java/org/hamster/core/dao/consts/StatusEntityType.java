/**
 * 
 */
package org.hamster.core.dao.consts;

/**
 * active/inactive/history
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class StatusEntityType {

    /**
     * the record is in normal state
     */
    public static final String ACTIVE = "active";

    /**
     * the record has been deleted
     */
    public static final String INACTIVE = "inactive";

    /**
     * the record is inactive and be treated as a history
     */
    public static final String HISTORY = "history";
}
