/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
public class DiffResultVO {
    private List<String> properties = Lists.newArrayList();
    private List<DiffObjectVO> objectList = Lists.newArrayList();
}
