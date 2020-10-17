package org.team4.shsParamTest;

import org.junit.Assert;
import org.junit.Test;
import static org.mockito.Mockito.*;
import org.team4.shsParameters.ShsParameterService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class timeTest {
//
    @Test
    public void testSetDateTime_fails() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = "1:1:";
        Date mockDate = new Date();
        mockDate.setDate(21);
        mockDate.setMonth(11);
        mockDate.setYear(1955);
        when(mockService.setDate(mockDate, 1, 1, 1)).thenReturn(true);
        LocalDate date = mockDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean result = mockService.setDateTime(date, mockTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testSetDateTime_fails_out_of_bound() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = "25:1:1";
        Date mockDate = new Date();
        mockDate.setDate(21);
        mockDate.setMonth(11);
        mockDate.setYear(1955);
        when(mockService.setDate(mockDate, 1, 1, 1)).thenReturn(true);
        LocalDate date = mockDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean result = mockService.setDateTime(date, mockTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testSetDateTime_fails_missing_field_time() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = null;
        Date mockDate = new Date();
        mockDate.setDate(21);
        mockDate.setMonth(11);
        mockDate.setYear(1955);
        when(mockService.setDate(mockDate, 1, 1, 1)).thenReturn(true);
        LocalDate date = mockDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        boolean result = mockService.setDateTime(date, mockTime);
        Assert.assertFalse(result);
    }

    @Test
    public void testSetDateTime_fails_missing_field_date() {
        ShsParameterService mockService = mock(ShsParameterService.class);
        String mockTime = "1:1:1";
        Date mockDate = new Date();
        mockDate.setDate(21);
        mockDate.setMonth(11);
        mockDate.setYear(1955);
        when(mockService.setDate(mockDate, 1, 1, 1)).thenReturn(true);
        boolean result = mockService.setDateTime(null, mockTime);
        Assert.assertFalse(result);
    }
}
