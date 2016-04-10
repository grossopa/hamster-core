/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Data
public class DiffObjectVO {
    private DiffType type;
    private Object id;
    private Map<String, DiffVO> propertyList;

    /**
     * constructor for ADD
     */
    public DiffObjectVO() {
        this(DiffType.ADD, null);
    }
    
    /**
     * constructor
     * 
     * @param type
     * @param id
     */
    public DiffObjectVO(DiffType type, Object id) {
        this.type = type;
        this.id = id;
        this.propertyList = Maps.newHashMap();
    }
}
