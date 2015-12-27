/**
 * 
 */
package org.hamster.core.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;


/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Configuration
public class AbstractAppConfig {
    
    private @Value("${app.version}") String version;
    private @Value("${app.environment}") String environment;

    /**
     * @return the version
     */
    public String getVersion() {
        return version;
    }

    /**
     * @return the environment
     */
    public String getEnvironment() {
        return environment;
    }
    
}
