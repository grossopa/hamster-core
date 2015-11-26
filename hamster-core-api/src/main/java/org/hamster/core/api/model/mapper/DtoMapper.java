/**
 * 
 */
package org.hamster.core.api.model.mapper;

import java.util.List;
import java.util.Set;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface DtoMapper<Src, Dto> {

    /**
     * map source as list
     * 
     * @param srcs
     * @return
     */
    List<Dto> mapList(Iterable<Src> srcs);

    /**
     * map source as set
     * 
     * @param srcs
     * @return
     */
    Set<Dto> mapSet(Iterable<Src> srcs);

    /**
     * map src to dto, return null if the src should be skipped
     * 
     * @param src
     * @return
     */
    Dto map(Src src);
}
