/**
 * 
 */
package org.hamster.core.util.audit.auditors;

import org.apache.commons.lang3.BooleanUtils;
import org.hamster.core.util.audit.auditors.base.AbstractTypeAuditor;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class BooleanAuditor extends AbstractTypeAuditor<Boolean> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.base.Auditor#compare(java.lang.Object, java.lang.Object,
     * java.lang.Object, java.lang.Object)
     */
    @Override
    public boolean compare(Boolean leftValue, Boolean rightValue, Object leftObject, Object rightObject) {
        return BooleanUtils.compare(leftValue, rightValue) == 0;
    }

    /* (non-Javadoc)
     * @see org.hamster.core.util.audit.auditors.base.AbstractTypeAuditor#getPropertyType()
     */
    @Override
    public Class<Boolean> getPropertyType() {
        return Boolean.class;
    }

}
