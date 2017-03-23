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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * a ref data is for storing and managing the key / value pairs used in application.
 * Usually the data size is not large and can be loaded into memory periodically. 
 * Hence no index is required
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Entity
@Table(name = EntityConsts.DB_PREFIX + "ref_data")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefDataEntity extends ManageableEntity implements RefData {
    public static final String COL_KEY = "key_";
    public static final String COL_VALUE = "value_";
    public static final String COL_LABEL = "label_";
    public static final String COL_ORDER = "order_";

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
}
