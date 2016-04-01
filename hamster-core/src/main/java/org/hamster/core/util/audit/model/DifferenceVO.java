/**
 * 
 */
package org.hamster.core.util.audit.model;

import lombok.Data;

/**
 * difference on property level
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Data
public class DifferenceVO {
    private String property;
    private Object oldValue;
    private Object newValue;
}
