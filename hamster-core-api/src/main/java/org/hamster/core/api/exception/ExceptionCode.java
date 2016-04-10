/**
 * 
 */
package org.hamster.core.api.exception;

import java.io.Serializable;
import java.util.Map;

import org.springframework.util.Assert;

import com.google.common.collect.Maps;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ExceptionCode implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1169960376375134384L;

    private static final Map<String, ExceptionCode> pool = Maps.newConcurrentMap();

    /**
     * register an exception
     * 
     * @param code
     * @param metadata
     */
    public static void register(String code, ExceptionMetadata metadata) {
        ExceptionCode exceptionCode = new ExceptionCode(code, metadata);
        pool.put(code, exceptionCode);
    }

    /**
     * find exception code from string
     * 
     * @param code
     * @return
     */
    public static ExceptionCode of(String code) {
        return pool.get(code);
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
