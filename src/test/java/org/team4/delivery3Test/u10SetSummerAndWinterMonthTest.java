package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Settings;
import org.team4.house.services.TemperatureService;

import java.util.Date;

public class u10SetSummerAndWinterMonthTest {
    @Test
    public void TestSetSummerAndWinterMonth() {
        Settings.summerBegin = 10;
        Settings.summerEnd = 12;
        TemperatureService temperatureService = new TemperatureService();
        boolean res = temperatureService.seasonIsSummer(new Date());
        Assert.assertTrue(res);
    }
}
