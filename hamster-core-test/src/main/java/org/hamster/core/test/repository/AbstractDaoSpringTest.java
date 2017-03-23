/**
 * 
 */
package org.hamster.core.test.repository;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * abstract spring test class with proper initial data abstract for sub classes to call,
 * sub classes should be annotated with {@link SpringBootTest}(configuration = SomeDaoConfiguration.class).
 * 
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public abstract class AbstractDaoSpringTest {

    @Autowired
    protected TestEntityManager entityManager;

    private boolean initialized;

    @Before
    public void before() {
        if (!initialized) {
            initCommonData(entityManager);
            initialized = true;
        }
    }

    protected abstract void initCommonData(TestEntityManager entityManager);


}
