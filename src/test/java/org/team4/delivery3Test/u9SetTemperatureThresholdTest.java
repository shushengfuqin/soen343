package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Settings;
import org.team4.house.services.TemperatureService;
import org.team4.shhParameters.ZoneService;

public class u9SetTemperatureThresholdTest {
    @Test
    public void TestSetNewTemperatureThresholds() {
        Settings.logging = false;
        ZoneService zoneService = new ZoneService();
        TemperatureService mockService = new TemperatureService();
        boolean unusual = mockService.isCurrentTempUnusual(-50);
        Assert.assertTrue(unusual);
        zoneService.setTemperatureAlertThreshold(100, -100);
        unusual = mockService.isCurrentTempUnusual(-50);
        Assert.assertFalse(unusual);
    }

    @Test
    public void TestSetNewTemperatureThresholdsUnusual() {
        Settings.logging = false;
        ZoneService zoneService = new ZoneService();
        TemperatureService mockService = new TemperatureService();
        boolean unusual = mockService.isCurrentTempUnusual(20);
        Assert.assertFalse(unusual);
        zoneService.setTemperatureAlertThreshold(10, -100);
        unusual = mockService.isCurrentTempUnusual(20);
        Assert.assertTrue(unusual);
    }
}
