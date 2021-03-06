/**
 * 
 */
package org.hamster.core.api.util.difference.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
@ToString
public class DiffPath {
    /**
     * property name
     */
    private String property;
    
    /**
     * property type;
     */
    private Class<?> type;
    
    /**
     * children
     */
    private Map<String, DiffPath> children;
}
