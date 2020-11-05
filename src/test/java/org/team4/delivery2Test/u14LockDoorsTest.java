package org.team4.delivery2Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.shcParameters.ShcParameterController;

public class u14LockDoorsTest {
    @Test
    public void testLockDoors() {
        ShcParameterController shcParameterController = new ShcParameterController();
        Assert.assertNotNull(shcParameterController);
    }
}
