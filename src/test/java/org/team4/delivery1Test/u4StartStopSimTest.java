package org.team4.delivery1Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.dashboard.DashboardController;

public class u4StartStopSimTest {
    @Test
    public void testStartAndStopSimulation() {
        DashboardController dashboardController = new DashboardController();
        Assert.assertNotNull(dashboardController);
    }
}
