/**
 * 
 */
package org.hamster.core.dao.test.util.comparator;

import java.util.Collections;
import java.util.List;

import org.hamster.core.api.model.base.OrderIfc;
import org.hamster.core.dao.util.comparator.OrderEntityComparator;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class OrderEntityComparatorTest {
    @Test
    public void test1() {
        List<Foo> fooList = Lists.newArrayList();
        fooList.add(null);
        fooList.add(new Foo(4l, null));
        fooList.add(new Foo(4l, 4));
        fooList.add(new Foo(4l, 3));
        fooList.add(new Foo(3l, 3));
        fooList.add(new Foo(2l, 2));
        fooList.add(new Foo(1l, 1));
        
        Collections.sort(fooList, new OrderEntityComparator<Long>());
        
        List<Foo> compareFooList = Lists.newArrayList();
        compareFooList.add(new Foo(1l, 1));
        compareFooList.add(new Foo(2l, 2));
        compareFooList.add(new Foo(3l, 3));
        compareFooList.add(new Foo(4l, 3));
        compareFooList.add(new Foo(4l, 4));
        compareFooList.add(new Foo(4l, null));
        compareFooList.add(null);
        
        Assert.assertArrayEquals(compareFooList.toArray(), fooList.toArray());
    }
}

@Getter
@Setter
@AllArgsConstructor
class Foo implements OrderIfc<Long> {
    private Long id;
    private Integer order;
}
