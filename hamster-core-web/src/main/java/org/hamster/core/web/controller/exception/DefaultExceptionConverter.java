/**
 * 
 */
package org.hamster.core.web.controller.exception;

import org.hamster.core.api.exception.dto.ExceptionDto;
import org.hamster.core.api.exception.dto.ExceptionDtoMapper;

/**
 * map exception to exception dto
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultExceptionConverter extends AbstractExceptionConverter {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.web.controller.exception.ExceptionHandler#handle(java.lang.Exception)
     */
    @Override
    public ExceptionDto handle(Exception ex) {
        return new ExceptionDtoMapper().map(ex);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.web.controller.exception.ExceptionHandler#canHandle(java.lang.Exception)
     */
    @Override
    public boolean canHandle(Exception ex) {
        return true;
    }

}
