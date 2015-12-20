package org.hamster.core.api.test.config;

import org.hamster.core.api.config.AbstractAppConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/hamster-config-test.xml"})
public class AppConfigTest {
    
    @Autowired
    private AbstractAppConfig appConfig;
    
    @Test
    public void verifyInjection() {
        Assert.assertNotNull(appConfig);
        Assert.assertNotNull(appConfig.getVersion());
    } 
    
}
