/**
 * 
 */
package org.hamster.core.web.spring.boot;

import org.hamster.core.api.environment.initializer.DefaultEnvironmentContextInitializer;
import org.springframework.boot.SpringApplication;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractApplication {
    
    /**
     * create an application and register default initializers
     * 
     * @param clazz
     * @return
     */
    public static SpringApplication create(Class<?> clazz) {
        SpringApplication application = new SpringApplication(new Object[] {clazz});
        application.addInitializers(new DefaultEnvironmentContextInitializer());
        return application;
    }
}
