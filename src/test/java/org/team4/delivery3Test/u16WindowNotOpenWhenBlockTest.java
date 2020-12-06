package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.HouseService;
import org.team4.house.services.WindowService;

public class u16WindowNotOpenWhenBlockTest {
    @Test
    public void TestWindowNotOpenWhenBlock() {
        HouseService houseService = new HouseService();
        houseService.getHouseLayout();
        WindowService windowService = new WindowService();
        boolean res = windowService.checkWindowBlock(1, 1);
        Assert.assertFalse(res);
    }
}
