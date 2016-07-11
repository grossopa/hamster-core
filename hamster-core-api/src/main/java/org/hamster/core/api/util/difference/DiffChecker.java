/**
 * 
 */
package org.hamster.core.api.util.difference;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hamster.core.api.util.difference.comparator.PropertyComparator;
import org.hamster.core.api.util.difference.comparator.defaults.ObjectComparator;
import org.hamster.core.api.util.difference.comparator.defaults.SimpleCollectionComparator;
import org.hamster.core.api.util.difference.internal.children.CheckerWrapper;
import org.hamster.core.api.util.difference.internal.executors.CheckerExecutor;
import org.hamster.core.api.util.difference.internal.executors.MergerExecutor;
import org.hamster.core.api.util.difference.internal.model.ExecutorResult;
import org.hamster.core.api.util.difference.merger.Merger;
import org.hamster.core.api.util.difference.merger.defaults.DefaultMerger;
import org.hamster.core.api.util.difference.model.DiffObjectVO;
import org.hamster.core.api.util.difference.model.DiffPath;
import org.hamster.core.api.util.difference.model.DiffResultVO;
import org.hamster.core.api.util.difference.model.DiffType;
import org.hamster.core.api.util.difference.model.DiffVO;
import org.hamster.core.api.util.difference.model.mapper.DiffObjectVOMapper;
import org.hamster.core.api.util.difference.transformer.IdInvoker;
import org.hamster.core.api.util.difference.transformer.PropertyInvoker;
import org.hamster.core.api.util.difference.transformer.defaults.DefaultIdInvoker;
import org.hamster.core.api.util.difference.transformer.defaults.DefaultPropertyInvoker;
import org.hamster.core.api.util.difference.walker.Walker;
import org.hamster.core.api.util.difference.walker.defaults.DefaultWalker;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import lombok.Getter;
import lombok.Setter;

