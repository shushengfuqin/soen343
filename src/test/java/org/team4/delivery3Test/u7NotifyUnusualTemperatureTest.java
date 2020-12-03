package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.services.TemperatureService;

public class u7NotifyUnusualTemperatureTest {
    @Test
    public void TestUnusualTemperatureValid() {
        TemperatureService mockService = new TemperatureService();
        boolean unusual = mockService.isCurrentTempUnusual(20);
        Assert.assertFalse(unusual);
    }

    @Test
    public void TestUnusualTemperatureUnusual() {
        TemperatureService mockService = new TemperatureService();
        boolean unusual = mockService.isCurrentTempUnusual(-50);
        Assert.assertTrue(unusual);
    }
}
