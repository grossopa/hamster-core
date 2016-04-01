/**
 * 
 */
package org.hamster.core.util.audit.auditors;

import org.hamster.core.util.audit.auditors.base.AbstractDifferenceTypeComparator;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class NumberDifferenceComparator extends AbstractDifferenceTypeComparator<Number> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.base.Auditor#compare(java.lang.Object, java.lang.Object, java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean compare(Number leftValue, Number rightValue, Object leftObject, Object rightObject) {
        return Double.compare(leftValue.doubleValue(), rightValue.doubleValue()) == 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.base.AbstractTypeAuditor#getPropertyType()
     */
    @Override
    public Class<Number> getPropertyType() {
        return Number.class;
    }

}