/**
 * to check and find differences from 2 collections
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class DiffChecker<K, T> {

    /**
     * the idProperty name, must have a corresponding getter method
     * 
     * <p>
     * Default value is "id"
     * </p>
     */
    @Getter
    @Setter
    private String idProperty;

    /**
     * Whether to include or exclude {{@link #getProperties()} during comparing and merging.
     * 
     * <p>
     * Default value is true and properties is empty
     * </p>
     * <p>
     * {@link #getIdProperty()} cannot be excluded
     * </p>
     */
    @Getter
    @Setter
    private boolean exclude = true;

    /**
     * Whether to include or exclude {{@link #getProperties()} during comparing and merging.
     * 
     * <p>
     * Default value is true and properties is empty
     * </p>
     * <p>
     * {@link #getIdProperty()} cannot be excluded
     * </p>
     */
    @Getter
    @Setter
    private String[] properties;

    /**
     * to get added/removed/changed list and compare properties
     * 
     * <p>
     * Default value is {@link DefaultWalker}
     * </p>
     */
    @Getter
    @Setter
    private Walker<K, T> walker;

    /**
     * to merge the target collection into source collection
     * 
     * <p>
     * Default value is {@link DefaultMerger}
     * </p>
     */
    @Getter
    @Setter
    private Merger<K, T> merger;

    /**
     * {@link #getIdProperty()} invoker
     * 
     * <p>
     * Default value is {@link DefaultIdInvoker}
     * </p>
     */
    @Getter
    @Setter
    private IdInvoker<K, T> idInvoker;

    /**
     * invoke properties
     * 
     * <p>
     * Default value is {@link DefaultPropertyInvoker}
     * </p>
     */
    @Getter
    @Setter
    private PropertyInvoker<T> propertyInvoker;

    /**
     * compare properties with order, see {@link PropertyComparator} for details
     * 
     * <p>
     * {@link NumberComparator} and {@link ObjectComparator} are the last two items
     * </p>
     */
    @Getter
    @Setter
    private List<PropertyComparator> propertyComparators;

    /**
     * Compares children properties / collections by another checker<br>
     * Produces {@link DiffVO} or {@link DiffObjectVO} depends on the type of property
     */
    @Getter
    @Setter
    private List<CheckerWrapper> childCheckerWrappers = Lists.newArrayList();

    /**
     * register checker wrappers for children property and collection
     * 
     * @param canCheckFunction
     * @param checker
     */
    public void registerChecker(Function<DiffPath, Boolean> canCheckFunction, DiffChecker<?, ?> checker, Class<?> type) {
        childCheckerWrappers.add(new CheckerWrapper(canCheckFunction, checker, type));
    }

    /**
     * Check and get the difference details between two collections.
     * 
     * @param clazz
     *            the class of these objects
     * @param sourceColl
     *            source collection
     * @param targetColl
     *            target collection
     * @return list of {@link DiffObjectVO} contains the details of difference
     * @throws DiffCheckerException
     */
    public DiffResultVO<K, T> check(Class<T> clazz, Collection<T> sourceColl, Collection<T> targetColl) {
        // ensure walker and merger are valid
        Config<K, T> config = validateConfig();

        // find all different items
        CheckerExecutor<K, T> executor = new CheckerExecutor<K, T>(config);
        ExecutorResult<K, T> result = executor.doCheck(clazz, sourceColl, targetColl);

        // map result into list of {@link DiffObjectVO}
        DiffObjectVOMapper<K, T> objectVOMapper = new DiffObjectVOMapper<K, T>(config.getIdInvoker(), config.getPropertyInvoker(),
                result.getMethods(), config.getPropertyComparators(), config.getChildCheckerWrappers());
        List<DiffObjectVO> addedDiffObjects = objectVOMapper.mapAddedColl(result.getAddedColl());
        List<DiffObjectVO> removedDiffObjects = objectVOMapper.mapRemovedColl(result.getRemovedColl());
        List<DiffObjectVO> changedDiffObjects = objectVOMapper.mapChangedColl(result.getChangedColl());

        // combine results and return
        DiffResultVO<K, T> diffResultVO = new DiffResultVO<K, T>();
        diffResultVO.setProperties(Sets.newHashSet(result.getMethods().keySet()));
        diffResultVO.getResults().put(DiffType.ADD, addedDiffObjects);
        diffResultVO.getResults().put(DiffType.REMOVAL, removedDiffObjects);
        diffResultVO.getResults().put(DiffType.CHANGE, changedDiffObjects);

        diffResultVO.setAddedColl(result.getAddedColl());
        diffResultVO.setRemovedColl(result.getRemovedColl());
        diffResultVO.setChangedColl(result.getChangedColl());

        return diffResultVO;
    }

    public void merge(Class<T> clazz, Collection<T> sourceColl, Collection<T> targetColl, DiffResultVO<K, T> diffResultVO)
            throws DiffCheckerException {
        Config<K, T> config = validateConfig();
        MergerExecutor<K, T> executor = new MergerExecutor<K, T>(config);
        executor.doMerge(clazz, sourceColl, targetColl, diffResultVO);
    }

    /**
     * Validate and create {@link Config} instance.
     * 
     * @return config
     */
    protected Config<K, T> validateConfig() {
        Config<K, T> config = new Config<K, T>();
        config.setExclude(exclude);
        config.setIdInvoker(idInvoker);
        config.setIdProperty(idProperty);
        config.setMerger(merger);
        config.setProperties(properties);
        config.setPropertyComparators(propertyComparators);
        config.setPropertyInvoker(propertyInvoker);
        config.setWalker(walker);
        config.setChildCheckerWrappers(childCheckerWrappers);

        if (StringUtils.isBlank(idProperty)) {
            config.setIdProperty("id");
        }

        if (idInvoker == null) {
            config.setIdInvoker(new DefaultIdInvoker<K, T>(config.getIdProperty()));
        }

        if (propertyInvoker == null) {
            config.setPropertyInvoker(new DefaultPropertyInvoker<T>());
        }

        if (propertyComparators == null) {
            config.setPropertyComparators(Lists.<PropertyComparator> newArrayList());
        }

        // add default ones
        config.getPropertyComparators().add(new ObjectComparator());
        config.getPropertyComparators().add(new SimpleCollectionComparator());

        if (walker == null) {
            config.setWalker(new DefaultWalker<K, T>(config.getIdInvoker(), config.getPropertyInvoker(), config.getPropertyComparators()));
        }

        if (merger == null) {
            config.setMerger(new DefaultMerger<K, T>(config.getPropertyInvoker()));
        }

        if (properties == null) {
            config.setProperties(new String[] {});
        }

        if (childCheckerWrappers == null) {
            config.setChildCheckerWrappers(Lists.<CheckerWrapper> newArrayList());
        }

        return config;
    }

    /**
     * Configuration used in {@link DiffCheckerExecutor}, it will collect {@link DiffChecker} configuration properties and set with default values if values are not set.
     * 
     * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
     * @since 1.0
     */
    @Getter
    @Setter
    public static class Config<K, T> {

        /**
         * @see DiffChecker#idProperty
         */
        private String idProperty;

        /**
         * @see DiffChecker#exclude
         */
        private boolean exclude = true;

        /**
         * @see DiffChecker#properties
         */
        private String[] properties;

        /**
         * @see DiffChecker#walker
         */
        private Walker<K, T> walker;

        /**
         * @see DiffChecker#merger
         */
        private Merger<K, T> merger;

        /**
         * @see DiffChecker#idInvoker
         */
        private IdInvoker<K, T> idInvoker;

        /**
         * @see DiffChecker#propertyInvoker
         */
        private PropertyInvoker<T> propertyInvoker;

        /**
         * @see DiffChecker#propertyComparators
         */
        private List<PropertyComparator> propertyComparators;

        /**
         * @see DiffChecker#childCheckerWrappers
         */
        private List<CheckerWrapper> childCheckerWrappers;
    }
}
