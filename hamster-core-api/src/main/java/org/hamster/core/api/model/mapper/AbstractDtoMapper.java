/**
 * 
 */
package org.hamster.core.api.model.mapper;

import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public abstract class AbstractDtoMapper<Src, Dto> implements DtoMapper<Src, Dto> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.model.mapper.DtoMapper#mapList(java.lang.Iterable)
     */
    @Override
    public List<Dto> mapList(Iterable<Src> srcs) {
        List<Dto> result = Lists.newArrayList();
        for (Src src : srcs) {
            Dto dto = map(src);
            if (dto != null) {
                result.add(dto);
            }
        }
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.model.mapper.DtoMapper#mapSet(java.lang.Iterable)
     */
    @Override
    public Set<Dto> mapSet(Iterable<Src> srcs) {
        Set<Dto> result = Sets.newHashSet();
        for (Src src : srcs) {
            Dto dto = map(src);
            if (dto != null) {
                result.add(dto);
            }
        }
        return result;
    }

}
