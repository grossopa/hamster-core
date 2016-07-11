/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Maps;
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
public class DiffResultVO<K, T> {
    private Set<String> properties = Sets.newHashSet();
    private Map<DiffType, List<DiffObjectVO>> results = Maps.newEnumMap(DiffType.class);
    private Collection<T> addedColl;
    private Map<K, T> removedColl;
    private Map<K, Pair<T, T>> changedColl;

    public boolean hasChange() {
        return CollectionUtils.isNotEmpty(addedColl) || MapUtils.isNotEmpty(removedColl) || MapUtils.isNotEmpty(changedColl);
    }
}
