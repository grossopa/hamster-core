/**
 * 
 */
package org.hamster.core.api.util.difference.comparator.defaults;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hamster.core.api.util.ComparatorUtils;
import org.hamster.core.api.util.difference.comparator.PropertyComparator;

import com.google.common.collect.Lists;

/**
 * compare two collections, this would sort the list so order is not considered.
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public abstract class AbstractCollectionComparator implements PropertyComparator {
    
    /*
     * (non-Javadoc)
     * 
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public int compare(Object o1, Object o2) {
        Collection c1 = null;
        Collection c2 = null;
        if (o1 instanceof Array) {
            c1 = Lists.newArrayList((Object[]) o1);
            c2 = Lists.newArrayList((Object[]) o2);
        } else {
           c1 = (Collection) o1;
           c2 = (Collection) o2;
        }
       
        if (CollectionUtils.isEmpty(c1)) {
            return CollectionUtils.isEmpty(c2) ? 0 : 1;
        } else if (CollectionUtils.isEmpty(c2)) {
            return -1;
        } else if (c1.size() != c2.size()) {
            return -1;
        }

        List l1 = createSortedList(c1);
        List l2 = createSortedList(c2);
        
        for (int i = 0; i < l1.size(); i++) {
            Object co1 = l1.get(i);
            Object co2 = l2.get(i);
            int result = compareListObjects(co1, co2);
            if (result != 0) {
                return result;
            }
        }
        
        return 0;
    }

    /**
     * Convert collection into new sorted list
     * 
     * @param coll
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    protected List createSortedList(Collection coll) {
        List list = Lists.newArrayList(coll);
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                Integer result = ComparatorUtils.obviateNull(o1, o2);
                if (result == null) {
                    result = Integer.compare(o1.hashCode(), o2.hashCode());
                }
                return result;
            }
            
        });
        return list;
    }
    
    /**
     * compare the children lists
     * 
     * @param o1
     * @param o2
     * @return
     */
    protected abstract int compareListObjects(Object o1, Object o2);

    /*
     * (non-Javadoc)
     * 
     * @see org.hamster.core.api.util.difference.comparator.PropertyComparator#canCompare(java.lang.String, java.lang.reflect.Method)
     */
    @Override
    public boolean canCompare(String property, Method getterMethod) {
        return Collection.class.isAssignableFrom(getterMethod.getReturnType())
                || getterMethod.getReturnType().isArray();
    }

}
