package org.hamster.core.web.controller.test;

import org.hamster.core.api.exception.dto.ExceptionDto;
import org.hamster.core.test.helper.Asserts;
import org.hamster.core.web.controller.AbstractController;
import org.hamster.core.web.controller.exception.ExceptionConverter;
import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class AbstractControllerTest {

    private AbstractController createTestSubject() {
        return new AbstractController() {

        };
    }

    @Test
    public void testHandleException() throws Exception {
        AbstractController testSubject;
        Exception ex = new Exception("This is another message");
        ExceptionDto result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.handleException(ex);

        Asserts.assertEquals(ex.getMessage(), result.getMessage());
    }

    @Test
    public void testHandleExceptionHasHandler() throws Exception {
        AbstractController testSubject;
        Exception ex = new IllegalArgumentException("This is another message");
        ExceptionDto result;

        // default test
        testSubject = createTestSubject();
        testSubject.setExceptionConverters(ImmutableList.of(new ExceptionConverter() {

            @Override
            public int getOrder() {
                return 55;
            }

            @Override
            public ExceptionDto handle(Exception ex) {
                ExceptionDto result = new ExceptionDto();
                result.setCode("abc123");
                result.setMessage("prefix " + ex.getMessage());
                return result;
            }

            @Override
            public boolean canHandle(Exception ex) {
                return ex instanceof IllegalArgumentException;
            }

        }));
        result = testSubject.handleException(ex);

        Asserts.assertEquals("prefix " + ex.getMessage(), result.getMessage());
        Asserts.assertEquals("abc123", result.getCode());

        ExceptionDto result2 = testSubject.handleException(new RuntimeException("aaa"));

        Asserts.assertEquals("aaa", result2.getMessage());
    }

}