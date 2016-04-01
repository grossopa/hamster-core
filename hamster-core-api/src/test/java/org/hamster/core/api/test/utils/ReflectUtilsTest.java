package org.hamster.core.api.test.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.hamster.core.utils.ReflectUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ReflectUtilsTest {

    @Test
    public void testFindByPrefixNull() {
        Map<String, Method> bar = ReflectUtils.findFieldMethodByPrefix(null, new String[] { "n" }, true, null);
        Assert.assertNull(bar);
    }

    @Test
    public void testFindByPrefix() {
        Map<String, Method> bar = ReflectUtils.findFieldMethodByPrefix(Bar.class, new String[] { "getN" }, true, null);
        assertResult(bar, new String[] { "ame", "umber" }, new String[] { "id", "division", "dummyObject", "male", "notes", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindGetter() {
        Map<String, Method> bar = ReflectUtils.findGetterMethods(Bar.class);
        assertResult(bar, new String[] { "id", "name", "number", "dummyObject", "male", "notes" }, new String[] { "division", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindGetterExclude() {
        Map<String, Method> bar = ReflectUtils.findGetterMethods(Bar.class, true, new String[] { "dummyObject", "male" });
        assertResult(bar, new String[] { "id", "name", "number", "notes" }, new String[] { "division", "dummyObject", "male", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindGetterInclude() {
        Map<String, Method> bar = ReflectUtils.findGetterMethods(Bar.class, false, new String[] { "dummyObject", "male", "division" });
        assertResult(bar, new String[] { "dummyObject", "male" }, new String[] { "id", "name", "number", "notes", "division", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindSetter() {
        Map<String, Method> bar = ReflectUtils.findSetterMethods(Bar.class);
        assertResult(bar, new String[] { "id", "name", "number", "dummyObject", "male", "division" }, new String[] { "notes", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindSetterExclude() {
        Map<String, Method> bar = ReflectUtils.findSetterMethods(Bar.class, true, new String[] { "dummyObject", "male", "division" });
        assertResult(bar, new String[] { "id", "name", "number" }, new String[] { "notes", "dummyObject", "male", "division", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindSetterInclude() {
        Map<String, Method> bar = ReflectUtils.findSetterMethods(Bar.class, false, new String[] { "dummyObject", "notes", "division", "thisIsPrivate", "thisIsProtected" });
        assertResult(bar, new String[] { "dummyObject", "division" }, new String[] { "id", "name", "number", "notes", "male", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testToMap() throws IllegalAccessException, InvocationTargetException {
        List<Bar> coll = Lists.newArrayList();
        Bar b = new Bar();
        b.setId(3l);
        Bar b2 = new Bar();
        b2.setId(5l);
        Bar b3 = new Bar();
        b3.setId(7l);

        coll.add(b);
        coll.add(b2);
        coll.add(b3);
        Map<Long, Bar> result = ReflectUtils.toMap(coll, Bar.class, "id");
        Assert.assertTrue(result.containsKey(3l));
        Assert.assertTrue(result.containsKey(5l));
        Assert.assertTrue(result.containsKey(7l));
    }

    private void assertResult(Map<String, ?> result, String[] exists, String[] notExists) {
        for (String exist : exists) {
            Assert.assertTrue(result.containsKey(exist));
        }

        for (String notExist : notExists) {
            Assert.assertFalse(result.containsKey(notExist));
        }
    }
}

class Foo {
    @Getter
    @Setter
    private Long id;

    @Getter
    @Setter
    private String name;
}

@NoArgsConstructor
@AllArgsConstructor
class Bar extends Foo {

    @Getter
    @Setter
    private Double number;

    @Getter
    @Setter
    private Object dummyObject;

    @Getter
    @Setter
    private boolean male;

    @Getter
    private String notes;

    @Setter
    private String division;

    private String getThisIsPrivate() {
        return division + notes;
    }

    protected String getThisIsProtected() {
        return division + notes;
    }

    private void setThisIsPrivate(String a) {
        this.notes = getThisIsPrivate() + a;
    }

    protected void setThisIsProtected(String a) {
        setThisIsPrivate(a);
        this.division = a;
    }
}