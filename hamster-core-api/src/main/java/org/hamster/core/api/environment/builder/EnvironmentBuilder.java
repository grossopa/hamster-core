/**
 * 
 */
package org.hamster.core.api.environment.builder;

import org.hamster.core.api.environment.Environment;

/**
 * Build the environment
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface EnvironmentBuilder {
    
    /**
     * Build the environment
     * 
     * @return the built environment, must be non-null
     */
    Environment build();
}
