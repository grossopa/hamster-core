/**
 * 
 */
package org.hamster.core.dao.repository;

import java.util.List;

import org.hamster.core.api.consts.StatusType;
import org.hamster.core.dao.entity.RefDataEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Repository of {@link RefDataEntity}
 *
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public interface RefDataRepository extends PagingAndSortingRepository<RefDataEntity, Long> {

    /**
     * find all ref data by status
     * 
     * @param status
     * @return
     */
    public List<RefDataEntity> findByStatus(String status);

    /**
     * finds all active ref data
     * 
     * @return all active ref data
     */
    @Query("from RefDataEntity where status = '" + StatusType.ACTIVE + "'")
    public List<RefDataEntity> findAllActive();
}
