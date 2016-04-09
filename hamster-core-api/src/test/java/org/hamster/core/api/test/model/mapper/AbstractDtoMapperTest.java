/**
 * 
 */
package org.hamster.core.api.test.model.mapper;

import java.util.List;
import java.util.Set;

import org.hamster.core.api.model.mapper.AbstractDtoMapper;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class AbstractDtoMapperTest {

    @Test
    public void testList() {
        List<Foo> srcList = Lists.newArrayList();
        srcList.add(new Foo("1", "John"));
        srcList.add(new Foo("2", "Doe"));
        srcList.add(null);
        
        List<FooDto> dtoList = new FooDtoMapper().mapList(srcList);
        Assert.assertEquals(2, dtoList.size());
        Assert.assertEquals(srcList.get(0).getId(), dtoList.get(0).getId());
        Assert.assertEquals(srcList.get(1).getId(), dtoList.get(1).getId());
    }
    
    @Test
    public void testSet() {
        Set<Foo> srcList = Sets.newHashSet();
        srcList.add(new Foo("1", "John"));
        srcList.add(new Foo("2", "Doe"));
        
        Set<FooDto> dtoList = new FooDtoMapper().mapSet(srcList);
        Assert.assertEquals(2, dtoList.size());
    }
}

class FooDtoMapper extends AbstractDtoMapper<Foo, FooDto> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.model.mapper.DtoMapper#map(java.lang.Object)
     */
    @Override
    public FooDto map(Foo src) {
        if (src == null) {
            return null;
        }
        FooDto dto = new FooDto();
        dto.setId(src.getId());
        dto.setName(src.getName());
        return dto;
    }

}

@Data
@AllArgsConstructor
class Foo {
    String id;
    String name;
}

@Data
class FooDto {
    String id;
    String name;
}