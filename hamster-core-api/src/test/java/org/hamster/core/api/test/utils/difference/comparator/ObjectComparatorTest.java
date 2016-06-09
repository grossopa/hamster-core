/**
 * 
 */
package org.hamster.core.api.test.utils.difference.comparator;

import java.lang.reflect.Method;
import java.util.Map;

import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.comparator.defaults.ObjectComparator;
import org.hamster.core.test.helper.Asserts;
import org.junit.Test;
import org.mockito.Mockito;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ObjectComparatorTest {
    private ObjectComparator objectComparator = new ObjectComparator();

    @Test
    public void testCanCompare() {
        Map<String, Method> methods = ReflectUtils.findGetterMethods(Bar.class);

        Asserts.assertTrue(objectComparator.canCompare("foo", methods.get("foo")));
        Asserts.assertFalse(objectComparator.canCompare("collection", methods.get("collection")));
        Asserts.assertTrue(objectComparator.canCompare("object", methods.get("object")));
    }

    @Test
    public void testComparable() {
        Foo2 f1 = Mockito.mock(Foo2.class);
        f1.setId(1l);
        Foo2 f2 = Mockito.mock(Foo2.class);

        objectComparator.compare(f1, f2);

        Mockito.verify(f1, Mockito.atLeast(1)).compareTo(f2);

        Object object = new Object();
        objectComparator.compare(object, f2);

        Mockito.verify(f2, Mockito.atLeast(1)).compareTo(object);

    }

    @Test
    public void testSimpleObject() {
        Object f1 = Mockito.mock(Object.class);
        Object f2 = Mockito.mock(Object.class);

        Asserts.assertEquals(-1, objectComparator.compare(f1, f2));
        Asserts.assertEquals(0, objectComparator.compare(f1, f1));
        Mockito.verify(f1, Mockito.atLeast(1)).equals(f2);

    }
}

@Setter
@Getter
class Bar {
    private Foo2 foo;
    private Iterable<String> collection;
    private Object object;

}

@Setter
@Getter
class Foo2 implements Comparable<Object> {

    private Long id;

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(Object o) {
        if (o instanceof Foo2) {
            return id.compareTo(((Foo2) o).id);
        }

        return -1;
    }

}