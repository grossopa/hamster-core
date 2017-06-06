package org.hamster.core.web.spring.view.test;

import static org.mockito.Mockito.mock;

import java.util.Locale;

import org.hamster.core.test.helper.Asserts;
import org.hamster.core.web.spring.view.JsonViewResolver;
import org.junit.Test;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.json.AbstractJackson2View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

public class JsonViewResolverTest {

    private JsonViewResolver createTestSubject() {
        return new JsonViewResolver();
    }

    @Test
    public void testResolveViewName() throws Exception {
        JsonViewResolver testSubject;
        String viewName = "theViewName";
        Locale locale = Locale.CANADA;
        View result = null;

        // default test
        testSubject = createTestSubject();
        result = testSubject.resolveViewName(viewName, locale);

        Asserts.assertEquals(MappingJackson2JsonView.class, result.getClass());
    }

    @Test
    public void testResolveViewNameExists() throws Exception {
        JsonViewResolver testSubject;
        String viewName = "theViewName2";
        Locale locale = Locale.CHINA;
        View result = null;
        AbstractJackson2View mockView = mock(AbstractJackson2View.class);

        // default test
        testSubject = createTestSubject();
        testSubject.setView(mockView);
        result = testSubject.resolveViewName(viewName, locale);

        Asserts.assertEquals(mockView, result);
    }

    @Test
    public void testSetOrder() throws Exception {
        JsonViewResolver testSubject = createTestSubject();
        testSubject.setOrder(1);
        Asserts.assertEquals(1, testSubject.getOrder());

    }
}