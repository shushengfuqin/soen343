package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.HouseService;
import org.team4.house.components.House;
import org.team4.house.services.DoorService;
import org.team4.house.services.WindowService;

public class u17WindowBlockedNotificationTest {
    @Test
    public void TestWindowBlockNotification() {
        HouseService houseService = new HouseService();
        houseService.getHouseLayout();
        WindowService windowService = new WindowService();
        boolean res = windowService.checkWindowBlock(1, 1);
        Assert.assertFalse(res);
    }
}
