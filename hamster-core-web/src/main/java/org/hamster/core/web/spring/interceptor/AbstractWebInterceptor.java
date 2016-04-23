/**
 * 
 */
package org.hamster.core.web.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * used to track user's access time
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractWebInterceptor extends HandlerInterceptorAdapter {

    private static final Logger log = LoggerFactory.getLogger(AbstractWebInterceptor.class);

    public static final String ATTRIBUTE_NAME = "__time_track";

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute(ATTRIBUTE_NAME, System.currentTimeMillis());
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object,
     * java.lang.Exception)
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long time = System.currentTimeMillis() - (Long) request.getAttribute(ATTRIBUTE_NAME);
        logAccessTime(log, time, request);
    }

    /**
     * track access time
     * 
     * @param log
     * @param time
     */
    public void logAccessTime(Logger log, Long time, HttpServletRequest request) {
        log.info("User {} {} ms, URI : {}", getUserName(), time, getRequestURI(request));
    }

    /**
     * Get request URI
     * 
     * @param request
     * @return
     */
    protected String getRequestURI(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * 
     * @return current user name
     */
    protected abstract String getUserName();

}
