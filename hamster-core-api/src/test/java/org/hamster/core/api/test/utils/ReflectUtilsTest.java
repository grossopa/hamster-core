package org.hamster.core.api.test.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.ReflectUtils;
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
    public void testFindGetterSetterMethods1() {
        Map<String, Pair<Method, Method>> bar = ReflectUtils.findGetterSetterMethods(Bar.class);
        assertResult(bar, new String[] { "id", "name", }, new String[] { "division", "notes", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindGetterSetterMethods2() {
        Map<String, Pair<Method, Method>> bar = ReflectUtils.findGetterSetterMethods(Bar.class, false, new String[] { "id", "name", "division" });
        assertResult(bar, new String[] { "id", "name" }, new String[] { "number", "dummyObject", "male", "division", "notes", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindGetterSetterMethods3() {
        Map<String, Pair<Method, Method>> bar = ReflectUtils.findGetterSetterMethods(Bar.class, true, new String[] { "id", "name", "division" });
        assertResult(bar, new String[] { "number", "dummyObject", "male" }, new String[] { "id", "name", "division", "notes", "thisIsPrivate", "thisIsProtected" });
    }

    @Test
    public void testFindGetterSetterMethods4() {
        Map<String, Pair<Method, Method>> bar = ReflectUtils.findGetterSetterMethods(null);
        Assert.assertNull(bar);
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

    @Test
    public void testToMap2() throws IllegalAccessException, InvocationTargetException {
        Map<Long, Bar> result = ReflectUtils.toMap(null, Bar.class, "id");
        Assert.assertTrue(result.isEmpty());
    }
    
    @Test
    public void testToMap3() throws IllegalAccessException, InvocationTargetException {
        List<Bar> coll = Lists.newArrayList();
        Map<Long, Bar> result = ReflectUtils.toMap(coll, Bar.class, "id");
        Assert.assertTrue(result.isEmpty());
    }

    @Test
    public void testTryInvoke() {
        Bar bar = new Bar();
        bar.setName("Dummy Name");
        Method method = ReflectUtils.findGetterMethods(Bar.class).get("name");
        String result = ReflectUtils.tryInvoke(method, bar);
        Assert.assertEquals("Dummy Name", result);
    }

    @Test
    public void testTryInvoke2() {
        Bar bar = new Bar();
        bar.setName("Dummy Name");
        String result = ReflectUtils.tryInvoke("getName", bar);
        Assert.assertEquals("Dummy Name", result);
    }

    @Test
    public void tesTryInvokeOverload1() throws IllegalAccessException, InvocationTargetException {
        Bar b = new Bar();
        ReflectUtils.tryInvoke("notifyUser", b);
        Assert.assertEquals("notifyUser()", b.lastCalledMethod());
    }

    @Test
    public void tesTryInvokeOverload12() throws IllegalAccessException, InvocationTargetException {
        Bar b = new Bar();
        ReflectUtils.tryInvoke("notifyUser", b, "abc");
        Assert.assertEquals("notifyUser(String)", b.lastCalledMethod());
    }

    @Test
    public void tesTryInvokeOverload3() throws IllegalAccessException, InvocationTargetException {
        Bar b = new Bar();
        ReflectUtils.tryInvoke("notifyUser", b, false);
        Assert.assertEquals("notifyUser(Boolean)", b.lastCalledMethod());
    }

    @Test
    public void tesTryInvokeOverload4() throws IllegalAccessException, InvocationTargetException {
        Bar b = new Bar();
        ReflectUtils.tryInvoke("notifyUser", b, true, null);
        Assert.assertEquals("notifyUser(Boolean, String)", b.lastCalledMethod());
    }

    @Test
    public void testTryInvokeWithArgs() {
        Bar bar = new Bar();
        ReflectUtils.tryInvoke("setNumber", bar, Double.valueOf(123d));
        Assert.assertEquals(Double.valueOf(123d), bar.getNumber());
    }

    @Test
    public void testTryInvokeNull1() {
        Method method = ReflectUtils.findGetterMethods(Bar.class).get("division");
        String result = ReflectUtils.tryInvoke(method, null);
        Assert.assertNull(result);
    }

    @Test
    public void testTryInvokeNull2() {
        Bar bar = new Bar();
        Method method = null;
        String result = ReflectUtils.tryInvoke(method, bar);
        Assert.assertNull(result);
    }

    @Test
    public void testTryInvokeNull3() {
        Bar bar = new Bar();
        Assert.assertNull(ReflectUtils.tryInvoke("no_such_methods", bar));
    }

    @Test
    public void testTryInvokeNull4() {
        Bar bar = null;
        Assert.assertNull(ReflectUtils.tryInvoke("getId", bar, "1"));
    }

    @Test
    public void testTryInvokeException() {
        Bar2 bar2 = new Bar2();
        Assert.assertNull(ReflectUtils.tryInvoke("throwException", bar2));
    }

    @Test
    public void testFindMethodsByName() {
        Set<Method> methods = ReflectUtils.findMethodsByName(Bar.class, "lastCalledMethod");
        Assert.assertEquals(1, methods.size());
    }

    @Test
    public void testFindMethodsByName2() {
        Set<Method> methods = ReflectUtils.findMethodsByName(Bar.class, "notifyUser");
        Assert.assertEquals(4, methods.size());
    }

    @Test(expected = NullPointerException.class)
    public void testInvokeNull1() throws IllegalAccessException, InvocationTargetException {
        Method method = null;
        ReflectUtils.invoke(method, new Bar());
    }

    @Test(expected = NullPointerException.class)
    public void testInvokeNull2() throws IllegalAccessException, InvocationTargetException {
        Method method = ReflectUtils.findGetterMethods(Bar.class).get("id");
        Assert.assertNotNull(method);
        ReflectUtils.invoke(method, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvokeNull3() throws IllegalAccessException, InvocationTargetException {
        String method = null;
        ReflectUtils.invoke(method, new Bar());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvokeNull4() throws IllegalAccessException, InvocationTargetException {
        String method = "getId";
        ReflectUtils.invoke(method, null);
    }

    @Test(expected = NullPointerException.class)
    public void testInvokeNull5() throws IllegalAccessException, InvocationTargetException {
        String method = "getId_not_exist";
        ReflectUtils.invoke(method, new Bar());
    }

    @Test
    public void testInvoke() throws IllegalAccessException, InvocationTargetException {
        String method = "setId";
        Bar b = new Bar();
        ReflectUtils.invoke(method, b, 33l);
        Assert.assertEquals(Long.valueOf(33l), b.getId());
    }

    private void assertResult(Map<String, ?> result, String[] exists, String[] notExists) {
        for (String exist : exists) {
            Assert.assertTrue(result.containsKey(exist));
        }

        for (String notExist : notExists) {
            Assert.assertFalse(result.containsKey(notExist));
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    public static class Bar extends Foo {

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

    public static class Bar2 {
        public void throwException() {
            throw new RuntimeException();
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

    private String lastCalledMethodName;

    public void notifyUser() {
        lastCalledMethodName = "notifyUser()";
    }

    public void notifyUser(String userName) {
        lastCalledMethodName = "notifyUser(String)";
    }

    public void notifyUser(Boolean notifyManager) {
        lastCalledMethodName = "notifyUser(Boolean)";
    }

    public void notifyUser(Boolean notifyManager, String message) {
        lastCalledMethodName = "notifyUser(Boolean, String)";
    }

    public String lastCalledMethod() {
        return lastCalledMethodName;
    }
}
