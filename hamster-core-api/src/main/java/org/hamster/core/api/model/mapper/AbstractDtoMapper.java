/**
 * 
 */
package org.hamster.core.api.model.mapper;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public abstract class AbstractDtoMapper<SRC, DTO> implements DtoMapper<SRC, DTO> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.model.mapper.DTOMapper#mapList(java.lang.Iterable)
     */
    @Override
    public List<DTO> mapList(Iterable<SRC> srcs) {
        List<DTO> result = Lists.newArrayList();
        map(result, srcs);
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.model.mapper.DTOMapper#mapSet(java.lang.Iterable)
     */
    @Override
    public Set<DTO> mapSet(Iterable<SRC> srcs) {
        Set<DTO> result = Sets.newHashSet();
        map(result, srcs);
        return result;
    }
    
    /**
     * mapping from src to dto
     * 
     * @param result
     * @param srcs
     */
    private void map(Collection<DTO> result, Iterable<SRC> srcs) {
        for (SRC src : srcs) {
            DTO dto = map(src);
            if (dto != null) {
                result.add(dto);
            }
        }
    }

}
