/**
 * 
 */
package org.hamster.core.api.model.base;

/**
 * As Hibernate requires Entity must contain an id and a good practice is to use consistent name id
 * for the primary key with auto-generation policy.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface IdIfc<T> {

    /**
     * return the identity
     * 
     * @return the id
     */
    T getId();

    /**
     * set the identity
     * 
     * @param id
     */
    void setId(T id);
}
