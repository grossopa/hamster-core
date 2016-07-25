/**
 * 
 */
package org.hamster.core.api.util;

import java.io.IOException;
import java.io.Writer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class JsonUtils {
    private static final Logger log = LoggerFactory.getLogger(JsonUtils.class);

    private JsonUtils() {
    }

    public static final String toJson(Object object) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    public static final String toJson(Object object, Writer out) {
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(out, object);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return "";
    }
}
