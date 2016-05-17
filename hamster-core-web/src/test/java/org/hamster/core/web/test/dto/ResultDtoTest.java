/**
 * 
 */
package org.hamster.core.web.test.dto;

import org.hamster.core.web.controller.dto.ResultDto;
import org.junit.Test;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ResultDtoTest {
    @Test
    public void testOf() {
        ResultDto.of("some message");
    }
    
    @Test
    public void testCoverage() {
        DtoCoverager.of(ResultDto.of("new message")).coverAll();
    }
}
