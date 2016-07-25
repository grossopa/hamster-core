/**
 * 
 */
package org.hamster.core.web.controller.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Getter
@Setter
public class ErrorDto<T> extends ResultDto<T> {
    private String code;
}
