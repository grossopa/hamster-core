/**
 * 
 */
package org.hamster.core.util.audit.auditors;

import org.apache.commons.lang3.StringUtils;
import org.hamster.core.util.audit.auditors.base.AbstractTypeAuditor;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class StringAuditor extends AbstractTypeAuditor<CharSequence> {

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.Auditor#compare(java.lang.Object, java.lang.Object, java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public boolean compare(CharSequence leftValue, CharSequence rightValue, Object leftObject, Object rightObject) {
        return StringUtils.equals(leftValue, rightValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.util.audit.auditors.AbstractTypeAuditor#getPropertyType()
     */
    @Override
    public Class<CharSequence> getPropertyType() {
        return CharSequence.class;
    }

}
