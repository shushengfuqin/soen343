package org.team4.delivery1Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.shsParameters.ShsParameterController;

public class u7BlockWindowTest {
    @Test
    public void testBlockWindow() {
        ShsParameterController shsParameterController = new ShsParameterController();
        Assert.assertNotNull(shsParameterController);
    }
}
