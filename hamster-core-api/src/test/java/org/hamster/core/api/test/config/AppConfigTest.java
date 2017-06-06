package org.hamster.core.api.test.config;

import org.hamster.core.api.config.AbstractAppConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class AppConfigTest {

    private AbstractAppConfig appConfig = new AbstractAppConfig();

    @Test
    public void verifyInjection() {
        Assert.assertNotNull(appConfig);
        Assert.assertNull(appConfig.getVersion());
        Assert.assertNull(appConfig.getEnvironment());
    }

}
