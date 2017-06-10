/**
 * 
 */
package org.hamster.core.web.spring.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hamster.core.api.environment.Environment;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractPageInterceptor extends AbstractWebInterceptor {

    public static final String CONTEXT_PATH = "context_path";
    public static final String ENVIRONMENT = "environment";

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle(javax.servlet.http.
     * HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object,
     * org.springframework.web.servlet.ModelAndView)
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
        buildModelAndView(request, modelAndView);
    }

    /**
     * Build base_uri for jsp usage
     * 
     * @param request
     * @param response
     * @param mav
     */
    protected void buildModelAndView(HttpServletRequest request, ModelAndView mav) {
        if (mav != null) {
            mav.addObject(CONTEXT_PATH, request.getContextPath());
            mav.addObject(ENVIRONMENT, Environment.global().current());
        }
    }
}
