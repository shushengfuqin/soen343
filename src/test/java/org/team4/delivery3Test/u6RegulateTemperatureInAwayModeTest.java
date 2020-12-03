package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Coordinate;
import org.team4.common.Settings;
import org.team4.house.HouseService;
import org.team4.house.services.TemperatureService;
import org.team4.shhParameters.ZoneService;

public class u6RegulateTemperatureInAwayModeTest {

    @Test
    public void TestRegulateTemperatureInAwayModeSummer() {
        Settings.logging = false;
        Settings.defaultTemp = 10.0;
        Settings.awayMode = true;
        Settings.summerBegin = 11;
        Settings.summerEnd = 12;
        HouseService houseService = new HouseService();
        houseService.getHouseLayout();

        ZoneService zoneService = new ZoneService();
        double roomTempBefore = zoneService.requestRoomTemperature(new Coordinate(1, 1));

        TemperatureService temperatureService = new TemperatureService();
        temperatureService.updateTemperature();

        double roomTempAfter = zoneService.requestRoomTemperature(new Coordinate(1, 1));
        Assert.assertTrue(roomTempBefore < roomTempAfter);
    }

}
