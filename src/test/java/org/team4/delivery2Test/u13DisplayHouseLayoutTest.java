package org.team4.delivery2Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.dashboard.DashboardController;

public class u13DisplayHouseLayoutTest {
    @Test
    public void testDisplayHouseLayout() {
        DashboardController dashboardController = new DashboardController();
        Assert.assertNotNull(dashboardController);
    }
}
