/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Data
public class DiffResultVO {
    private List<String> properties = Lists.newArrayList();
    private List<DiffObjectVO> objectList = Lists.newArrayList();
}
