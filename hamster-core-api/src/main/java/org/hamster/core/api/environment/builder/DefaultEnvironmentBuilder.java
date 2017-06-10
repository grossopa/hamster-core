/**
 * 
 */
package org.hamster.core.api.environment.builder;

import org.apache.commons.lang3.StringUtils;
import org.hamster.core.api.environment.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default environment builder to get from Environment Variable or System Property
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DefaultEnvironmentBuilder implements EnvironmentBuilder {

    private static final Logger log = LoggerFactory.getLogger(DefaultEnvironmentBuilder.class);

    public static final String ENVIRONMENT_VARIABLE = "hm.environment";

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.environment.builder.EnvironmentBuilder#build()
     */
    @Override
    public String build() {
        String source = "System Environment Variable";

        String env = System.getenv(ENVIRONMENT_VARIABLE);

        if (StringUtils.isEmpty(env)) {
            source = "System Property";
            env = System.getProperty(ENVIRONMENT_VARIABLE);
        }

        if (StringUtils.isEmpty(env)) {
            log.warn("No Environment Variable nor System Property found for {}, defaulted to be UNKNOWN.",
                    ENVIRONMENT_VARIABLE);
            return Environment.UNKNOWN;
        }

        env = env.toUpperCase();

        log.info("Current environment : {}, source : {}", env, source);
        return env;
    }
}
