/**
 * 
 */
package org.hamster.core.api.test.utils.difference.comparator;

import java.lang.reflect.Method;

import org.hamster.core.api.util.difference.comparator.defaults.AbstractPropertyComparator;
import org.hamster.core.test.helper.Asserts;
import org.junit.Test;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class AbstractPropertyComparatorTest {
    private AbstractPropertyComparator comparator = new AbstractPropertyComparator() {

        @Override
        public boolean canCompare(String property, Method getterMethod) {
            return true;
        }

        @Override
        protected int doCompare(Object o1, Object o2) {
            return 3;
        }
    };

    @Test
    public void testLeftNull() {
        Asserts.assertEquals(1, comparator.compare(null, new Object()));
    }

    @Test
    public void testRightNull() {
        Asserts.assertEquals(-1, comparator.compare(new Object(), null));
    }

    @Test
    public void testBothNull() {
        Asserts.assertEquals(0, comparator.compare(null, null));
    }

    @Test
    public void testNeitherNull() {
        Asserts.assertEquals(3, comparator.compare(new Object(), new Object()));
    }
}
