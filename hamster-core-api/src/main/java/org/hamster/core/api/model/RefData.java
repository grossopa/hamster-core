/**
 * 
 */
package org.hamster.core.api.model;

import org.hamster.core.api.model.base.ManageableIfc;
import org.hamster.core.api.model.base.OrderIfc;

/**
 * reference data model. it represents a simple key value structure with parent-children tree layout.<br>
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface RefData extends ManageableIfc<Long>, OrderIfc<Long> {
    /**
     * @return the key
     */
    String getKey();

    /**
     * @return the value
     */
    String getValue();

    /**
     * @return the label
     */
    String getLabel();

    /**
     * @param key
     */
    void setKey(String key);

    /**
     * @param value
     */
    void setValue(String value);

    /**
     * @param label
     */
    void setLabel(String label);
}
