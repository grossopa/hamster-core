/**
 * 
 */
package org.hamster.core.api.environment.initializer;

import org.hamster.core.api.environment.Environment;
import org.hamster.core.api.environment.builder.EnvironmentBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class LocalEnvironmentContextInitializer extends EnvironmentContextInitializer {
    /**
     * 
     */
    private static final Logger log = LoggerFactory.getLogger(LocalEnvironmentContextInitializer.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.environment.initializer.EnvironmentContextInitializer#getBuilder()
     */
    @Override
    public EnvironmentBuilder getBuilder() {
        return new EnvironmentBuilder() {

            @Override
            public Environment build() {
                log.info("Current environment : LOCAL");
                return Environment.LOCAL;
            }

        };
    }

}
