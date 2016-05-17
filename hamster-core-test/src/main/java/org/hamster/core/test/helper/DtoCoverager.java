/**
 * 
 */
package org.hamster.core.test.helper;

import org.junit.Assert;

/**
 * For coverage propose
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DtoCoverager<T> {
    
    private T target;
    
    public static final <T> DtoCoverager<T> of(T target) {
        return new DtoCoverager<T>(target);
    }
    
    /**
     * 
     * @param target
     */
    private DtoCoverager(T target) {
        this.target = target;
    }

    /**
     * to cover <tt>toString()</tt> Method
     */
    public void coverToString() {
        target.toString();
    }
    
    public void coverEquals() {
        Assert.assertTrue(target.equals(target));
    }
    
    public void coverHashCode() {
        target.hashCode();
    }
    
    public void coverAll() {
        coverToString();
        coverEquals();
        coverHashCode();
    }
    
    
}
