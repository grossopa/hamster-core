/**
 * 
 */
package org.hamster.core.dao.repository;

import java.util.List;

import org.hamster.core.api.model.base.ManageableIfc;

/**
 * repository for manageableEntity. it's suggested to extends this class in use of Spring data jpa.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface ManageableEntityRepository<I, M extends ManageableIfc<I>> {
    /**
     * find all active items
     * 
     * @param clazz
     * @return
     */
    List<M> findAllActive(Class<M> clazz);
    
    /**
     * find all active items and add extra conditions
     * 
     * @param clazz
     * @param factory
     * @return
     */
    List<M> findAllActive(Class<M> clazz, CriteriaQueryFactory<M> factory);

}
