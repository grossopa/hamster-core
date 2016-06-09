/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import lombok.Getter;
import lombok.Setter;

/**
 * difference on property level
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Getter
@Setter
public class DiffVO {
    private String property;
    private Object oldValue;
    private Object newValue;
}
