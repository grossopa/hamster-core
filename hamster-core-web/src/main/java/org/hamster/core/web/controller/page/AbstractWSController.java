/**
 * 
 */
package org.hamster.core.web.controller.page;

import org.hamster.core.api.exception.dto.ExceptionDto;
import org.hamster.core.web.controller.AbstractController;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractWSController extends AbstractController {

    /**
     * 
     * @param ex
     * @return
     */
    @ExceptionHandler
    public ExceptionDto exceptionHandler(Exception ex) {
        return handleException(ex);
    }
}
