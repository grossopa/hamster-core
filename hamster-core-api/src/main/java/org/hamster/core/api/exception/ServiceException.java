/**
 * 
 */
package org.hamster.core.api.exception;

import java.text.MessageFormat;

import org.apache.commons.lang3.StringUtils;

/**
 * A service exception indicates the actions of service is failing and need to be revealed to front-end user or tracked
 * by audit system.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ServiceException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5091391282554626690L;

    private final String code;
    private ExceptionCode exceptionCode;

    /**
     * 
     * 
     * @param code
     *            the exception code
     */
    public ServiceException(String code, Object... arguments) {
        super();
        this.code = code;
    }

    /**
     * 
     * @param code
     *            the exception code
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public ServiceException(String code, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }

    /**
     * 
     * 
     * @param code
     *            the exception code
     * @param cause
     */
    public ServiceException(String code, Throwable cause, Object... arguments) {
        super(cause);
        this.code = code;
    }

    /**
     * 
     * @param code
     *            the exception code
     * @param message
     */
    public ServiceException(String code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 
     * @param code
     *            the exception code
     * @param message
     * @param cause
     */
    public ServiceException(String code, String message, Throwable cause, Object... arguments) {
        super(message, cause);
        this.code = code;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @return the exceptionCode
     */
    public ExceptionCode getExceptionCode() {
        return exceptionCode;
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
    public static ServiceException of(String code, Throwable cause, String message, Object... arguments) {
        ExceptionCode exceptionCode = ExceptionCode.of(code);
        String tempMessage = message;
        if (exceptionCode != null && StringUtils.isBlank(message)) {
            tempMessage = exceptionCode.getMetadata().getMessage();
        }
        if (arguments != null && arguments.length > 0) {
            tempMessage = MessageFormat.format(tempMessage, arguments);
        }
        ServiceException se = new ServiceException(code, cause, tempMessage);
        se.exceptionCode = exceptionCode;
        return se;
    }

}
