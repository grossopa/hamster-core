/**
 * 
 */
package org.hamster.core.api.test.utils.difference;

import java.util.List;

import org.hamster.core.api.util.difference.DiffChecker;
import org.hamster.core.api.util.difference.DiffCheckerException;
import org.hamster.core.api.util.difference.model.DiffObjectVO;
import org.hamster.core.api.util.difference.model.DiffType;
import org.hamster.core.test.helper.Asserts;
import org.junit.Test;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DiffCheckerTest {

    @Test
    public void testSimple() throws DiffCheckerException {
        //@formatter:off
        List<Dog> list1 = Lists.newArrayList(
            new Dog("AAA001", "Jone", 35),
            new Dog("AAA002", "Jack Jones", 44),
            new Dog("AAA003", "DDYYDYD", 61)
        );
        
        //AAA002 DELETED
        List<Dog> list2 = Lists.newArrayList(
                new Dog("AAA001", "Jone", 35),
                new Dog("AAA003", null, 61),
                new Dog("AAA004", "NEWVAL2", 100),
                new Dog(null, "NEWITEM1", 3),
                new Dog(null, "NEWITEM2", 5)
            );
        //@formatter:on

        DiffChecker<String, Dog> checker = new DiffChecker<String, Dog>();
        List<DiffObjectVO> diffResult = checker.check(Dog.class, list1, list2);

        Asserts.assertEquals(5, diffResult.size());
        int count1 = 0;
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;

        for (DiffObjectVO object : diffResult) {
            Asserts.assertNotEquals("AAA001", object.getId());
            
            if (object.getType() == DiffType.ADD) {
                if ("AAA004".equals(object.getId())) {
                    Asserts.assertNull(object.getPropertyList().get("name").getOldValue());
                    Asserts.assertEquals("NEWVAL2", object.getPropertyList().get("name").getNewValue());
                    Asserts.assertNull(object.getPropertyList().get("length").getOldValue());
                    Asserts.assertEquals(100, object.getPropertyList().get("length").getNewValue());
                    count1++;
                } else if (object.getId() == null) {
                    Asserts.assertNull(object.getPropertyList().get("name").getOldValue());
                    Object newValue = object.getPropertyList().get("name").getNewValue();
                    Asserts.assertTrue(newValue.equals("NEWITEM1") || newValue.equals("NEWITEM2"));
                    count2++;
                }
            } else if (object.getType() == DiffType.REMOVAL) {
                Asserts.assertEquals("AAA002", object.getId());
                count3++;
            } else if (object.getType() == DiffType.CHANGE) {
                Asserts.assertEquals("AAA003", object.getId());
                Asserts.assertEquals(1, object.getPropertyList().size());
                Asserts.assertEquals("DDYYDYD", object.getPropertyList().get("name").getOldValue());
                Asserts.assertEquals(null, object.getPropertyList().get("name").getNewValue());
                count4++;
            }
        }
        
        Asserts.assertEquals(1, count1);
        Asserts.assertEquals(2, count2);
        Asserts.assertEquals(1, count3);
        Asserts.assertEquals(1, count4);
    }

    @Getter
    @Setter
    public static class Person {
        private Long id;
        private String name;
        private Double money;
        private boolean male;
        private Dog dog;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Dog {
        private String id;
        private String name;
        private Integer length;
    }
}
