/**
 * 
 */
package org.hamster.core.api.environment;

/**
 * Defines all possible environments
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public enum Environment {
    UNKNOWN, LOCAL, DEV, UAT, PROD;

    private static Environment currentEnvironment;

    /**
     * initializes environment
     * 
     * @param environment
     */
    public static void initializeEnvironment(Environment environment) {
        if (currentEnvironment != null && currentEnvironment != environment) {
            throw new IllegalArgumentException(
                    "Environment already set to " + currentEnvironment + "Cannot be changed to " + environment);
        } else if (currentEnvironment == null) {
            synchronized (Environment.class) {
                currentEnvironment = environment;
            }
        }
    }

    /**
     * Clean up current environment, only for testing propose
     */
    public static void cleanup() {
        currentEnvironment = null;
    }

    /**
     * returns current environment, throws exception if environment not set
     * 
     * @return
     */
    public static final Environment current() {
        if (currentEnvironment == null) {
            throw new NullPointerException("Environment not set");
        }
        return currentEnvironment;
    }

    public static final boolean isProd() {
        return current() == Environment.PROD;
    }

    /**
     * is any of the environments
     * 
     * @param envs
     * @return
     */
    public static final boolean isOneOf(Environment... envs) {
        for (Environment env : envs) {
            if (current() == env) {
                return true;
            }
        }
        return false;

    }
}
