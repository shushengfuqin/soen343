package org.team4.delivery1Test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.team4.shsParameters.ShsParameterService;

import java.time.LocalDate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class u3ModifyDateTimeTest {

    @Test
    public void testSetDateTime_fails() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = "1:1:";
        LocalDate mockDate = LocalDate.of(1,1,1);
        when(mockService.setDateTime(mockDate, "1:1:1")).thenReturn(true);
        boolean result = mockService.setDateTime(mockDate, mockTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testSetDateTime_fails_out_of_bound() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = "25:1:1";
        LocalDate mockDate = LocalDate.of(1,1,1);
        when(mockService.setDateTime(mockDate, "1:1:1")).thenReturn(true);
        boolean result = mockService.setDateTime(mockDate, mockTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testSetDateTime_fails_missing_field_time() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = null;
        LocalDate mockDate = LocalDate.of(1,1,1);
        when(mockService.setDateTime(mockDate, "1:1:1")).thenReturn(true);
        boolean result = mockService.setDateTime(mockDate, mockTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testSetDateTime_fails_missing_field_date() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = "1:1:1";
        LocalDate mockDate = LocalDate.of(1,1,1);
        when(mockService.setDateTime(mockDate, "1:1:1")).thenReturn(true);
        boolean result = mockService.setDateTime(null, mockTime);
        Assert.assertFalse(result);
    }
}
