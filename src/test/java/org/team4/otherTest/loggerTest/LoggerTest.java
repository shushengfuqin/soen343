package org.team4.otherTest.loggerTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.team4.common.logger.Log;
import org.team4.common.logger.Logger;


import java.util.Date;

import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Logger.class, Log.class})
public class LoggerTest {

    /**
     * Test if the log info is callable
     * @throws Exception
     */
    @Test
    public void logInfoShouldReturnTrue() throws Exception {
        Date date = new Date();
        Log mockLog = new Log("hello", "foo", date, "foo");
        PowerMockito.mockStatic(Log.class);
        PowerMockito.whenNew(Log.class).withArguments(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()).thenReturn(mockLog);

        PowerMockito.mockStatic(Logger.class);
        when(Logger.log("hello", "foo", date, "foo")).thenReturn(true);

        PowerMockito.mockStatic(Logger.class);
        when(Logger.info("hello")).thenReturn(true);
        boolean res = Logger.info("hello");
        Assert.assertTrue(res);
    }

    /**
     * Test if the log warning is callable
     * @throws Exception
     */
    @Test
    public void logWarningShouldReturnTrue() throws Exception {
        Date date = new Date();
        Log mockLog = new Log("hello", "foo", date, "foo");
        PowerMockito.mockStatic(Log.class);
        PowerMockito.whenNew(Log.class).withArguments(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()).thenReturn(mockLog);

        PowerMockito.mockStatic(Logger.class);
        when(Logger.log("hello", "foo", date, "foo")).thenReturn(true);

        PowerMockito.mockStatic(Logger.class);
        when(Logger.warning("hello")).thenReturn(true);
        boolean res = Logger.warning("hello");
        Assert.assertTrue(res);
    }

    /**
     * Test if the log error is callable
     * @throws Exception
     */
    @Test
    public void logErrorShouldReturnTrue() throws Exception {
        Date date = new Date();
        Log mockLog = new Log("hello", "foo", date, "foo");
        PowerMockito.mockStatic(Log.class);
        PowerMockito.whenNew(Log.class).withArguments(Mockito.any(),Mockito.any(),Mockito.any(),Mockito.any()).thenReturn(mockLog);

        PowerMockito.mockStatic(Logger.class);
        when(Logger.log("hello", "foo", date, "foo")).thenReturn(true);

        PowerMockito.mockStatic(Logger.class);
        when(Logger.error("hello")).thenReturn(true);
        boolean res = Logger.error("hello");
        Assert.assertTrue(res);
    }
}
