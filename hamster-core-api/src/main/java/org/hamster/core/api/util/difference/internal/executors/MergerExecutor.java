/**
 * 
 */
package org.hamster.core.api.util.difference.internal.executors;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;
import org.hamster.core.api.util.ReflectUtils;
import org.hamster.core.api.util.difference.DiffChecker.Config;
import org.hamster.core.api.util.difference.merger.Merger;
import org.hamster.core.api.util.difference.model.DiffObjectVO;
import org.hamster.core.api.util.difference.model.DiffResultVO;
import org.hamster.core.api.util.difference.model.DiffType;

import com.google.common.collect.Sets;

/**
 * executor merger
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class MergerExecutor<K, T> {
    private final Config<K, T> config;

    /**
     * Constructor
     * 
     * @param config
     */
    public MergerExecutor(Config<K, T> config) {
        this.config = config;
    }

    public void doMerge(Class<T> clazz, Collection<T> sourceColl, Collection<T> targetColl, DiffResultVO<K, T> diffResultVO) {
        Merger<K, T> merger = config.getMerger();

        Map<String, Pair<Method, Method>> methods = findProperties(clazz);

        merger.mergeAdded(sourceColl, diffResultVO.getAddedColl());
        merger.mergeRemoved(sourceColl, diffResultVO.getRemovedColl());

        for (DiffObjectVO diffObject : diffResultVO.getResults().get(DiffType.CHANGE)) {
            Set<String> changedValues = diffObject.getPropertyList().keySet();
            Object key = diffObject.getId();
            merger.mergeChanged(methods, changedValues, 
                    diffResultVO.getChangedColl().get(key).getLeft(),
                    diffResultVO.getChangedColl().get(key).getRight());
        }
    }

    /**
     * find interested getter/setter pairs from class
     * 
     * @param clazz
     * @return property/method pair
     */
    protected Map<String, Pair<Method, Method>> findProperties(Class<T> clazz) {
        Set<String> temp = Sets.newHashSet(config.getProperties());
        if (!config.isExclude()) {
            // id property must be included
            temp.add(config.getIdProperty());
        }
        return ReflectUtils.findGetterSetterMethods(clazz, config.isExclude(), temp.toArray(new String[] {}));
    }
}
