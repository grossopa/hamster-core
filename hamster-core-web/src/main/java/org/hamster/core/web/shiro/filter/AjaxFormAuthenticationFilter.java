/**
 * 
 */
package org.hamster.core.web.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.hamster.core.api.util.JsonUtils;
import org.hamster.core.web.controller.dto.ErrorDto;
import org.hamster.core.web.controller.dto.ResultDto;
import org.hamster.core.web.shiro.authc.HttpUsernamePasswordToken;
import org.hamster.core.web.shiro.util.ShiroFilterUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">grossopaforever@gmail.com</a>
 * @version May 18, 2014 12:10:22 PM
 */
public class AjaxFormAuthenticationFilter extends FormAuthenticationFilter {

    private static final Logger log = LoggerFactory.getLogger(AjaxFormAuthenticationFilter.class);

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.shiro.web.filter.authc.FormAuthenticationFilter#onLoginSuccess(org.apache.shiro.authc.AuthenticationToken , org.apache.shiro.subject.Subject, javax.servlet.ServletRequest,
     * javax.servlet.ServletResponse)
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        if (!ShiroFilterUtils.isAjaxRequest(request)) {
            return super.onLoginSuccess(token, subject, request, response);
        }

        JsonUtils.toJson(ResultDto.of("Success"), response.getWriter());
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        if (!ShiroFilterUtils.isAjaxRequest(request)) {
            return super.onLoginFailure(token, e, request, response);
        }

        response.setCharacterEncoding("UTF-8");
        try {
            String message = e.getClass().getSimpleName();
            JsonUtils.toJson(ErrorDto.of(message), response.getWriter());
            return false;
        } catch (IOException e1) {
            log.error(e1.getMessage(), e1);
        }
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        if (!ShiroFilterUtils.isAjaxRequest(request)) {
            return super.onAccessDenied(request, response);
        }
        if (isLoginRequest(request, response)) {
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response);
            } else {
                response.setCharacterEncoding("UTF-8");
                JsonUtils.toJson(ErrorDto.of("login failed"), response.getWriter());
                return false;
            }
        } else {
            response.setCharacterEncoding("UTF-8");
            JsonUtils.toJson(ErrorDto.of("login failed"), response.getWriter());
            return false;
        }
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        if (request instanceof HttpServletRequest) {
            return createHttpToken(username, password, rememberMe, host, (HttpServletRequest) request);
        } else {
            return super.createToken(username, password, rememberMe, host);
        }
    }

    protected AuthenticationToken createHttpToken(String username, String password, boolean rememberMe, String host, HttpServletRequest httpRequest) {
        HttpUsernamePasswordToken token = new HttpUsernamePasswordToken(username, password, rememberMe, host);
        token.enrich(httpRequest);
        return token;
    }

}
