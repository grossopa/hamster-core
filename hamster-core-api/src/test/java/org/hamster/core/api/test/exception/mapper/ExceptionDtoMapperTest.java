/**
 * 
 */
package org.hamster.core.api.test.exception.mapper;

import org.hamster.core.api.exception.ExceptionCode;
import org.hamster.core.api.exception.ServiceException;
import org.hamster.core.api.exception.dto.ExceptionDto;
import org.hamster.core.api.exception.dto.ExceptionDtoMapper;
import org.hamster.core.api.exception.meta.DefaultExceptionMetadata;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ExceptionDtoMapperTest {
    
    @Before
    public void before() {
        ExceptionCode.register("ENGA001", new DefaultExceptionMetadata("here is some apples"));
        ExceptionCode.register("ENGA002", new DefaultExceptionMetadata("here is some apples {0} {1}"));
    }

    @Test
    public void testMapperGeneral() {
        Exception e = new Exception("generalException");
        ExceptionDtoMapper mapper = new ExceptionDtoMapper();
        ExceptionDto result = mapper.map(e);
        Assert.assertNull(result.getCode());
        Assert.assertEquals("generalException", e.getMessage());
    }

    @Test
    public void testMapperServiceException() {
        ServiceException se = ServiceException.of("ENGA001", "there is no cow level", null);
        
        ExceptionDtoMapper mapper = new ExceptionDtoMapper();
        ExceptionDto result = mapper.map(se);
        Assert.assertEquals("ENGA001", result.getCode());
        Assert.assertEquals("there is no cow level", result.getMessage());
    }
    
    @Test
    public void testMapperServiceException2() {
        ServiceException se = ServiceException.of("ENGA001", null);
        
        ExceptionDtoMapper mapper = new ExceptionDtoMapper();
        ExceptionDto result = mapper.map(se);
        Assert.assertEquals("ENGA001", result.getCode());
        Assert.assertEquals("here is some apples", result.getMessage());
    }
    
    @Test
    public void testMapperServiceExceptionArguments() {
        ServiceException se = ServiceException.of("ENGA002", null, "aaa", "bbb");
        
        ExceptionDtoMapper mapper = new ExceptionDtoMapper();
        ExceptionDto result = mapper.map(se);
        Assert.assertEquals("ENGA002", result.getCode());
        Assert.assertEquals("here is some apples aaa bbb", result.getMessage());
    }
}
