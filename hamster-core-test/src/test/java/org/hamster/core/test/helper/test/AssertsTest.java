package org.hamster.core.test.helper.test;

import org.hamster.core.test.helper.Asserts;
import org.hamster.core.test.helper.Coverage;
import org.junit.Test;
import org.springframework.util.Assert;

public class AssertsTest {
    
    @Test
    public void testConstructor() {
        Coverage.coverUtilConstructor(Asserts.class);
    }

    @Test
    public void testAssertGreaterThan() throws Exception {
        Asserts.assertGreaterThan(0, 1);
    }

    @Test(expected = AssertionError.class)
    public void testAssertGreaterThan1() throws Exception {
        Asserts.assertGreaterThan(0, 0);
        Asserts.assertGreaterThan("123", 0, 0);
    }

    @Test(expected = AssertionError.class)
    public void testAssertGreaterThan2() throws Exception {
        Asserts.assertGreaterThan(0, -1);
    }

    @Test
    public void testAssertGreaterThanOrEqualTo() throws Exception {
        Asserts.assertGreaterThanOrEqualTo(0, 1);
        Asserts.assertGreaterThanOrEqualTo("123", 0, 1);
    }

    @Test
    public void testAssertGreaterThanOrEqualTo1() throws Exception {
        Asserts.assertGreaterThanOrEqualTo(0, 0);
    }

    @Test(expected = AssertionError.class)
    public void testAssertGreaterThanOrEqualTo2() throws Exception {
        Asserts.assertGreaterThanOrEqualTo(0, -1);
    }

    @Test
    public void testAssertGreaterThanT() throws Exception {
        Asserts.assertGreaterThan(new AssertsTestBean(0, "a"), new AssertsTestBean(1, "a"));
        Asserts.assertGreaterThan(new AssertsTestBean(1, "a"), new AssertsTestBean(1, "b"));
        Asserts.assertGreaterThan("123", new AssertsTestBean(1, "a"), new AssertsTestBean(1, "b"));
    }

    @Test(expected = AssertionError.class)
    public void testAssertGreaterThanT1() throws Exception {
        Asserts.assertGreaterThan(new AssertsTestBean(1, "a"), new AssertsTestBean(1, "a"));
    }

    @Test(expected = AssertionError.class)
    public void testAssertGreaterThanT2() throws Exception {
        Asserts.assertGreaterThan(new AssertsTestBean(2, "a"), new AssertsTestBean(1, "a"));
    }

    @Test
    public void testAssertGreaterThanOrEqualTo_1() throws Exception {
        Asserts.assertGreaterThanOrEqualTo(new AssertsTestBean(0, "a"), new AssertsTestBean(1, "a"));
        Asserts.assertGreaterThanOrEqualTo(new AssertsTestBean(1, "a"), new AssertsTestBean(1, "b"));
    }

    @Test
    public void testAssertGreaterThanOrEqualTo_2() throws Exception {
        Asserts.assertGreaterThanOrEqualTo(new AssertsTestBean(0, "a"), new AssertsTestBean(0, "a"));
    }

    @Test(expected = AssertionError.class)
    public void testAssertGreaterThanOrEqualTo_3() throws Exception {
        Asserts.assertGreaterThanOrEqualTo(new AssertsTestBean(1, "a"), new AssertsTestBean(-1, "a"));
    }

    @Test
    public void testAssertLessThan() throws Exception {
        Asserts.assertLessThan(0, -1);
    }

    @Test
    public void testAssertLessThanOrEqualTo() throws Exception {
        Asserts.assertLessThanOrEqualTo(0, 0);
        Asserts.assertLessThanOrEqualTo(0, -1);
    }

    @Test
    public void testAssertLessThan_1() throws Exception {
        Asserts.assertLessThan("234", 0, -1);
    }

    @Test
    public void testAssertLessThanOrEqualTo_1() throws Exception {
        Asserts.assertLessThanOrEqualTo("234", new AssertsTestBean(0, "a"), new AssertsTestBean(-1, "b"));
    }

    @Test(expected = AssertionError.class)
    public void testAssertThrownWrongType() {
        Asserts.assertThrown(NullPointerException.class, () -> {
            throw new IllegalAccessException();
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAssertThrownNoConsumer() {
        Asserts.assertThrown(NullPointerException.class, null);
    }

    @Test(expected = AssertionError.class)
    public void testAssertThrownNoThrown() {
        Asserts.assertThrown(NullPointerException.class, () -> {
            Integer a = 1;
            a.toString();
        });
    }

    @Test
    public void testAssertThrown() {
        Asserts.assertThrown(RuntimeException.class, () -> {
            throw new RuntimeException();
        });
    }
}

class AssertsTestBean implements Comparable<AssertsTestBean> {

    private final Integer id;
    private final String name;

    public AssertsTestBean(Integer id, String name) {
        Assert.notNull(id, "id cannot be null");
        Assert.notNull(name, "name cannot be null");
        this.id = id;
        this.name = name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(AssertsTestBean o) {
        if (o == null) {
            return 1;
        }

        int idResult = id.compareTo(o.id);
        if (idResult == 0) {
            return name.compareTo(o.name);
        }
        return idResult;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}