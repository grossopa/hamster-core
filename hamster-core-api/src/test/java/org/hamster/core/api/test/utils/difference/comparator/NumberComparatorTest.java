/**
 * 
 */
package org.hamster.core.api.test.utils.difference.comparator;

import java.lang.reflect.Method;
import java.util.Map;

import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.comparator.defaults.NumberComparator;
import org.hamster.core.test.helper.Asserts;
import org.junit.Assert;
import org.junit.Test;

import lombok.Getter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class NumberComparatorTest {

    private NumberComparator comparator = new NumberComparator();

    @Test
    public void testCanCompare() {
        Map<String, Method> methods = ReflectUtils.findGetterMethods(Foo.class);
        
        Assert.assertTrue(comparator.canCompare("a", methods.get("a")));
        Assert.assertTrue(comparator.canCompare("b", methods.get("b")));
        Assert.assertTrue(comparator.canCompare("c", methods.get("c")));
        Assert.assertTrue(comparator.canCompare("d", methods.get("d")));
        Assert.assertTrue(comparator.canCompare("e", methods.get("e")));
        Assert.assertFalse(comparator.canCompare("f", methods.get("f")));
        Assert.assertFalse(comparator.canCompare("g", methods.get("g")));
        Assert.assertFalse(comparator.canCompare("h", methods.get("h")));
    }
    
    @Test
    public void testCompare() {
        Asserts.assertNotEquals(0, comparator.compare(1l, 2l));
        Asserts.assertEquals(0, comparator.compare(1l, 1l));
        Asserts.assertEquals(0, comparator.compare(1.000000000000000000001d, 1d));
        Asserts.assertNotEquals(0, comparator.compare(1, 2));
        Asserts.assertEquals(0, comparator.compare(0, 0));
        
        Asserts.assertEquals(0, comparator.compare(Integer.valueOf(1), 1l));
    }
}

@Getter
class Foo {
    private Double a;
    private Number b;
    private Float c;
    private int d;
    private long e;
    private String f;
    private Object g;
    private char h;
}