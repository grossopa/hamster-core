/**
 * 
 */
package org.hamster.core.util.audit.auditors.base;

/**
 * Default Type Comparator
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public abstract class AbstractDifferenceTypeComparator<P> implements DifferenceComparator<P> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.Auditor#canCompare(java.lang.Class)
     */
    @Override
    public boolean canCompare(Class<?> propertyType) {
        return getPropertyType().isAssignableFrom(propertyType);
    }

    /**
     * 
     * @return the class type
     */
    abstract public Class<P> getPropertyType();
}
