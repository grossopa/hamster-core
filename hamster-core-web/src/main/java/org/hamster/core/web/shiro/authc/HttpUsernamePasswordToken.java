/**
 * 
 */
package org.hamster.core.web.shiro.authc;

import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.UsernamePasswordToken;

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
    
    public enrich(HttpServletRequest request) {
        cookies = ImmutableSet.copyOf(request.getCookies());
        headers = request.getr
    }

}
