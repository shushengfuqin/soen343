package org.team4.delivery2Test;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.permissions.Permission;
import org.team4.user.User;
import org.team4.user.UserService;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Logger.class)
public class u3GrantAndDenyPermissionTest {
    @Test
    public void testWindowPermissions() {
        PowerMockito.mockStatic(Logger.class);
        BDDMockito.given(Logger.warning(anyString())).willReturn(true);
        UserService userService = mock(UserService.class);
        when(userService.getSingleUser(anyString())).thenReturn(new User("bob", "family", 18, 1, 1));

        Settings.currentUser = "bob";
        boolean valid = Permission.checkUserWindowPermission(1, 1);
        Assert.assertFalse(valid);
    }

    @Test
    public void testWindowPermissionsFail() {
        PowerMockito.mockStatic(Logger.class);
        BDDMockito.given(Logger.warning(anyString())).willReturn(true);
        UserService userService = mock(UserService.class);
        PowerMockito.mockStatic(Logger.class);
        BDDMockito.given(Logger.warning(anyString())).willReturn(true);
        when(userService.getSingleUser(anyString())).thenReturn(new User("bob", "stranger", 18, 0, 1));

        Settings.currentUser = "bob";
        boolean valid = Permission.checkUserWindowPermission(0, 1);
        Assert.assertTrue(valid);
    }

    @Test
    public void testDoorPermissions() {
        PowerMockito.mockStatic(Logger.class);
        BDDMockito.given(Logger.warning(anyString())).willReturn(true);
        UserService userService = mock(UserService.class);
        when(userService.getSingleUser(anyString())).thenReturn(new User("bob", "family", 18, 1, 1));

        Settings.currentUser = "bob";
        boolean valid = Permission.checkUserDoorPermission(1, 1);
        Assert.assertFalse(valid);
    }

    @Test
    public void testDoorPermissionsFail() {
        UserService userService = mock(UserService.class);
        PowerMockito.mockStatic(Logger.class);
        BDDMockito.given(Logger.warning(anyString())).willReturn(true);
        when(userService.getSingleUser(anyString())).thenReturn(new User("bob", "stranger", 18, 0, 1));

        Settings.currentUser = "bob";
        boolean valid = Permission.checkUserDoorPermission(0, 1);
        Assert.assertTrue(valid);
    }


    @Test
    public void testLightPermissions() {
        PowerMockito.mockStatic(Logger.class);
        BDDMockito.given(Logger.warning(anyString())).willReturn(true);
        UserService userService = mock(UserService.class);
        when(userService.getSingleUser(anyString())).thenReturn(new User("bob", "family", 18, 1, 1));

        Settings.currentUser = "bob";
        boolean valid = Permission.checkUserLightPermission(1, 1);
        Assert.assertFalse(valid);
    }

    @Test
    public void testLightPermissionsFail() {
        UserService userService = mock(UserService.class);
        PowerMockito.mockStatic(Logger.class);
        BDDMockito.given(Logger.warning(anyString())).willReturn(true);
        when(userService.getSingleUser(anyString())).thenReturn(new User("bob", "stranger", 18, 0, 1));

        Settings.currentUser = "bob";
        boolean valid = Permission.checkUserLightPermission(0, 1);
        Assert.assertTrue(valid);
    }
}
