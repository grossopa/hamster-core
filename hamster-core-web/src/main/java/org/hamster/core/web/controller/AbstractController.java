/**
 * 
 */
package org.hamster.core.web.controller;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hamster.core.api.exception.dto.ExceptionDto;
import org.hamster.core.web.controller.exception.DefaultExceptionConverter;
import org.hamster.core.web.controller.exception.ExceptionConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.OrderComparator;

import com.google.common.collect.Lists;

import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractController {

    /**
     * optional exception converters
     */
    @Autowired(required = false)
    @Setter
    protected List<ExceptionConverter> exceptionConverters;

    /**
     * handle exception with converters
     * 
     * @param ex
     * @return
     */
    public ExceptionDto handleException(Exception ex) {
        if (CollectionUtils.isEmpty(exceptionConverters)) {
            return handleDefault(ex);
        }
        List<ExceptionConverter> sortedConverters = Lists.newArrayList(exceptionConverters);
        Collections.sort(sortedConverters, new OrderComparator());
        for (ExceptionConverter converter : sortedConverters) {
            if (converter.canHandle(ex)) {
                return converter.handle(ex);
            }
        }

        // no suitable converters found, handle by default
        return handleDefault(ex);
    }

    /**
     * 
     * @param ex
     * @return
     */
    private ExceptionDto handleDefault(Exception ex) {
        DefaultExceptionConverter converter = new DefaultExceptionConverter();
        return converter.handle(ex);
    }
    
    

}
