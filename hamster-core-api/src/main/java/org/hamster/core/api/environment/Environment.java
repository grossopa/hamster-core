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
        if (currentEnvironment != null) {
            throw new IllegalArgumentException("Environment already set");
        }
        synchronized (Environment.class) {
            currentEnvironment = environment;
        }
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
}
