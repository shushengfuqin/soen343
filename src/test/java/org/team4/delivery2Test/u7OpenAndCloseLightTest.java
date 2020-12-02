package org.team4.delivery2Test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.team4.house.services.WindowService;

public class u7OpenAndCloseLightTest {
    @Test
    public void testOpenDoor() {
        try {
            boolean res = new WindowService().toggleWindowOpen("(1, 2) - left", false);
            Assert.assertTrue(res);
        }
        catch (Exception e) {
            Assert.assertTrue(true);
        }
    }

    @Test
    public void testOpenDoorFail() {
        try{
            boolean res = new WindowService().toggleWindowOpen("(1, 2) - left", false);
            Assert.assertFalse(res);
        }
        catch (Exception e) {
            Assert.assertTrue(true);
        }
    }
}
