/**
 * 
 */
package org.hamster.core.util.audit.model;

/**
 * difference on property level
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class DifferenceVO {
    private String property;
    private Object oldValue;
    private Object newValue;

    /**
     * @return the property
     */
    public String getProperty() {
        return property;
    }

    /**
     * @return the oldValue
     */
    public Object getOldValue() {
        return oldValue;
    }

    /**
     * @return the newValue
     */
    public Object getNewValue() {
        return newValue;
    }

    /**
     * @param property
     *            the property to set
     */
    public void setProperty(String property) {
        this.property = property;
    }

    /**
     * @param oldValue
     *            the oldValue to set
     */
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    /**
     * @param newValue
     *            the newValue to set
     */
    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

}
