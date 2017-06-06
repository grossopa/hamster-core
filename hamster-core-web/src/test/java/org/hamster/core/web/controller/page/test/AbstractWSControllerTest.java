package org.hamster.core.web.controller.page.test;

import org.hamster.core.api.exception.dto.ExceptionDto;
import org.hamster.core.test.helper.Asserts;
import org.hamster.core.web.controller.page.AbstractWSController;
import org.junit.Test;

public class AbstractWSControllerTest {

    private AbstractWSController createTestSubject() {
        return new AbstractWSController() {

        };
    }

    @Test
    public void testExceptionHandler() throws Exception {
        AbstractWSController testSubject;
        Exception ex = new RuntimeException("This is message");
        ExceptionDto result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.exceptionHandler(ex);
        Asserts.assertEquals(ex.getMessage(), result.getMessage());
    }
    
}