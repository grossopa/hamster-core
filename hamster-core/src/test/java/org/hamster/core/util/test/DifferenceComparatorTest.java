/**
 * 
 */
package org.hamster.core.util.test;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.collections4.IterableUtils;
import org.apache.commons.collections4.Predicate;
import org.hamster.core.util.audit.DifferenceChecker;
import org.hamster.core.util.audit.DifferenceChecker.Config;
import org.hamster.core.util.audit.model.DifferenceCollVO;
import org.hamster.core.util.audit.model.DifferenceObjectVO;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DifferenceComparatorTest {

    @Getter
    @Setter
    public static class Foo {
        private Long id;
        private String name;
        private Integer timestamp;
        private Double priceNumber1;
        private Boolean male;
    }

    @Test
    public void testChangeBase() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Foo f1 = new Foo();
        f1.setId(1l);
        f1.setMale(false);
        f1.setName("Jack 1");
        f1.setPriceNumber1(25d);
        f1.setTimestamp(15);

        Foo f2 = new Foo();
        f2.setId(1l);
        f2.setMale(true);
        f2.setName("Jack 1");
        f2.setPriceNumber1(33d);
        f2.setTimestamp(15);

        DifferenceChecker auditor = new DifferenceChecker(new Config());
        DifferenceCollVO result = auditor.check(Lists.newArrayList(f1), Lists.newArrayList(f2));
        Assert.assertEquals(1, result.getObjectList().size());

        Assert.assertEquals(1l, result.getObjectList().get(0).getId());

        Assert.assertEquals(5, result.getProperties().size());

        Assert.assertNotNull(this.findByProperty(result, "male"));
        Assert.assertNull(findByProperty(result, "name"));
        Assert.assertNotNull(this.findByProperty(result, "priceNumber1"));
        Assert.assertNull(findByProperty(result, "timestamp"));
    }

    @Test
    public void testChangeInclude() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Foo f1 = new Foo();
        f1.setId(1l);
        f1.setMale(false);
        f1.setName("Jack 1");
        f1.setPriceNumber1(25d);
        f1.setTimestamp(15);

        Foo f2 = new Foo();
        f2.setId(1l);
        f2.setMale(true);
        f2.setName("Jack 1");
        f2.setPriceNumber1(33d);
        f2.setTimestamp(15);

        Config config = new Config();
        config.setExclude(false);
        config.setProperties(Sets.newHashSet("male", "name"));
        DifferenceChecker auditor = new DifferenceChecker(config);
        DifferenceCollVO result = auditor.check(Lists.newArrayList(f1), Lists.newArrayList(f2));

        Assert.assertEquals(3, result.getProperties().size());

        Assert.assertNotNull(this.findByProperty(result, "male"));
        Assert.assertNull(findByProperty(result, "name"));
        Assert.assertNull(this.findByProperty(result, "priceNumber1"));
        Assert.assertNull(findByProperty(result, "timestamp"));
    }
    
    @Test
    public void testChangeExclude() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Foo f1 = new Foo();
        f1.setId(1l);
        f1.setMale(false);
        f1.setName("Jack 1");
        f1.setPriceNumber1(25d);
        f1.setTimestamp(15);

        Foo f2 = new Foo();
        f2.setId(1l);
        f2.setMale(true);
        f2.setName("Jack 1");
        f2.setPriceNumber1(33d);
        f2.setTimestamp(15);

        Config config = new Config();
        config.setExclude(true);
        config.setProperties(Sets.newHashSet("male", "name"));
        DifferenceChecker auditor = new DifferenceChecker(config);
        DifferenceCollVO result = auditor.check(Lists.newArrayList(f1), Lists.newArrayList(f2));

        Assert.assertEquals(3, result.getProperties().size());

        Assert.assertNull(this.findByProperty(result, "male"));
        Assert.assertNull(findByProperty(result, "name"));
        Assert.assertNotNull(this.findByProperty(result, "priceNumber1"));
        Assert.assertNull(findByProperty(result, "timestamp"));
    }

    protected DifferenceObjectVO findByProperty(DifferenceCollVO result, final String property) {
        return IterableUtils.find(result.getObjectList(), new Predicate<DifferenceObjectVO>() {

            @Override
            public boolean evaluate(DifferenceObjectVO vo) {
                return vo.getPropertyList().containsKey(property);
            }

        });
    }
}
