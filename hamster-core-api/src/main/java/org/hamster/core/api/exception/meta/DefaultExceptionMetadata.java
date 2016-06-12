/**
 * 
 */
package org.hamster.core.api.exception.meta;

import org.hamster.core.api.exception.ExceptionMetadata;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DefaultExceptionMetadata implements ExceptionMetadata {

    private static final long serialVersionUID = 8443383003863034072L;
    
    private final String message;

    /**
     * constructor
     * 
     * @param message
     */
    public DefaultExceptionMetadata(String message) {
        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.exception.ExceptionMetadata#getMessage()
     */
    @Override
    public String getMessage() {
        return this.message;
    }

}
