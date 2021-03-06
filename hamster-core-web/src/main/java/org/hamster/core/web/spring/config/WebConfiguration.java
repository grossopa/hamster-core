/**
 * 
 */
package org.hamster.core.web.spring.config;

import org.hamster.core.web.spring.view.JsonViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.google.common.collect.Lists;

/**
 * for the web configuration
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class WebConfiguration {

    /**
     * Supplied by Spring boot
     */
    @Autowired
    protected ThymeleafViewResolver thymeleafViewResolver;

    @Bean
    public ContentNegotiatingViewResolver cnViewResolver() {
        enrichThymeleafViewResolver(thymeleafViewResolver);

        ContentNegotiatingViewResolver cnResolver = new ContentNegotiatingViewResolver();
        cnResolver.setContentNegotiationManager(cnManagerFactoryBean().getObject());
        cnResolver.setViewResolvers(Lists.<ViewResolver>newArrayList(jsonViewResolver(), thymeleafViewResolver,
                internalResourceViewResolver()));
        return cnResolver;
    }

    /**
     * JSON ViewResolver
     * 
     * @return
     */
    public JsonViewResolver jsonViewResolver() {
        JsonViewResolver jsonViewResolver = new JsonViewResolver();
        jsonViewResolver.setOrder(10);
        return jsonViewResolver;
    }

    /**
     * Thymeleaf template ViewResolver
     * 
     * @return
     */
    protected void enrichThymeleafViewResolver(ThymeleafViewResolver thymeleafViewResolver) {
        thymeleafViewResolver.setOrder(20);
    }

    /**
     * Default JSP ViewResolver
     * 
     * @return
     */
    public InternalResourceViewResolver internalResourceViewResolver() {
        InternalResourceViewResolver irViewResolver = new InternalResourceViewResolver();
        irViewResolver.setPrefix("/WEB-INF/views");
        irViewResolver.setSuffix(".jsp");
        irViewResolver.setOrder(100);
        return irViewResolver;
    }

    /**
     * Uses suffix to identify the ViewResolver
     * 
     * @return
     */
    public ContentNegotiationManagerFactoryBean cnManagerFactoryBean() {
        ContentNegotiationManagerFactoryBean cnManagerFactoryBean = new ContentNegotiationManagerFactoryBean();
        cnManagerFactoryBean.setIgnoreAcceptHeader(true);
        cnManagerFactoryBean.setIgnoreUnknownPathExtensions(true);
        cnManagerFactoryBean.setDefaultContentType(MediaType.TEXT_HTML);
        cnManagerFactoryBean.addMediaType("html", MediaType.TEXT_HTML);
        cnManagerFactoryBean.addMediaType("json", MediaType.APPLICATION_JSON_UTF8);
        return cnManagerFactoryBean;
    }
}
