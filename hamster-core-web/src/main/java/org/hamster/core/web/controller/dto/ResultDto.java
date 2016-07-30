/**
 * 
 */
package org.hamster.core.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
public class ResultDto<T> {
    private String message;
    private T data;
    
    /**
     * Create an instance of {@link ResultDto} with no message and no data returns
     * 
     * @return
     */
    public static final ResultDto<Void> of() {
        return ResultDto.<Void> of("", null);
    }

    /**
     * Create an instance of {@link ResultDto} with no data returns
     * 
     * @param message
     * @return
     */
    public static final ResultDto<Void> of(String message) {
        return ResultDto.<Void> of(message, null);
    }

    /**
     * Create an instance of {@link ResultDto}
     * 
     * @param message
     * @param data
     * @return
     */
    public static final <U> ResultDto<U> of(String message, U data) {
        ResultDto<U> dto = new ResultDto<>();
        dto.setMessage(message);
        dto.setData(data);
        return dto;
    }

    public static final <U> ResultDto<U> of(U data) {
        return of("", data);
    }

}
