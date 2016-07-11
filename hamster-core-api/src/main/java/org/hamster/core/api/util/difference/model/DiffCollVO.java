/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class DiffCollVO extends DiffVO {
    private Set<String> properties = Sets.newHashSet();
    private List<DiffObjectVO> objectList = Lists.newArrayList();
}
