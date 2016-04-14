/**
 * 
 */
package org.hamster.core.web.controller.exception;

/**
 * abstract converter provides order support
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractExceptionConverter implements ExceptionConverter {

    private int order;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.core.Ordered#getOrder()
     */
    @Override
    public int getOrder() {
        return this.order;
    }

    /**
     * order to set
     * 
     * @param order
     */
    public void setOrder(int order) {
        this.order = order;
    }

}
