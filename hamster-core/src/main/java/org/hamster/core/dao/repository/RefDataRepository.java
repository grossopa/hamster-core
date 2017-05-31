/**
 * 
 */
package org.hamster.core.dao.repository;

import java.util.List;

import org.hamster.core.api.consts.StatusType;
import org.hamster.core.dao.entity.RefDataEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository of {@link RefDataEntity}
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Repository
public interface RefDataRepository extends PagingAndSortingRepository<RefDataEntity, Long> {

    /**
     * find all ref data by status
     * 
     * @param status
     * @return
     */
    List<RefDataEntity> findByStatus(String status);

    /**
     * finds all active ref data
     * 
     * @return all active ref data
     */
    @Query("from RefDataEntity where status = '" + StatusType.ACTIVE + "'")
    List<RefDataEntity> findAllActive();

    /**
     * @param string
     * @return
     */
    List<RefDataEntity> findByKey(String string);

    /**
     * @param string
     * @param active
     * @return
     */
    List<RefDataEntity> findByKeyAndStatus(String string, String active);
}
