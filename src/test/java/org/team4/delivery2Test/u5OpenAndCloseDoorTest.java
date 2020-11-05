package org.team4.delivery2Test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.team4.common.logger.Logger;
import org.team4.house.House;
import org.team4.user.User;

import static org.mockito.ArgumentMatchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(House.class)
public class u5OpenAndCloseDoorTest {
    @Test
    public void testOpenDoor() {
        PowerMockito.mockStatic(House.class);
        BDDMockito.given(House.toggleDoor("hey", false)).willReturn(true);
        boolean res = House.toggleDoor("hey", false);
        Assert.assertTrue(res);
    }

    @Test
    public void testOpenDoorFail() {
        PowerMockito.mockStatic(House.class);
        BDDMockito.given(House.toggleDoor("hey", false)).willReturn(false);
        boolean res = House.toggleDoor("hey", false);
        Assert.assertFalse(res);
    }
}
