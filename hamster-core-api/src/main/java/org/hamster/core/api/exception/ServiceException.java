/**
 * 
 */
package org.hamster.core.api.exception;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * A service exception indicates the actions of service is failing and need to be revealed to front-end user or tracked by audit system.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5091391282554626690L;

    private ExceptionCode exceptionCode;

    /**
     * default constructor
     * 
     * @param tempMessage
     * @param cause
     */
    private ServiceException(String tempMessage, Throwable cause) {
        super(tempMessage, cause);
    }

    /**
     * @return the exceptionCode, could be null
     */
    public ExceptionCode getExceptionCode() {
        return exceptionCode;
    }

    /**
     * helper method to generate an instance of ServiceException
     * 
     * @param code
     * @param cause
     * @param arguments
     * @return
     */
    public static ServiceException of(String code, Throwable cause, Object... arguments) {
        return of(code, null, cause, arguments);
    }

    /**
     * helper method to generate an instance of ServiceException
     * 
     * @param code
     * @param cause
     * @param message
     * @param arguments
     * @return
     */
    public static ServiceException of(String code, String message, Throwable cause, Object... arguments) {
        ExceptionCode exceptionCode = ExceptionCode.of(code);
        String tempMessage = message;
        if (exceptionCode != null && StringUtils.isBlank(message)) {
            tempMessage = exceptionCode.getMetadata().getMessage();
        }
        if (arguments != null && arguments.length > 0) {
            tempMessage = MessageFormat.format(tempMessage, arguments);
        }
        ServiceException se = new ServiceException(tempMessage, cause);
        se.exceptionCode = exceptionCode;
        return se;
    }

}
