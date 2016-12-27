/**
 * 
 */
package org.hamster.core.dao.test.util.comparator;

import org.hamster.core.dao.entity.base.IdEntity;
import org.hamster.core.dao.util.comparator.IdEntityComparator;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class IdEntityComparatorTest {
    private final IdEntityComparator<Long> comparator = new IdEntityComparator<Long>();

    @Test
    public void testCompareLeftNull() {
        Assert.assertEquals(1, comparator.compare(null, new TestIdEntity(1L)));
    }

    @Test
    public void testCompareLeftIdNull() {
        Assert.assertEquals(1, comparator.compare(new TestIdEntity(null), new TestIdEntity(1L)));
    }

    @Test
    public void testCompareRightNull() {
        Assert.assertEquals(-1, comparator.compare(new TestIdEntity(1L), null));
    }

    @Test
    public void testCompareRightIdNull() {
        Assert.assertEquals(-1, comparator.compare(new TestIdEntity(1L), new TestIdEntity(null)));
    }

    @Test
    public void testCompareRightBothNull() {
        Assert.assertEquals(0, comparator.compare(null, null));
    }

    @Test
    public void testCompareRightBothIdNull() {
        Assert.assertEquals(0, comparator.compare(new TestIdEntity(null), new TestIdEntity(null)));
    }

    public static class TestIdEntity extends IdEntity {
        public TestIdEntity(Long id) {
            super();
            this.setId(id);
        }
    }
}
