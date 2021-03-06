/**
 * 
 */
package org.hamster.core.test.helper;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Assert;

/**
 * asserts utility
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class Asserts extends Assert {

    /**
     * @throws AssertionError
     */
    private Asserts() {
       throw new AssertionError(); 
    }

    /**
     * Delegate of {@link Matchers#greaterThan(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertGreaterThan(T expected, T actual) {
        MatcherAssert.assertThat(actual, Matchers.greaterThan(expected));
    }

    /**
     * Delegate of {@link Matchers#greaterThanOrEqualTo(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertGreaterThanOrEqualTo(T expected, T actual) {
        MatcherAssert.assertThat(actual, Matchers.greaterThanOrEqualTo(expected));
    }

    /**
     * Delegate of {@link Matchers#greaterThan(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertGreaterThan(String message, T expected, T actual) {
        MatcherAssert.assertThat(message, actual, Matchers.greaterThan(expected));
    }

    /**
     * Delegate of {@link Matchers#greaterThanOrEqualTo(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertGreaterThanOrEqualTo(String message, T expected, T actual) {
        MatcherAssert.assertThat(message, actual, Matchers.greaterThanOrEqualTo(expected));
    }

    /**
     * Delegate of {@link Matchers#lessThan(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertLessThan(T expected, T actual) {
        MatcherAssert.assertThat(actual, Matchers.lessThan(expected));
    }

    /**
     * Delegate of {@link Matchers#lessThanOrEqualTo(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertLessThanOrEqualTo(T expected, T actual) {
        MatcherAssert.assertThat(actual, Matchers.lessThanOrEqualTo(expected));
    }

    /**
     * Delegate of {@link Matchers#lessThan(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertLessThan(String message, T expected, T actual) {
        MatcherAssert.assertThat(message, actual, Matchers.lessThan(expected));
    }

    /**
     * Delegate of {@link Matchers#lessThanOrEqualTo(Comparable)}
     * 
     * @param expected
     * @param actual
     */
    public static <T extends Comparable<T>> void assertLessThanOrEqualTo(String message, T expected, T actual) {
        MatcherAssert.assertThat(message, actual, Matchers.lessThanOrEqualTo(expected));
    }

    public static <E extends Throwable> void assertThrown(Class<E> expectedThrowable, ThrowableRunner consumer) {
        org.springframework.util.Assert.notNull(consumer, "consumer cannot be null");
        boolean caught = false;
        try {
            consumer.accept();
        } catch (Throwable throwable) {
            assertEquals("Caught throwables classes are different with expected type.", expectedThrowable,
                    throwable.getClass());
            caught = true;
        }
        assertTrue(caught);
    }

    /**
     * to accept some logics and throw out the expected exception
     *
     * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
     * @since 1.0
     */
    @FunctionalInterface
    public static interface ThrowableRunner {
        /**
         * to accept with 
         * 
         * @throws Throwable
         */
        void accept() throws Throwable;
    }

}
