/**
 * 
 */
package org.hamster.core.dao.test.repository;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ManageableEntityRepositoryImplTest {
    
    private EntityManager entityManager;
    
    @Before
    public void before() {
        entityManager = Mockito.mock(EntityManager.class);
        
    }
    
    @Test
    public void testFindAllActive() {
        //FIXME: not sure how to test
    }
}
