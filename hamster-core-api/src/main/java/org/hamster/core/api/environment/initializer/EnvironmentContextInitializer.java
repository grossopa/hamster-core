/**
 * 
 */
package org.hamster.core.api.environment.initializer;

import org.hamster.core.api.environment.Environment;
import org.hamster.core.api.environment.builder.EnvironmentBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public abstract class EnvironmentContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.context.ApplicationContextInitializer#initialize(org.springframework.context.
     * ConfigurableApplicationContext)
     */
    @Override
    public void initialize(ConfigurableApplicationContext context) {
        Environment env = getBuilder().build();
        
        Assert.notNull(env, "Environment variable from EnvironmentBuilder must be non-null!");
        
        System.setProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME, env.toString());
        System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, env.toString());
    }
    
    abstract public EnvironmentBuilder getBuilder();

}
