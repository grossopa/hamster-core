package org.hamster.core.test.helper.test;

import javax.annotation.Generated;

import org.hamster.core.test.helper.Coverage;
import org.junit.Test;

@Generated(value = "org.junit-tools-1.0.5")
public class CoverageTest {

    @Test
    public void testConstructor() {
        Coverage.coverUtilConstructor(Coverage.class);
    }

    @Test(expected = AssertionError.class)
    public void testCoverUtilConstructorClass1() throws Exception {
        Coverage.coverUtilConstructor(TestClass1.class);
    }
    
    @Test(expected = AssertionError.class)
    public void testCoverUtilConstructorClass2() throws Exception {
        Coverage.coverUtilConstructor(TestClass2.class);
    }
    
    @Test(expected = AssertionError.class)
    public void testCoverUtilConstructorClass3() throws Exception {
        Coverage.coverUtilConstructor(TestClass3.class);
    }
    
    @Test(expected = AssertionError.class)
    public void testCoverUtilConstructorClass4() throws Exception {
        Coverage.coverUtilConstructor(TestClass4.class);
    }
    
    @Test(expected = AssertionError.class)
    public void testCoverUtilConstructorClass5() throws Exception {
        Coverage.coverUtilConstructor(TestClass5.class);
    }
    
    @Test
    public void testCoverUtilConstructorClass6() throws Exception {
        Coverage.coverUtilConstructor(TestClass6.class);
    }
    
    @Test
    public void testCoverUtilConstructorClass7() throws Exception {
        Coverage.coverUtilConstructor(TestClass7.class);
    }

    @Test(expected = AssertionError.class)
    public void testCoverUtilConstructorInterface() throws Exception {
        Coverage.coverUtilConstructor(TestInterface.class);
    }

    public static class TestClass1 {

    }
    
    public static class TestClass2 {
        protected TestClass2() {
            
        }
    }
    
    public static class TestClass3 {
        public TestClass3() {
            
        }
    }
    
    public static class TestClass4 {
        private TestClass4(String args) {
            
        }
    }
    
    public static class TestClass5 {
        private TestClass5() {
            
        }
    }
    
    public static class TestClass6 {
        private TestClass6() {
            throw new IllegalArgumentException();
        }
    }
    
    public static class TestClass7 {
        private TestClass7() {
            throw new AssertionError();
        }
    }


    public static interface TestInterface {

    }

}