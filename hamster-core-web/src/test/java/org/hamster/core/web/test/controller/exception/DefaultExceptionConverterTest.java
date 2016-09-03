/**
 * 
 */
package org.hamster.core.web.test.controller.exception;

import org.hamster.core.api.exception.ExceptionCode;
import org.hamster.core.api.exception.ExceptionMetadata;
import org.hamster.core.api.exception.ServiceException;
import org.hamster.core.api.exception.dto.ExceptionDto;
import org.hamster.core.web.controller.exception.DefaultExceptionConverter;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultExceptionConverterTest {
    @Test
    public void testCanHandle() {
        Exception e = new Exception("Error occurs, please check.");
        DefaultExceptionConverter converter = new DefaultExceptionConverter();
        Assert.assertTrue(converter.canHandle(e));
    }
    
    @Test
    public void testHandle() {
        Exception e = new Exception("Error occurs, please check.");
        DefaultExceptionConverter converter = new DefaultExceptionConverter();
        converter.setOrder(1);
        Assert.assertEquals(1, converter.getOrder());
        ExceptionDto dto = converter.handle(e);
        
        Assert.assertEquals(e.getMessage(), dto.getMessage());
        Assert.assertNull(dto.getCode());
    }
    
    @Test
    public void testHandleServiceException() {
        ExceptionCode.register("code123", new ExceptionMetadata() {

            private static final long serialVersionUID = -6172347072627464109L;

            @Override
            public String getMessage() {
                return "No cow level";
            }
            
        });
        
        Exception e = ServiceException.of("code123", null);
        DefaultExceptionConverter converter = new DefaultExceptionConverter();
        ExceptionDto dto = converter.handle(e);
        
        Assert.assertEquals("No cow level", dto.getMessage());
        Assert.assertEquals("code123", dto.getCode());
    }
    
}
