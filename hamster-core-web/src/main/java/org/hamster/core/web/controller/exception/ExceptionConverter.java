/**
 * 
 */
package org.hamster.core.web.controller.exception;

import org.hamster.core.api.exception.dto.ExceptionDto;
import org.springframework.core.Ordered;

/**
 * to handle exception and convert it into standardized format
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface ExceptionConverter extends Ordered {

    /**
     * handle exception and return a exception dto
     * 
     * @param ex
     * @return
     */
    ExceptionDto handle(Exception ex);

    /**
     * determine if can handle the exception
     * 
     * @param ex
     * @return
     */
    boolean canHandle(Exception ex);
}
