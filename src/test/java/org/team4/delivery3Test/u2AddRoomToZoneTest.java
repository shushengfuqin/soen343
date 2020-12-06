package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.HouseService;
import org.team4.house.components.Room;
import org.team4.shhParameters.ZoneService;

public class u2AddRoomToZoneTest {
    @Test
    public void TestAddRoomToZone() {
        HouseService houseService = new HouseService();
        houseService.getHouseLayout();
        ZoneService zoneService = new ZoneService();
        zoneService.addNewZone("foo420", 42.0);
        zoneService.setRoomZone("foo420", 1, 1);
        Room[][] room = houseService.getRooms();
        Assert.assertEquals(room[1][1].zone, "foo420");
    }
}
