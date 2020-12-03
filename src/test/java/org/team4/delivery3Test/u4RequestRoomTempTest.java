package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Coordinate;
import org.team4.common.Settings;
import org.team4.house.HouseService;
import org.team4.shhParameters.ZoneService;

public class u4RequestRoomTempTest {
    @Test
    public void TestRequestRoomTemperature() {
        Settings.logging = false;
        Settings.defaultTemp = 20.0;
        HouseService houseService = new HouseService();
        houseService.getHouseLayout();

        ZoneService zoneService = new ZoneService();
        double roomTemp = zoneService.requestRoomTemperature(new Coordinate(0, 0));
        Assert.assertEquals(roomTemp, 20.0, 0.1);
    }
}
