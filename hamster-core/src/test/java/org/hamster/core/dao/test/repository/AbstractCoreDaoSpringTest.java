/**
 * 
 */
package org.hamster.core.dao.test.repository;

import java.util.Date;

import org.hamster.core.api.consts.StatusType;
import org.hamster.core.api.model.base.ManageableIfc;
import org.hamster.core.api.model.base.OrderIfc;
import org.hamster.core.dao.entity.PersistentLoginsEntity;
import org.hamster.core.dao.entity.RefDataEntity;
import org.hamster.core.test.repository.AbstractDaoSpringTest;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@SpringBootTest(classes = DaoConfiguration.class)
public abstract class AbstractCoreDaoSpringTest extends AbstractDaoSpringTest {

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.hamster.core.test.repository.AbstractDaoSpringTest#initCommonData()
     */
    @Override
    protected void initCommonData(TestEntityManager entityManager) {
        entityManager.persist(order(new RefDataEntity("key1", "value1", "This Is Label 1", 1), 1));
        entityManager.persist(order(new RefDataEntity("key1", "value2", "This Is Label 2", 2), 2));
        entityManager.persist(new RefDataEntity("key1", "value3", "This Is Label 3", 3));
        entityManager.persist(new RefDataEntity("key1", "value4", "This Is Label 4", 4));
        
        entityManager.persist(enrich(active(new RefDataEntity("key2", "value1", "This Is Label 1", 1))));
        entityManager.persist(enrich(active(new RefDataEntity("key2", "value2", "This Is Label 2", 2))));
        entityManager.persist(active(new RefDataEntity("key1", "value4", "This Is Label 4", 4)));
        
        entityManager.persist(inactive(new RefDataEntity("key1", "value1", "This Is Label 1111", 1)));
        entityManager.persist(inactive(new RefDataEntity("key1", "value2", "This Is Label 2222", 2)));
        entityManager.persist(inactive(new RefDataEntity("key1", "value4", "This Is Label 4333", 4)));
        
        entityManager.persist(new PersistentLoginsEntity("username22", "series2", "token value 233", new Date()));
        entityManager.persist(new PersistentLoginsEntity("username33", "series4", "token to be removed", new Date()));
        
        entityManager.flush();
    }
    
    private static <T extends ManageableIfc<Long>> T inactive(T entity) {
        entity.setStatus(StatusType.INACTIVE);
        return entity;
    }
    
    private static <T extends OrderIfc<Long>> T order(T entity, int index) {
        entity.setOrder(index);
        return entity;
    }
    
    private static <T extends ManageableIfc<Long>> T active(T entity) {
        entity.setStatus(StatusType.ACTIVE);
        return entity;
    }
    
    private static <T extends ManageableIfc<Long>> T enrich(T entity) {
        entity.setCreatedBy("Jack Yin");
        entity.setCreatedOn(new Date(10000));
        entity.setUpdatedBy("John Doe");
        entity.setUpdatedOn(new Date(20000));
        return entity;
    }
    

}

@Configuration
@EntityScan("org.hamster.core.dao.entity")
@EnableJpaRepositories("org.hamster.core.dao.repository")
@ComponentScan("org.hamster.core.dao.repository")
class DaoConfiguration {

}
