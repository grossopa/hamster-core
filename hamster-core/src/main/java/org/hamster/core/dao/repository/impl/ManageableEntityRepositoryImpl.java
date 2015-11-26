/**
 * 
 */
package org.hamster.core.dao.repository.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hamster.core.api.model.base.ManageableIfc;
import org.hamster.core.dao.consts.StatusEntityType;
import org.hamster.core.dao.entity.base.StatusEntity;
import org.hamster.core.dao.repository.CriteriaQueryFactory;
import org.hamster.core.dao.repository.ManageableEntityRepository;

/**
 * Default implementation
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public class ManageableEntityRepositoryImpl implements ManageableEntityRepository<Long, ManageableIfc<Long>> {

    EntityManager entityManager;

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.dao.repository.ManageableEntityRepository#findAllActive(java.lang.Class)
     */
    @Override
    public List<ManageableIfc<Long>> findAllActive(Class<ManageableIfc<Long>> clazz) {
        return findAllActive(clazz, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.dao.repository.ManageableEntityRepository#findAllActive(java.lang.Class,
     * org.hamster.core.dao.repository.CriteriaQueryFactory)
     */
    @Override
    public List<ManageableIfc<Long>> findAllActive(Class<ManageableIfc<Long>> clazz, CriteriaQueryFactory<ManageableIfc<Long>> factory) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ManageableIfc<Long>> cq = cb.createQuery(clazz);
        Root<ManageableIfc<Long>> root = cq.from(clazz);

        Predicate equalActive = cb.equal(root.get(StatusEntity.PROP_STATUS), StatusEntityType.ACTIVE);

        Predicate factoryResult;
        if (factory != null && (factoryResult = factory.build(cb, cq, root)) != null) {
            cq.where(cb.and(equalActive, factoryResult));
        } else {
            cq.where(equalActive);
        }

        return entityManager.createQuery(cq).getResultList();
    }

    /**
     * set the entityManager
     * 
     * @param entityManager
     */
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
