/**
 * 
 */
package org.hamster.core.api.test.environment.builder;

import org.hamster.core.api.environment.Environment;
import org.hamster.core.api.environment.builder.DefaultEnvironmentBuilder;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultEnvironmentBuilderTest {

    @After
    public void after() {
        System.clearProperty(DefaultEnvironmentBuilder.ENVIRONMENT_VARIABLE);
    }

    @Test
    public void testBuilder() {

        DefaultEnvironmentBuilder builder = new DefaultEnvironmentBuilder();
        System.setProperty(DefaultEnvironmentBuilder.ENVIRONMENT_VARIABLE, Environment.LOCAL.toString());

        Assert.assertEquals(Environment.LOCAL, builder.build());
    }

    @Test
    public void testDefault() {
        DefaultEnvironmentBuilder builder = new DefaultEnvironmentBuilder();
        Assert.assertEquals(Environment.UNKNOWN, builder.build());
    }

}
