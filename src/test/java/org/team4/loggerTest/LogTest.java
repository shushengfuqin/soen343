package org.team4.loggerTest;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.team4.common.logger.Log;

import java.util.Date;

public class LogTest {
    @Test
    public void toJsonTestShouldReturnJSONObject() {
        Log mockLog = new Log("fuu", "faa", new Date(), "foo");
        JSONObject jo = mockLog.toJson();
        Assert.assertEquals(jo.get("user"), "foo");
        Assert.assertEquals(jo.get("message"), "fuu");
        Assert.assertEquals(jo.get("level"), "faa");
    }
}
