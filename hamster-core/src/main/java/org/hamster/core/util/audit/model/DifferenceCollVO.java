/**
 * 
 */
package org.hamster.core.util.audit.model;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * collection level
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DifferenceCollVO extends DifferenceVO {
    
    private List<DifferenceObjectVO> objectList = Lists.newArrayList();

    /**
     * @return the objectList
     */
    public List<DifferenceObjectVO> getObjectList() {
        return objectList;
    }

}
