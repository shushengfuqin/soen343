package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Coordinate;
import org.team4.common.Settings;
import org.team4.house.HouseService;
import org.team4.house.services.TemperatureService;
import org.team4.shhParameters.ZoneService;

public class u11OverrideRoomTemperatureTest {
    @Test
    public void TestOverrideRoomTemperature() {
        Settings.logging = false;
        Settings.defaultTemp = 20.0;
        Settings.summerBegin = 6;
        Settings.summerEnd = 5;
        HouseService houseService = new HouseService();
        houseService.getHouseLayout();

        ZoneService zoneService = new ZoneService();
        double roomTempBefore = zoneService.requestRoomTemperature(new Coordinate(1, 1));

        Settings.outsideTemperature = 20;
        zoneService.overrideTemperature(new Coordinate(1,1), 10);

        TemperatureService temperatureService = new TemperatureService();
        temperatureService.updateTemperature();

        double roomTempAfter = zoneService.requestRoomTemperature(new Coordinate(1, 1));
        Assert.assertTrue(roomTempBefore > roomTempAfter);

        double roomTemp2 = zoneService.requestRoomTemperature(new Coordinate(1, 2));
        Assert.assertEquals(roomTempBefore, roomTemp2, 0.01);
    }
}
