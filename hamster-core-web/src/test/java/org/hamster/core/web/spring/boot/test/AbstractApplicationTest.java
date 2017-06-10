package org.hamster.core.web.spring.boot.test;

import org.hamster.core.test.helper.Asserts;
import org.hamster.core.web.spring.boot.AbstractApplication;
import org.junit.Test;

public class AbstractApplicationTest {

    @Test
    public void testCreateApplication() throws Exception {
        Asserts.assertNotNull(AbstractApplication.create(null));
    }
}