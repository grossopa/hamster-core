/**
 * 
 */
package org.hamster.core.api.exception.meta;

import java.text.MessageFormat;

import org.hamster.core.api.exception.ExceptionMetadata;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DefaultExceptionMetadata implements ExceptionMetadata {
    
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

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.exception.ExceptionMetadata#getMessage(java.lang.Object[])
     */
    @Override
    public String getMessage(Object... params) {
        return MessageFormat.format(message, params);
    }

}
