/**
 * 
 */
package org.hamster.core.web.controller.page;

import org.hamster.core.web.controller.AbstractController;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractPageController extends AbstractController {
    
    @ModelAttribute("hs_application")
    public abstract String getApplication();
    
}
