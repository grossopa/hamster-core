/**
 * 
 */
package org.hamster.core.util.audit.model;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.Data;

/**
 * Difference on row/object level
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Data
public class DifferenceObjectVO {
    private DifferenceType type;
    private Object id;
    private Map<String, DifferenceVO> propertyList;

    /**
     * constructor for ADD
     */
    public DifferenceObjectVO() {
        this(DifferenceType.ADD, null);
    }
    
    /**
     * constructor
     * 
     * @param type
     * @param id
     */
    public DifferenceObjectVO(DifferenceType type, Object id) {
        this.type = type;
        this.id = id;
        this.propertyList = Maps.newHashMap();
    }
}
