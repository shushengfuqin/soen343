package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Settings;
import org.team4.house.services.TemperatureService;

import java.util.Date;

public class u15TurnOffAirConditioningInSummerTest {
    @Test
    public void TestACTurnOffInSummer() {
        TemperatureService temperatureService = new TemperatureService();
        Settings.outsideTemperature = 5;
        Settings.summerBegin = 1;
        Settings.summerEnd = 12;
        boolean res = temperatureService.openWindowOrNot(new Date());
        Assert.assertFalse(res);
    }
}
