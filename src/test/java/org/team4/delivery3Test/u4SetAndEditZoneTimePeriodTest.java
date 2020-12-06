package org.team4.delivery3Test;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Settings;
import org.team4.shhParameters.Zone;
import org.team4.shhParameters.ZoneService;

import java.util.Date;

public class u4SetAndEditZoneTimePeriodTest {
    @Test
    public void TestCreateTimePeriod1() {
        Settings.logging = false;
        Date date = new Date();
        ZoneService mockService = new ZoneService();
        mockService.addNewZone("foo1", 13.0);
        mockService.setTimePeriod1("foo1", date, date,20.0);
        Zone zone = mockService.getZone("foo1");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo1");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod1.begin, date);
        Assert.assertEquals(zone.timePeriod1.end, date);
        Assert.assertEquals(zone.timePeriod1.desiredTemperature, 20.0, 0.01);
    }

    @Test
    public void TestCreateTimePeriod2() {
        Settings.logging = false;
        Date date = new Date();
        ZoneService mockService = new ZoneService();
        mockService.addNewZone("foo2", 13.0);
        mockService.setTimePeriod2("foo2", date, date,20.0);
        Zone zone = mockService.getZone("foo2");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo2");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod2.begin, date);
        Assert.assertEquals(zone.timePeriod2.end, date);
        Assert.assertEquals(zone.timePeriod2.desiredTemperature, 20.0, 0.01);
    }

    @Test
    public void TestCreateTimePeriod3() {
        Settings.logging = false;
        Date date = new Date();
        ZoneService mockService = new ZoneService();
        mockService.addNewZone("foo3", 13.0);
        mockService.setTimePeriod3("foo3", date, date,20.0);
        Zone zone = mockService.getZone("foo3");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo3");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod3.begin, date);
        Assert.assertEquals(zone.timePeriod3.end, date);
        Assert.assertEquals(zone.timePeriod3.desiredTemperature, 20.0, 0.01);
    }

    @Test
    public void TestEditTimePeriod1() {
        Settings.logging = false;
        Date date = new Date();
        ZoneService mockService = new ZoneService();
        mockService.addNewZone("foo4", 13.0);
        mockService.setTimePeriod1("foo4", date, date,20.0);
        Zone zone = mockService.getZone("foo4");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo4");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod1.begin, date);
        Assert.assertEquals(zone.timePeriod1.end, date);
        Assert.assertEquals(zone.timePeriod1.desiredTemperature, 20.0, 0.01);

        date = new Date();
        mockService.setTimePeriod1("foo4", date, date,225.0);
        zone = mockService.getZone("foo4");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo4");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod1.begin, date);
        Assert.assertEquals(zone.timePeriod1.end, date);
        Assert.assertEquals(zone.timePeriod1.desiredTemperature, 225.0, 0.01);
    }

    @Test
    public void TestEditTimePeriod2() {
        Settings.logging = false;
        Date date = new Date();
        ZoneService mockService = new ZoneService();
        mockService.addNewZone("foo5", 13.0);
        mockService.setTimePeriod2("foo5", date, date,20.0);
        Zone zone = mockService.getZone("foo5");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo5");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod2.begin, date);
        Assert.assertEquals(zone.timePeriod2.end, date);
        Assert.assertEquals(zone.timePeriod2.desiredTemperature, 20.0, 0.01);

        date = new Date();
        mockService.setTimePeriod2("foo5", date, date,225.0);
        zone = mockService.getZone("foo5");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo5");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod2.begin, date);
        Assert.assertEquals(zone.timePeriod2.end, date);
        Assert.assertEquals(zone.timePeriod2.desiredTemperature, 225.0, 0.01);
    }

    @Test
    public void TestEditTimePeriod3() {
        Settings.logging = false;
        Date date = new Date();
        ZoneService mockService = new ZoneService();
        mockService.addNewZone("foo6", 13.0);
        mockService.setTimePeriod3("foo6", date, date,20.0);
        Zone zone = mockService.getZone("foo6");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo6");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod3.begin, date);
        Assert.assertEquals(zone.timePeriod3.end, date);
        Assert.assertEquals(zone.timePeriod3.desiredTemperature, 20.0, 0.01);

        date = new Date();
        mockService.setTimePeriod3("foo6", date, date,225.0);
        zone = mockService.getZone("foo6");
        Assert.assertNotNull(zone);
        Assert.assertEquals(zone.name, "foo6");
        Assert.assertEquals(zone.defaultTemp, 13.0, 0.01);
        Assert.assertEquals(zone.timePeriod3.begin, date);
        Assert.assertEquals(zone.timePeriod3.end, date);
        Assert.assertEquals(zone.timePeriod3.desiredTemperature, 225.0, 0.01);
    }
}
