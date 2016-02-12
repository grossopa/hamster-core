/**
 * 
 */
package org.hamster.core.api.exception;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface ExceptionMetadata {
    /**
     * 
     * @return the plain message
     */
    String getMessage();

    /**
     * 
     * @param params
     * @return the message with params
     */
    String getMessage(Object... params);
}
