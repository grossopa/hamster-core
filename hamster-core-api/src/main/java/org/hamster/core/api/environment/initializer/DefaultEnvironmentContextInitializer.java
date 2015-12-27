/**
 * 
 */
package org.hamster.core.api.environment.initializer;

import org.hamster.core.api.environment.builder.DefaultEnvironmentBuilder;
import org.hamster.core.api.environment.builder.EnvironmentBuilder;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DefaultEnvironmentContextInitializer extends EnvironmentContextInitializer {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.environment.initializer.EnvironmentContextInitializer#getBuilder()
     */
    @Override
    public EnvironmentBuilder getBuilder() {
        return new DefaultEnvironmentBuilder();
    }

}
