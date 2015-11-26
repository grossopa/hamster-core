/**
 * 
 */
package org.hamster.core.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hamster.core.api.model.RefData;
import org.hamster.core.dao.consts.EntityConsts;
import org.hamster.core.dao.entity.base.ManageableEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Entity
@Table(name = EntityConsts.DB_PREFIX + "ref_data")
public class RefDataEntity extends ManageableEntity implements RefData {
    public static final String COL_KEY = "key_";
    public static final String COL_VALUE = "value_";
    public static final String COL_LABEL = "label";
    public static final String COL_ORDER = "order";

    @Length(max = 100)
    @NotBlank
    @Column(name = COL_KEY, length = 100, nullable = false)
    private String key;

    @Length(max = 250)
    @Column(name = COL_VALUE, length = 100, nullable = true)
    private String value;

    @Length(max = 250)
    @Column(name = COL_LABEL, length = 100, nullable = true)
    private String label;

    @Column(name = COL_ORDER, nullable = true)
    private Integer order;

    /**
     * @return the key
     */

    public String getKey() {
        return key;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @return the order
     */
    public Integer getOrder() {
        return order;
    }

    /**
     * @param order
     *            the order to set
     */
    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * @param key
     *            the key to set
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @param label
     *            the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

}
