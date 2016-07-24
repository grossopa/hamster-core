/**
 * 
 */
package org.hamster.core.web.shiro.util;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">grossopaforever@gmail.com</a>
 * @version Jun 2, 2014 3:56:56 PM
 */
public class ShiroFilterUtils {

    private ShiroFilterUtils() {
    }

    public static final boolean isAjaxRequest(ServletRequest request) {
        if (!StringUtils.isBlank(request.getParameter("ajax"))) {
            return true;
        } 
        if (request instanceof HttpServletRequest) {
            HttpServletRequest hsr = (HttpServletRequest) request;
            String appPath = hsr.getRequestURI().substring(hsr.getContextPath().length());
            if (appPath.startsWith("/ws")) {
                return true;
            }
        }
        return false;
    }
}
