/**
 * 
 */
package org.hamster.core.dao.test.repository;

import java.util.List;

import org.hamster.core.api.consts.StatusType;
import org.hamster.core.dao.entity.RefDataEntity;
import org.hamster.core.dao.repository.RefDataRepository;
import org.hamster.core.test.helper.Asserts;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class RefDataRepositoryTest extends AbstractCoreDaoSpringTest {

    @Autowired
    private RefDataRepository refDataRepository;
    
    @Test
    public void testLoad() {
        Asserts.assertNotNull(refDataRepository);
    }
    
    @Test
    public void testFindByStatus() {
        List<RefDataEntity> result = refDataRepository.findByStatus(StatusType.ACTIVE);
        Asserts.assertGreaterThan(6, result.size());
    }
    
    @Test
    public void testFindByStatus2() {
        List<RefDataEntity> result = refDataRepository.findByStatus("not exist status");
        Asserts.assertEquals(0, result.size());
    }
    
    @Test
    public void testFindByStatusInactive() {
        List<RefDataEntity> result = refDataRepository.findByStatus(StatusType.INACTIVE);
        Asserts.assertEquals(3, result.size());
        result.forEach((entity) -> {
            Asserts.assertEquals(StatusType.INACTIVE, entity.getStatus());
        });
    }
    
    @Test
    public void testManageableEntity() {
        List<RefDataEntity> result = refDataRepository.findByKeyAndStatus("key2", StatusType.ACTIVE);
        Asserts.assertEquals(2, result.size());
        result.forEach((entity) -> {
            Asserts.assertEquals("Jack Yin", entity.getCreatedBy());
            Asserts.assertEquals(10000L, entity.getCreatedOn().getTime());
            Asserts.assertEquals("John Doe", entity.getUpdatedBy());
            Asserts.assertEquals(20000L, entity.getUpdatedOn().getTime());
        });
    }
    
    @Test
    public void testOrder() {
        List<RefDataEntity> result = refDataRepository.findByKeyAndStatus("key1", StatusType.ACTIVE);
        Asserts.assertEquals(5, result.size());
        result.forEach((entity) -> {
            Asserts.assertGreaterThan(0, entity.getOrder());
        });
    }
    
    @Test
    public void testFindAllActive() {
        List<RefDataEntity> r1 = refDataRepository.findAllActive();
        List<RefDataEntity> r2 = refDataRepository.findByStatus(StatusType.ACTIVE);
        Asserts.assertEquals(r1.size(), r2.size());
    }

}
