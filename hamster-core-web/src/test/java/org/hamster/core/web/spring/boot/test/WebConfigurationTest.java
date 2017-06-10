package org.hamster.core.web.spring.boot.test;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.hamster.core.test.helper.Asserts;
import org.hamster.core.web.spring.config.WebConfiguration;
import org.hamster.core.web.spring.view.JsonViewResolver;
import org.junit.Test;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

public class WebConfigurationTest {

    private WebConfiguration createTestSubject() {
        return new WebConfiguration() {
            @Override
            protected void enrichThymeleafViewResolver(ThymeleafViewResolver thymeleafViewResolver) {
                thymeleafViewResolver = mock(ThymeleafViewResolver.class);
                super.enrichThymeleafViewResolver(thymeleafViewResolver);
                verify(thymeleafViewResolver, times(1)).setOrder(anyInt());
            }
        };
    }
    
    @Test
    public void testCnViewResolver() throws Exception {
        WebConfiguration testSubject;
        ContentNegotiatingViewResolver result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.cnViewResolver();

        Asserts.assertNotNull(result);
    }

    @Test
    public void testJsonViewResolver() throws Exception {
        WebConfiguration testSubject;
        JsonViewResolver result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.jsonViewResolver();
        Asserts.assertNotNull(result);
    }

    @Test
    public void testInternalResourceViewResolver() throws Exception {
        WebConfiguration testSubject;
        InternalResourceViewResolver result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.internalResourceViewResolver();
        Asserts.assertNotNull(result);
    }

    @Test
    public void testCnManagerFactoryBean() throws Exception {
        WebConfiguration testSubject;
        ContentNegotiationManagerFactoryBean result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.cnManagerFactoryBean();
        Asserts.assertNotNull(result);
    }
}