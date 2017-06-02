/**
 * 
 */
package org.hamster.core.test.repository.test;

import org.hamster.core.test.helper.Asserts;
import org.hamster.core.test.repository.AbstractDaoSpringTest;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

/**
 *
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class AbstractDaoSpringTestTest {

    @Test
    public void testBefore() {
        
        final boolean[] called = new boolean[1];
        AbstractDaoSpringTest daoSpringTest = new AbstractDaoSpringTest() {
            
            @Override
            protected void initCommonData(TestEntityManager entityManager) {
                Asserts.assertFalse(called[0]);
                called[0] = true;
            }
        };
        daoSpringTest.before();
        
        Asserts.assertTrue(called[0]);
    }
}
