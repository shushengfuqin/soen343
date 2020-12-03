package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Settings;
import org.team4.shhParameters.Zone;
import org.team4.shhParameters.ZoneService;

public class u1CreateNewZoneTest {
    @Test
    public void TestCreateNewZone() {
        Settings.logging = false;
        ZoneService mockService = new ZoneService();
        mockService.addNewZone("foo", 13.0);
        Zone zone = mockService.getZone("foo");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
    }
}
