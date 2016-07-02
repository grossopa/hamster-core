/**
 * 
 */
package org.hamster.core.api.util.difference.comparator.defaults;

/**
 * use {@link ObjectComparator} to compare each element.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class SimpleCollectionComparator extends AbstractCollectionComparator {

    private final ObjectComparator objectComparator = new ObjectComparator();

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.comparator.defaults.AbstractCollectionComparator#compareListObjects(java.lang.Object, java.lang.Object)
     */
    @Override
    protected int compareListObjects(Object o1, Object o2) {
        return objectComparator.compare(o1, o2);
    }

}
