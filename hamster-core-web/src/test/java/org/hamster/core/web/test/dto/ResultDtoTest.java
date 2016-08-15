/**
 * 
 */
package org.hamster.core.web.test.dto;

import org.hamster.core.web.controller.dto.ResultDto;
import org.junit.Assert;
import org.junit.Test;


/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ResultDtoTest {
    @Test
    public void testOf() {
        ResultDto<Void> dto = ResultDto.of("some message");
        Assert.assertEquals("some message", dto.getMessage());
    }
    
    @Test
    public void testOfWithData() {
        Object o = new Object();
        ResultDto<Object> dto = ResultDto.of("some message", o);
        Assert.assertEquals("some message", dto.getMessage());
        Assert.assertEquals(o, dto.getData());
    }
    
}
