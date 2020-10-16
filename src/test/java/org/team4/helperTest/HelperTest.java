package org.team4.helperTest;

import org.junit.Assert;
import org.junit.Test;
import org.team4.common.Helper;

public class HelperTest {
    @Test
    public void isAlphanumericTest_ShouldReturnTrue() {
        String value = "hello";
        Boolean result = Helper.isAlphanumeric(value);
        Assert.assertTrue(result);
    }

    @Test
    public void isAlphanumericTest_ShouldReturnFalse() {
        String value = "hell $o";
        Boolean result = Helper.isAlphanumeric(value);
        Assert.assertFalse(result);
    }
}
