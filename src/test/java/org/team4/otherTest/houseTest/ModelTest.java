package org.team4.otherTest.houseTest;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.team4.house.House;
import org.team4.house.components.Room;

@RunWith(PowerMockRunner.class)
@PrepareForTest(House.class)
public class ModelTest {

    @Test
    public void getRoomLocationTest() {
        int[] expectedResult = {1,2,0};
        int[] result = House.getRoomLocation("(1, 2) - left");
        Assert.assertEquals(result[0], expectedResult[0]);
        Assert.assertEquals(result[1], expectedResult[1]);
        Assert.assertEquals(result[2], expectedResult[2]);
    }

    @Test
    public void wallSideMapperTest_withIndex() {
        String res = Room.wallSideMapper(0);
        String res1 = Room.wallSideMapper(1);
        String res2 = Room.wallSideMapper(2);
        String res3 = Room.wallSideMapper(3);
        Assert.assertEquals(res, "left");
        Assert.assertEquals(res1, "top");
        Assert.assertEquals(res2, "right");
        Assert.assertEquals(res3, "bot");
    }

    @Test
    public void wallSideMapperTest_withString() {
        int res = Room.wallSideMapper("left");
        int res1 = Room.wallSideMapper("top");
        int res2 = Room.wallSideMapper("right");
        int res3 = Room.wallSideMapper("bot");
        Assert.assertEquals(res, 0);
        Assert.assertEquals(res1, 1);
        Assert.assertEquals(res2, 2);
        Assert.assertEquals(res3, 3);
    }

    @Test
    public void generateHouseTest() {
        House.generateHouse();
        Assert.assertNotNull(House.rooms[0][0]);
    }
}
