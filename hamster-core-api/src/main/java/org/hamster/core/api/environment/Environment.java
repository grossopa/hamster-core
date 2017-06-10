/**
 * 
 */
package org.hamster.core.api.environment;

import org.apache.commons.lang3.StringUtils;

/**
 * All pre-defined environments
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class Environment {
    public static final String UNKNOWN = "UNKNOWN";
    public static final String LOCAL = "LOCAL";
    public static final String DEV = "DEV";
    public static final String INT = "INT";
    public static final String UAT = "UAT";
    public static final String PROD = "PROD";
    
    private static final Environment global = new Environment();
    
    private String currentEnvironment;
    
    /**
     * get global environment instance
     * 
     * @return
     */
    public static final Environment global() {
        return global;
    }
    
    /**
     * initializes environment
     * 
     * @param environment
     */
    public void initializeEnvironment(String environment) {
        if (StringUtils.isNotBlank(currentEnvironment)) {
            throw new IllegalArgumentException(
                    "Environment already set to " + currentEnvironment + "Cannot be changed to " + environment);
        } else if (currentEnvironment == null) {
            synchronized (this) {
                currentEnvironment = StringUtils.defaultIfBlank(environment, UNKNOWN).toUpperCase();
            }
        }
    }

    /**
     * Clean up current environment, only for testing propose
     */
    public void cleanup() {
        currentEnvironment = null;
    }

    /**
     * returns current environment, throws exception if environment not set
     * 
     * @return
     */
    public String current() {
        if (StringUtils.isBlank(currentEnvironment)) {
            throw new NullPointerException("Environment not set");
        }
        return currentEnvironment;
    }

    public boolean isProd() {
        return Environment.PROD.equals(current());
    }

    /**
     * is any of the environments
     * 
     * @param envs
     * @return
     */
    public boolean isOneOf(String... envs) {
        for (String env : envs) {
            if (StringUtils.equalsIgnoreCase(env, currentEnvironment)) {
                return true;
            }
        }
        return false;

    }
}
