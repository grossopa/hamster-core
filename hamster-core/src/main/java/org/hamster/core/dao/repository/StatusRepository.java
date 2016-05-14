/**
 * 
 */
package org.hamster.core.dao.repository;

import java.util.List;

import org.hamster.core.api.model.base.StatusIfc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Status Repository
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @param <I>
 *            type of id
 * @since 1.0
 */
public interface StatusRepository<T extends StatusIfc<I>, I> {

    /**
     * find item by status
     * 
     * @param status
     * @return
     */
    List<T> findByStatus(String status);

    /**
     * find item by status with page result
     * 
     * @param status
     * @param pageable
     * @return
     */
    Page<T> findByStatus(String status, Pageable pageable);
}
