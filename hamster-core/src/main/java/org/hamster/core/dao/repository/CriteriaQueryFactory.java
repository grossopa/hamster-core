/**
 * 
 */
package org.hamster.core.dao.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * factory method to allow developer to add extra conditions
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface CriteriaQueryFactory<T> {

    /**
     * Callback method to add extra conditions and return Predicate, outer class will be responsible to call
     * CriteriaBuilder.where.
     * 
     * @param cb
     * @param cq
     * @param root
     * @return the Predicate or null if not necessary
     */
    public Predicate build(CriteriaBuilder cb, CriteriaQuery<T> cq, Root<T> root);
}
