/**
 * 
 */
package org.hamster.core.web.shiro.authc;

import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.UsernamePasswordToken;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
public class HttpUsernamePasswordToken extends UsernamePasswordToken {

    private static final long serialVersionUID = 1L;

    /**
     * cookies from request
     */
    private Set<Cookie> cookies;

    /**
     * request headers (not including cookie details)
     */
    private Map<String, String> headers;

    /**
     * 
     */
    public HttpUsernamePasswordToken() {
        super();
    }

    /**
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     */
    public HttpUsernamePasswordToken(String username, char[] password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

    /**
     * @param username
     * @param password
     * @param rememberMe
     */
    public HttpUsernamePasswordToken(String username, char[] password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    /**
     * @param username
     * @param password
     * @param host
     */
    public HttpUsernamePasswordToken(String username, char[] password, String host) {
        super(username, password, host);
    }

    /**
     * @param username
     * @param password
     */
    public HttpUsernamePasswordToken(String username, char[] password) {
        super(username, password);
    }

    /**
     * @param username
     * @param password
     * @param rememberMe
     * @param host
     */
    public HttpUsernamePasswordToken(String username, String password, boolean rememberMe, String host) {
        super(username, password, rememberMe, host);
    }

    /**
     * @param username
     * @param password
     * @param rememberMe
     */
    public HttpUsernamePasswordToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }

    /**
     * @param username
     * @param password
     * @param host
     */
    public HttpUsernamePasswordToken(String username, String password, String host) {
        super(username, password, host);
    }

    /**
     * @param username
     * @param password
     */
    public HttpUsernamePasswordToken(String username, String password) {
        super(username, password);
    }

    /**
     * set cookies and headers
     * 
     * @param request
     */
    public void enrich(HttpServletRequest request) {
        cookies = ImmutableSet.copyOf(request.getCookies());

        Map<String, String> temp = Maps.newHashMap();
        Enumeration<String> headerKeys = request.getHeaderNames();
        while (headerKeys.hasMoreElements()) {
            String headerKey = headerKeys.nextElement();
            temp.put(headerKey, request.getHeader(headerKey));
        }

        headers = ImmutableMap.copyOf(temp);
    }
    
    public String getHeader(String name) {
        return headers.get(name);
    }
    
    public Cookie getCookie(String name) {
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(name)) {
                return cookie;
            }
        }
        return null;
    }

}
