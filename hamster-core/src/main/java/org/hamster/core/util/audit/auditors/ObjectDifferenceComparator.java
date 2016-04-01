/**
 * 
 */
package org.hamster.core.util.audit.auditors;

import org.apache.commons.lang3.ObjectUtils;
import org.hamster.core.util.audit.auditors.base.AbstractDifferenceTypeComparator;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ObjectDifferenceComparator extends AbstractDifferenceTypeComparator<Object> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.Auditor#canCompare(java.lang.Class)
     */
    @Override
    public boolean canCompare(Class<?> propertyType) {
        return !Iterable.class.isAssignableFrom(propertyType);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.base.Auditor#compare(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean compare(Object leftValue, Object rightValue, Object leftObject, Object rightObject) {
        return !ObjectUtils.notEqual(leftValue, rightValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.base.AbstractTypeAuditor#getPropertyType()
     */
    @Override
    public Class<Object> getPropertyType() {
        return Object.class;
    }

}
