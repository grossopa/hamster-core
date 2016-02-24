/**
 * 
 */
package org.hamster.core.util.audit.model;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Difference on row/object level
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
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

    /**
     * @return the type
     */
    public DifferenceType getType() {
        return type;
    }

    /**
     * @return the id
     */
    public Object getId() {
        return id;
    }

    /**
     * @return the propertyList
     */
    public Map<String, DifferenceVO> getPropertyList() {
        return propertyList;
    }

    /**
     * @param type
     *            the type to set
     */
    public void setType(DifferenceType type) {
        this.type = type;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Object id) {
        this.id = id;
    }


}
