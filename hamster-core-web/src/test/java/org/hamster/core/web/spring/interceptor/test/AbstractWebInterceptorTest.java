package org.hamster.core.web.spring.interceptor.test;

import static org.mockito.Mockito.*;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamster.core.test.helper.Asserts;
import org.hamster.core.web.spring.interceptor.AbstractWebInterceptor;
import org.junit.Test;
import org.slf4j.Logger;

public class AbstractWebInterceptorTest {

    private AbstractWebInterceptor createTestSubject() {
        return new AbstractWebInterceptor() {

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
    public void testPreHandle() throws Exception {
        AbstractWebInterceptor testSubject;
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getAttribute(AbstractWebInterceptor.ATTR_TIME_TRACK)).thenReturn(Long.valueOf(111));
        HttpServletResponse response = null;
        Object handler = null;
        boolean result;

        // default test
        testSubject = createTestSubject();
        result = testSubject.preHandle(request, response, handler);
        verify(request, times(1)).setAttribute(eq(AbstractWebInterceptor.ATTR_TIME_TRACK), any());
        Asserts.assertTrue(result);
        
        testSubject.afterCompletion(request, response, handler, null);
        
        verify(request, times(1)).getRequestURI();
        verify(request, times(1)).getAttribute(eq(AbstractWebInterceptor.ATTR_TIME_TRACK));
    }

}