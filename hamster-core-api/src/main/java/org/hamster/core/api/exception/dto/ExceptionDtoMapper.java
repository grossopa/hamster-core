/**
 * 
 */
package org.hamster.core.api.exception.dto;

import org.hamster.core.api.exception.ServiceException;
import org.hamster.core.api.model.mapper.AbstractDtoMapper;

/**
 * DtoMapper for ExceptionDto and Exception
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ExceptionDtoMapper extends AbstractDtoMapper<Exception, ExceptionDto> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.model.mapper.DtoMapper#map(java.lang.Object)
     */
    @Override
    public ExceptionDto map(Exception exception) {
        ExceptionDto dto = new ExceptionDto();
        if (exception instanceof ServiceException) {
            ServiceException se = (ServiceException) exception;
            if (se.getExceptionCode() != null) {
                dto.setCode(se.getExceptionCode().getCode());
            }
        }
        dto.setMessage(exception.getMessage());
        return dto;
    }
}
