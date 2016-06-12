/**
 * 
 */
package org.hamster.core.api.exception;

import java.io.Serializable;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface ExceptionMetadata extends Serializable {
    /**
     * 
     * @return the message
     */
    String getMessage();
}
