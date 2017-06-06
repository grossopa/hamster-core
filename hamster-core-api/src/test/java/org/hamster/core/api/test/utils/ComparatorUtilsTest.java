/**
 * 
 */
package org.hamster.core.api.test.utils;

import org.hamster.core.api.util.ComparatorUtils;
import org.hamster.core.test.helper.Coverage;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ComparatorUtilsTest {
    
    @Test
    public void testCoverage() {
        Coverage.coverUtilConstructor(ComparatorUtils.class);
    }
    
    @Test
    public void testObviateNull1() {
        Object o1 = new Object();
        Object o2 = new Object();
        Assert.assertNull(ComparatorUtils.obviateNull(o1, o2));
    }
    
    @Test
    public void testObviateNull2() {
        Object o1 = new Object();
        Assert.assertEquals(-1, ComparatorUtils.obviateNull(o1, null).intValue());
    }
    
    @Test
    public void testObviateNull3() {
        Object o2 = new Object();
        Assert.assertEquals(1, ComparatorUtils.obviateNull(null, o2).intValue());
    }
    
    @Test
    public void testObviateNull4() {
        Assert.assertEquals(0, ComparatorUtils.obviateNull(null, null).intValue());
    }
}
