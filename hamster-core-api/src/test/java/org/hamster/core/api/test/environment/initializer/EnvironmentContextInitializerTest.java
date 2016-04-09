/**
 * 
 */
package org.hamster.core.api.test.environment.initializer;

import org.hamster.core.api.environment.Environment;
import org.hamster.core.api.environment.builder.DefaultEnvironmentBuilder;
import org.hamster.core.api.environment.initializer.DefaultEnvironmentContextInitializer;
import org.hamster.core.api.environment.initializer.EnvironmentContextInitializer;
import org.hamster.core.api.environment.initializer.LocalEnvironmentContextInitializer;
import org.hamster.core.api.environment.initializer.UnknownEnvironmentContextInitializer;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.env.AbstractEnvironment;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class EnvironmentContextInitializerTest {

    @After
    public void after() {
        System.clearProperty(DefaultEnvironmentBuilder.ENVIRONMENT_VARIABLE);
        System.clearProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME);
        System.clearProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME);
    }

    @Test
    public void testLocalInitializer() {
        EnvironmentContextInitializer initializer = new LocalEnvironmentContextInitializer();
        initializer.initialize(null);
        assertEnv(Environment.LOCAL.toString());
    }

    @Test
    public void testUnknownInitializer() {
        EnvironmentContextInitializer initializer = new UnknownEnvironmentContextInitializer();
        initializer.initialize(null);
        assertEnv(Environment.UNKNOWN.toString());
    }
    
    @Test
    public void testDefaultInitializer() {
        EnvironmentContextInitializer initializer = new DefaultEnvironmentContextInitializer();
        initializer.initialize(null);
        assertEnv(Environment.UNKNOWN.toString());
    }

    public void assertEnv(String expected) {
        Assert.assertEquals(expected, System.getProperty(AbstractEnvironment.DEFAULT_PROFILES_PROPERTY_NAME));
        Assert.assertEquals(expected, System.getProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME));
    }
}
