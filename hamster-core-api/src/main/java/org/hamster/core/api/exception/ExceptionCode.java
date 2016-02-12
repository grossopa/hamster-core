/**
 * 
 */
package org.hamster.core.api.exception;

import org.springframework.util.Assert;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ExceptionCode {
    
    public static void register(String code, ExceptionMetadata metadata) {
        
    }

    /**
     * the exception code string
     */
    private final String code;
    
    /**
     * the metadata holds additional information
     */
    private final ExceptionMetadata metadata;

    /**
     * constructor
     * 
     * @param code
     *            the code of the exception
     */
    ExceptionCode(String code, ExceptionMetadata metadata) {
        Assert.hasText(code);
        Assert.notNull(metadata);
        this.code = code;
        this.metadata = metadata;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }
    
    public ExceptionMetadata getMetadata() {
        return this.metadata;
    }

}
