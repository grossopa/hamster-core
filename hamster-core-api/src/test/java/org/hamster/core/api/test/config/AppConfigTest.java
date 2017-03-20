package org.hamster.core.api.test.config;

import org.hamster.core.api.config.AbstractAppConfig;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@Ignore
public class AppConfigTest {

    @Autowired
    private AbstractAppConfig appConfig;

    @Test
    public void verifyInjection() {
        Assert.assertNotNull(appConfig);
        Assert.assertNotNull(appConfig.getVersion());
        Assert.assertEquals("Unknown", appConfig.getEnvironment());
    }

}
