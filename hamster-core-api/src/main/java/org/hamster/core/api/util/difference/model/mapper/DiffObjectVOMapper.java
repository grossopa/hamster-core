/**
 * 
 */
package org.hamster.core.api.util.difference.model.mapper;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.difference.comparator.PropertyComparator;
import org.hamster.core.api.util.difference.model.DiffObjectVO;
import org.hamster.core.api.util.difference.model.DiffType;
import org.hamster.core.api.util.difference.model.DiffVO;
import org.hamster.core.api.util.difference.transformer.IdInvoker;
import org.hamster.core.api.util.difference.transformer.PropertyInvoker;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Mapper for {@link DiffObjectVO}
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DiffObjectVOMapper<K, T> {

    private final IdInvoker<K, T> idInvoker;

    private final PropertyInvoker<T> propertyInvoker;

    private final Map<String, Method> methods;

    private final List<PropertyComparator> propertyComparators;

    /**
     * constructor, idInvoker and propertyInvoker must be non-null
     * 
     * @param idInvoker
     * @param propertyInvoker
     * @param methods
     *            property/method pair, getter method must be no arguments
     */
    public DiffObjectVOMapper(IdInvoker<K, T> idInvoker, PropertyInvoker<T> propertyInvoker, Map<String, Method> methods,
            List<PropertyComparator> propertyComparators) {
        this.idInvoker = idInvoker;
        this.propertyInvoker = propertyInvoker;
        this.methods = methods;
        this.propertyComparators = propertyComparators;
    }

    /**
     * Extract added collection details into list of {@link DiffObjectVO}
     * 
     * @param addedColl
     *            calculated added collection
     * @param methods
     * @return
     */
    public List<DiffObjectVO> mapAddedColl(Collection<T> addedColl) {
        List<DiffObjectVO> result = Lists.newArrayList();
        for (T addedItem : addedColl) {
            DiffObjectVO vo = new DiffObjectVO();
            // here id could be null, which is allowed for added item
            vo.setId(idInvoker.invoke(methods, addedItem));
            vo.setType(DiffType.ADD);
            vo.setPropertyList(mapAddOrRemovedToPropertyList(addedItem, DiffType.ADD));

            result.add(vo);
        }
        return result;
    }

    /**
     * Extract removed collection details into list of {@link DiffObjectVO}
     * 
     * @param removedColl
     * @param methods
     * @return
     */
    public List<DiffObjectVO> mapRemovedColl(Map<K, T> removedColl) {
        List<DiffObjectVO> result = Lists.newArrayList();
        for (Map.Entry<K, T> entry : removedColl.entrySet()) {
            K key = entry.getKey();
            T removedItem = entry.getValue();

            DiffObjectVO vo = new DiffObjectVO();
            // here id could not be null ideally
            vo.setId(key);
            vo.setType(DiffType.REMOVAL);
            vo.setPropertyList(mapAddOrRemovedToPropertyList(removedItem, DiffType.REMOVAL));

            result.add(vo);
        }
        return result;
    }

    /**
     * Extract changed collection details into list of {@link DiffObjectVO}
     * 
     * @param changedColl
     * @return
     */
    public List<DiffObjectVO> mapChangedColl(Map<K, Pair<T, T>> changedColl) {
        List<DiffObjectVO> result = Lists.newArrayList();
        for (Map.Entry<K, Pair<T, T>> entry : changedColl.entrySet()) {
            K key = entry.getKey();
            T sourceItem = entry.getValue().getLeft();
            T targetItem = entry.getValue().getRight();

            DiffObjectVO vo = new DiffObjectVO();
            // here id could not be null ideally
            vo.setId(key);
            vo.setType(DiffType.CHANGE);
            vo.setPropertyList(mapChangedToPropertyList(sourceItem, targetItem));

            result.add(vo);
        }
        return result;
    }

    /**
     * Map property lists
     * 
     * @param object
     * @param methods
     * @param type
     * @return
     */
    private Map<String, DiffVO> mapAddOrRemovedToPropertyList(T object, DiffType type) {
        Map<String, DiffVO> result = Maps.newHashMap();

        for (Map.Entry<String, Method> entry : methods.entrySet()) {
            String property = entry.getKey();
            Method method = entry.getValue();

            Object value = propertyInvoker.invoke(property, method, object);

            DiffVO vo = new DiffVO();
            vo.setProperty(property);
            if (type == DiffType.ADD) {
                vo.setOldValue(null);
                vo.setNewValue(value);
            } else if (type == DiffType.REMOVAL) {
                vo.setOldValue(value);
                vo.setNewValue(null);
            }

            result.put(property, vo);
        }

        return result;
    }

    private Map<String, DiffVO> mapChangedToPropertyList(T source, T target) {
        Map<String, DiffVO> result = Maps.newHashMap();
        for (Map.Entry<String, Method> entry : methods.entrySet()) {
            String property = entry.getKey();
            Method method = entry.getValue();

            Object sourceValue = propertyInvoker.invoke(property, method, source);
            Object targetValue = propertyInvoker.invoke(property, method, target);

            if (!compareProperty(property, method, sourceValue, targetValue)) {
                DiffVO vo = new DiffVO();
                vo.setProperty(property);
                vo.setOldValue(sourceValue);
                vo.setNewValue(targetValue);
                result.put(property, vo);
            }
        }
        return result;
    }

    private boolean compareProperty(String property, Method getterMethod, Object sourceValue, Object targetValue) {
        for (PropertyComparator comparator : propertyComparators) {
            if (comparator.canCompare(property, getterMethod)) {
                int result = comparator.compare(sourceValue, targetValue);
                return result == 0;
            }
        }
        // cannot compare, default as true
        return true;
    }
}
