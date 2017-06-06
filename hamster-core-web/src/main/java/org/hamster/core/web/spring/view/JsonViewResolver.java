/**
 * 
 */
package org.hamster.core.web.spring.view;

import java.util.Locale;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.json.AbstractJackson2View;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

/**
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class JsonViewResolver implements ViewResolver, Ordered {

    @Setter
    private AbstractJackson2View view;

    @Getter
    @Setter
    private int order;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.web.servlet.ViewResolver#resolveViewName(java.lang.String, java.util.Locale)
     */
    @Override
    public View resolveViewName(String viewName, Locale locale) throws Exception {
        if (view != null) {
            return view;
        }
        view = new MappingJackson2JsonView();
        view.setPrettyPrint(true);
        view.setObjectMapper(new ObjectMapper());
        return view;
    }

}
