/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiffObjectVO {
    private DiffType type;
    private Object id;
    private Map<String, DiffVO> propertyList;
}
