/**
 * 
 */
package org.hamster.core.web.spring.interceptor;

import java.util.Optional;

import org.springframework.web.servlet.config.annotation.InterceptorRegistration;

/**
 *
 * @see {@link InterceptorRegistration}
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface InterceptorPathPatterns {

    /**
     * 
     * @see {@link InterceptorRegistration#addPathPatterns(String...)}
     * @return path patterns for the Interceptor, null to ignore
     */
    Optional<String[]> pathPatterns();

    /**
     * 
     * @see {@link InterceptorRegistration#excludePathPatterns(String...)}
     * @return path patterns to exclude for the Interceptor, null to ignore
     */
    Optional<String[]> excludePathPatterns();
}
