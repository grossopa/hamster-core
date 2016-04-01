/**
 * 
 */
package org.hamster.core.util.audit.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * collection level
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DifferenceCollVO extends DifferenceVO {
    private List<String> properties = Lists.newArrayList();
    private List<DifferenceObjectVO> objectList = Lists.newArrayList();
}
