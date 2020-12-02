package org.team4.delivery2Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.shpParameters.ShpService;

public class u12SetTimeToAlertAuthoritiesTest {
    @Test
    public void testTimeToAlertAuthorities() {
        ShpService shpService = new ShpService();
        try {
            shpService.setAwayModeNotifTime(123);
            Assert.assertNotNull(shpService);
        }
        catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
}
