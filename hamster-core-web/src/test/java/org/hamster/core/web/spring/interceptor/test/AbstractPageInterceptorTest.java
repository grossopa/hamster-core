package org.hamster.core.web.spring.interceptor.test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamster.core.api.environment.Environment;
import org.hamster.core.web.spring.interceptor.AbstractPageInterceptor;
import org.junit.After;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

public class AbstractPageInterceptorTest {

    @After
    public void after() {
        Environment.cleanup();
    }

    private AbstractPageInterceptor createTestSubject() {
        return new AbstractPageInterceptor() {

            @Override
            public Optional<String[]> pathPatterns() {
                return null;
            }

            @Override
            public Optional<String[]> excludePathPatterns() {
                return null;
            }

            @Override
            protected String getUserName() {
                return null;
            }

        };
    }

    @Test
    public void testPostHandle() throws Exception {
        AbstractPageInterceptor testSubject;
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        Object handler = null;
        ModelAndView modelAndView = null;

        // default test
        testSubject = createTestSubject();
        testSubject.postHandle(request, response, handler, modelAndView);
    }

    @Test
    public void testPostHandle2() throws Exception {

        Environment.initializeEnvironment(Environment.UNKNOWN);
        AbstractPageInterceptor testSubject;
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = null;
        Object handler = null;
        ModelAndView modelAndView = mock(ModelAndView.class);

        // default test
        testSubject = createTestSubject();
        testSubject.postHandle(request, response, handler, modelAndView);

        verify(modelAndView, times(2)).addObject(anyString(), any());
    }
}