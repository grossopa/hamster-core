/**
 * 
 */
package org.hamster.core.api.test.utils.excel2sql;

import java.io.File;
import java.io.IOException;

import org.hamster.core.api.util.excel2sql.Excel2SQLGenerator;
import org.junit.Test;

/**
 * Functional test for generating sql from excel
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class Excel2SQLGeneratorTest {
    
    @Test
    public void test() throws IllegalAccessException, InstantiationException, IOException {
        Excel2SQLGenerator generator = new Excel2SQLGenerator();
        generator.setSingleFile(false);
        generator.generate(new File("src/test/java/org/hamster/core/api/test/utils/excel2sql/test-sql.xlsx"), new File("src/test/java/org/hamster/core/api/test/utils/excel2sql/results"));
    }
}
