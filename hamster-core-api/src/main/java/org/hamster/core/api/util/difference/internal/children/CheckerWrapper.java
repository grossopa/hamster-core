package org.hamster.core.api.util.difference.internal.children;

import org.hamster.core.api.util.difference.DiffChecker;
import org.hamster.core.api.util.difference.model.DiffPath;

import com.google.common.base.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Records registered checker details
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
@SuppressWarnings("rawtypes")
public class CheckerWrapper {
    private Function<DiffPath, Boolean> canCheckFunction;
    private DiffChecker checker;
    private Class type;
}