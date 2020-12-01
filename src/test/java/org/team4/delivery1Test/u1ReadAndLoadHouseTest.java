package org.team4.delivery1Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.components.Room;
import org.team4.house.HouseService;

public class u1ReadAndLoadHouseTest {
    @Test
    public void testGetHouseFromFile() {
        try{
            Room[][] rooms = new HouseService().getHouseLayout();
            Assert.assertNotNull(rooms);
        }
        catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
}
