/**
 * 
 */
package org.hamster.core.api.test.consts;

import org.hamster.core.api.consts.StatusType;
import org.hamster.core.test.helper.Coverage;
import org.junit.Test;

/**
 *
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class StatueTypeTest {
    @Test
    public void testCoverage() {
        Coverage.coverUtilConstructor(StatusType.class);
    }
}
