package org.hamster.core.api.util.difference.internal.model;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import lombok.Getter;
import lombok.Setter;

/**
 * Execution result
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
public class ExecutorResult<K, T> {

    private Map<String, Method> methods;

    private Collection<T> addedColl;

    private Map<K, T> removedColl;

    private Map<K, Pair<T, T>> changedColl;
}