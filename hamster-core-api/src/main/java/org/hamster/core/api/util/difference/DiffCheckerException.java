/**
 * 
 */
package org.hamster.core.api.util.difference;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DiffCheckerException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 2743312705966840095L;

    /**
     * 
     */
    public DiffCheckerException() {
        super();
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public DiffCheckerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * @param message
     * @param cause
     */
    public DiffCheckerException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message
     */
    public DiffCheckerException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DiffCheckerException(Throwable cause) {
        super(cause);
    }

}
