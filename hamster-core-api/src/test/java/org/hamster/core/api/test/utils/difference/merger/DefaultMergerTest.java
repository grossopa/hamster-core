/**
 * 
 */
package org.hamster.core.api.test.utils.difference.merger;

import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.merger.defaults.DefaultMerger;
import org.hamster.core.api.util.difference.transformer.defaults.DefaultPropertyInvoker;
import org.hamster.core.test.helper.Asserts;
import org.junit.Test;

import com.google.common.collect.Sets;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DefaultMergerTest {
    private DefaultMerger<Long, Foo> merger;

    @Test
    public void testDefaultMerger() {
        merger = new DefaultMerger<Long, Foo>(new DefaultPropertyInvoker<Foo>());
        Map<String, Pair<Method, Method>> methods = ReflectUtils.findGetterSetterMethods(Foo.class);

        Foo f1 = new Foo(1l, "uvw", "xyz");
        Foo f2 = new Foo(1l, "uvw2", "changedValue");

        merger.mergeChanged(methods, Sets.newHashSet("name", "readOnly"), f1, f2);

        Asserts.assertEquals("xyz", f1.getReadOnly());
        Asserts.assertEquals("uvw2", f1.getName());
    }

    @Test
    public void testDefaultMergerInvokeFailed() {
        DefaultMerger<Long, Foo2> merger = new DefaultMerger<Long, Foo2>(new DefaultPropertyInvoker<Foo2>());
        Map<String, Pair<Method, Method>> methods = ReflectUtils.findGetterSetterMethods(Foo2.class);

        Foo2 f1 = new Foo2(1l, "uvw", "exception1");
        Foo2 f2 = new Foo2(1l, "uvw2", "newValue");

        merger.mergeChanged(methods, Sets.newHashSet("name", "throwException"), f1, f2);

        Asserts.assertEquals("exception1", f1.getThrowException());
    }
    
    @Test
    public void testDefaultMergerInvokeFailed2() {
        DefaultMerger<Long, Foo2> merger = new DefaultMerger<Long, Foo2>(new DefaultPropertyInvoker<Foo2>());
        Map<String, Pair<Method, Method>> methods = ReflectUtils.findGetterSetterMethods(Foo2.class);

        Foo2 f1 = new Foo2(1l, "uvw", "exception1");
        Foo2 f2 = new Foo2(1l, "uvw2", "newValue");

        merger.mergeChanged(methods, Sets.newHashSet("name", "no_such_property"), f1, f2);

        Asserts.assertEquals("exception1", f1.getThrowException());
    }
}

@AllArgsConstructor
class Foo {

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    @Getter
    private String readOnly;

}

class Foo2 {

    public Foo2(Long id, String name, String throwException) {
        this.id = id;
        this.name = name;
        this.throwException = throwException;
    }

    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;

    private String throwException;

    /**
     * @return the throwException
     */
    public String getThrowException() {
        return throwException;
    }

    /**
     * @param throwException
     *            the throwException to set
     */
    public void setThrowException(String throwException) {
        throw new RuntimeException();
    }

}
