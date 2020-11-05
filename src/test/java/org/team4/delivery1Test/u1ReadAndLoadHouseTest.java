package org.team4.delivery1Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.House;
import org.team4.house.components.Room;

public class u1ReadAndLoadHouseTest {
    @Test
    public void testGetHouseFromFile() {
        Room[][] rooms = House.getHouseLayout();
        Assert.assertNotNull(rooms);
    }
}
