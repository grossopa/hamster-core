/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DiffCollVO extends DiffVO {
    private List<String> properties = Lists.newArrayList();
    private List<DiffObjectVO> objectList = Lists.newArrayList();
}
