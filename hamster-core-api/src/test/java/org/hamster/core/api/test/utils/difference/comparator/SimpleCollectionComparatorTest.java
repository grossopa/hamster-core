/**
 * 
 */
package org.hamster.core.api.test.utils.difference.comparator;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.comparator.defaults.SimpleCollectionComparator;
import org.hamster.core.test.helper.Asserts;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class SimpleCollectionComparatorTest {

    SimpleCollectionComparator comparator = new SimpleCollectionComparator();

    @Test
    public void testCanCompare() {
        Map<String, Method> getterMethods = ReflectUtils.findGetterMethods(Bars.class, true, new String[] { "map1" });
        Function<Map.Entry<String, Method>, Void> verifyFunction = new Function<Map.Entry<String, Method>, Void>() {

            @Override
            public Void apply(Entry<String, Method> input) {
                Asserts.assertTrue(input.getKey(), comparator.canCompare(input.getKey(), input.getValue()));
                return null;
            }

        };

        for (Map.Entry<String, Method> entry : getterMethods.entrySet()) {
            verifyFunction.apply(entry);
        }
    }

    @Test
    public void testCompareBothNull() {
        Asserts.assertEquals(0, comparator.compare(null, null));
        Asserts.assertEquals(0, comparator.compare(Sets.newHashSet(), null));
        Asserts.assertEquals(0, comparator.compare(null, Lists.newArrayList()));
        Asserts.assertEquals(0, comparator.compare(Lists.newArrayList(), Lists.newArrayList()));
    }

    @Test
    public void testCompareLeftNull() {
        Asserts.assertNotEquals(0, comparator.compare(null, Lists.newArrayList(new Object())));
    }

    @Test
    public void testCompareRightNull() {
        Asserts.assertNotEquals(0, comparator.compare(Lists.newArrayList(new Object()), null));
    }

    @Test
    public void testCompareEquals() {
        Object object = new Object();
        Asserts.assertEquals(0, comparator.compare(Lists.newArrayList(object), Lists.newArrayList(object)));
    }

    @Test
    public void testCompareEqualsDifferentOrder() {
        Object object1 = new Object();
        Object object2 = new Object();
        Asserts.assertEquals(0, comparator.compare(Lists.newArrayList(object1, object2), Lists.newArrayList(object2, object1)));
    }
}

@Getter
@Setter
class Bars {
    private Object[] array1;
    private Long[] array2;
    private String[] array3;
    private List<String> coll1;
    private Set<Object> coll2;
    private Map<String, Long> map1;
}
