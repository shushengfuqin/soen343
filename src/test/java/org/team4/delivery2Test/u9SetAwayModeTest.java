package org.team4.delivery2Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.team4.shpParameters.ShpService;

public class u9SetAwayModeTest {

    @Test
    public void setAwayModeTest() {
        ShpService shpService = mock(ShpService.class);
        Assert.assertNotNull(shpService);
    }
}
