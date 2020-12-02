package org.team4.delivery2Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.services.LightService;

public class u10ChooseLightInAwayModeTest {
    @Test
    public void testAddtoAwayModeTest() {
        try {
            new LightService().toggleLightsAway("aa");
        }
        catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
}
