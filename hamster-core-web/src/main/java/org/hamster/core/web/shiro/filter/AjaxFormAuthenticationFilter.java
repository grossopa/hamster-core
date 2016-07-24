/**
 * 
 */
package org.hamster.core.web.shiro.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.hamster.mobile.constant.StatusCode;
import org.hamster.mobile.model.vo.ErrorVO;
import org.hamster.mobile.model.vo.RespVO;
import org.hamster.mobile.util.JsonUtils;
import org.hamster.mobile.util.Msgs;
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

        JsonUtils.toJson(RespVO.success(), response.getWriter());
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

            ErrorVO result = null;
            if ("IncorrectCredentialsException".equals(message)) {
                result = ErrorVO.error(StatusCode.LOGIN_PASSWORD_WRONG, Msgs.get(Msgs.STATUS_LOGIN_PASSWORD_WRONG));
            } else if ("UnknownAccountException".equals(message)) {
                result = ErrorVO.error(StatusCode.LOGIN_USERNAME_WRONG, Msgs.get(Msgs.STATUS_LOGIN_USERNAME_WRONG));
            } else if ("LockedAccountException".equals(message)) {
                result = ErrorVO.error(StatusCode.LOGIN_USER_BLOCKED, Msgs.get(Msgs.STATUS_LOGIN_USER_BLOCKED));
            } else {
                result = ErrorVO.error(StatusCode.ERROR, Msgs.get(Msgs.STATUS_ERROR));
            }
            JsonUtils.toJson(result, response.getWriter());
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
                ErrorVO result = ErrorVO.error(StatusCode.LOGIN_REQUIRE_LOGIN, Msgs.get(Msgs.STATUS_LOGIN_REQUIRE_LOGIN));
                JsonUtils.toJson(result, response.getWriter());
                return false;
            }
        } else {
            response.setCharacterEncoding("UTF-8");
            ErrorVO result = ErrorVO.error(StatusCode.LOGIN_PASSWORD_WRONG, Msgs.get(Msgs.STATUS_LOGIN_PASSWORD_WRONG));
            JsonUtils.toJson(result, response.getWriter());
            return false;
        }
    }

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
        String username = getUsername(request);
        String password = getPassword(request);
        return createToken(username, password, request, response);
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, ServletRequest request, ServletResponse response) {
        boolean rememberMe = isRememberMe(request);
        String host = getHost(request);
        return createToken(username, password, rememberMe, host);
    }

    @Override
    protected AuthenticationToken createToken(String username, String password, boolean rememberMe, String host) {
        return new UsernamePasswordToken(username, password, rememberMe, host);
    }

}
