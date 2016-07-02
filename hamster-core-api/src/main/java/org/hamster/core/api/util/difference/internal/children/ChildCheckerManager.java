/**
 * 
 */
package org.hamster.core.api.util.difference.internal.children;

import java.util.List;

import org.hamster.core.api.util.difference.DiffChecker;
import org.hamster.core.api.util.difference.model.DiffPath;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Manages children checker
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class ChildCheckerManager {

    private final List<CheckerWrapper> wrappers = Lists.newArrayList();

    /**
     * Registers checker
     * 
     * @param canCheckFunction
     * @param checker
     */
    public void registerChecker(Function<DiffPath, Boolean> canCheckFunction, DiffChecker<?, ?> checker) {
        wrappers.add(new CheckerWrapper(canCheckFunction, checker));
    }
    
    /**
     * finds available checkers by calling canCheckFunction {@link Function#apply(DiffPath)}
     * 
     * @param path
     * @return the checker or null if none found
     */
    public DiffChecker<?, ?> findChecker(DiffPath path) {
        for (CheckerWrapper wrapper : wrappers) {
            if (wrapper.getCanCheckFunction().apply(path)) {
                return wrapper.getChecker();
            }
        }
        return null;
    }

}

/**
 * Records registered checker details
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Getter
@Setter
@AllArgsConstructor
class CheckerWrapper {
    private Function<DiffPath, Boolean> canCheckFunction;
    private DiffChecker<?, ?> checker;
}