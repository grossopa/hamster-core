/**
 * 
 */
package org.hamster.core.web.spring.boot;

import org.hamster.core.api.environment.initializer.DefaultEnvironmentContextInitializer;
import org.hamster.core.web.spring.view.JsonViewResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import com.google.common.collect.Lists;

/**
 * 
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractApplication {
    
    @Autowired
    private ThymeleafViewResolver thymeleafViewResolver;
    
    /**
     * creates an application and registers default initializers
     * 
     * @param clazz
     * @return
     */
    public static SpringApplication create(Class<?> clazz) {
        SpringApplication application = new SpringApplication(new Object[] { clazz });
        application.addInitializers(new DefaultEnvironmentContextInitializer());
        return application;
    }
    
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
    protected JsonViewResolver jsonViewResolver() {
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
    protected InternalResourceViewResolver internalResourceViewResolver() {
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
    protected ContentNegotiationManagerFactoryBean cnManagerFactoryBean() {
        ContentNegotiationManagerFactoryBean cnManagerFactoryBean = new ContentNegotiationManagerFactoryBean();
        cnManagerFactoryBean.setIgnoreAcceptHeader(true);
        cnManagerFactoryBean.setIgnoreUnknownPathExtensions(true);
        cnManagerFactoryBean.setDefaultContentType(MediaType.TEXT_HTML);
        cnManagerFactoryBean.addMediaType("html", MediaType.TEXT_HTML);
        cnManagerFactoryBean.addMediaType("json", MediaType.APPLICATION_JSON_UTF8);
        return cnManagerFactoryBean;
    }
    
}
