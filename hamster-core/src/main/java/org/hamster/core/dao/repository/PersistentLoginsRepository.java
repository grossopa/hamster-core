/**
 * 
 */
package org.hamster.core.dao.repository;

import java.util.List;

import org.hamster.core.dao.entity.PersistentLoginsEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for {@link PersistentLoginsEntity}
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Repository
public interface PersistentLoginsRepository extends PagingAndSortingRepository<PersistentLoginsEntity, String> {

    /**
     * @param username
     * @return
     */
    List<PersistentLoginsEntity> findByUsername(String username);
    
}
