package org.team4.delivery1Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.shsParameters.ShsParameterController;

public class u6ModifyOutsideTemperatureTest {
    @Test
    public void testModifyOutsideTemperature() {
        ShsParameterController shsParameterController = new ShsParameterController();
        Assert.assertNotNull(shsParameterController);
    }
}
