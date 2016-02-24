/**
 * 
 */
package org.hamster.core.util.audit.auditors.base;


/**
 * auditor used to do comparing between single properties of two object
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
public interface Auditor<P> {

    /**
     * determine if this auditor can do comparing for certain property type
     * 
     * @param object
     * @param propertyType
     * @return
     */
    boolean canCompare(Class<?> propertyType);
    
    /**
     * 
     * @param leftValue
     * @param rightValue
     * @param leftObject
     * @param rightObject
     * @return true if they are NOT different, otherwise return false
     */
    boolean compare(P leftValue, P rightValue, Object leftObject, Object rightObject);
}
