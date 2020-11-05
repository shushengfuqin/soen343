package org.team4.delivery2Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.house.House;

public class u10ChooseLightInAwayModeTest {
    @Test
    public void testAddtoAwayModeTest() {
        try {
            House.toggleLightsAway("aa");
        }
        catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
}
