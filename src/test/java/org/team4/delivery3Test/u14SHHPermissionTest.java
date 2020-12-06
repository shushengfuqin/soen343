package org.team4.delivery3Test;

import org.junit.Test;
import org.team4.permissions.Permission;
import org.team4.user.User;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

public class u14SHHPermissionTest {

    private static final User bob = new User("bob", "family", 30, 1, 1);
    private static final User billy = new User("billy", "family", 5, 1, 2);
    private static final User guest = new User("guest", "guest", 20, 1, 1);
    private static final User intruder = new User("intruder", "intruder", 20, 0, 0);

    @Test
    public void testValidUserSHHPermission() {
        assertTrue(Permission.checkSHHPermission(bob));
    }

    @Test
    public void testInvalidUserSHHPermission() {
        assertFalse(Permission.checkSHHPermission(intruder));
        assertFalse(Permission.checkSHHPermission(billy));
        assertFalse(Permission.checkSHHPermission(guest));
    }

    @Test
    public void testValidUserChangeTempPermission() {
        assertTrue(Permission.checkChangeTempPermission(bob,1,1));
        assertTrue(Permission.checkChangeTempPermission(guest,1,1));
    }

    @Test
    public void testInvalidUserChangeTempPermission() {
        assertFalse(Permission.checkChangeTempPermission(intruder,0,0));
        assertFalse(Permission.checkChangeTempPermission(billy,1,2));
        assertFalse(Permission.checkChangeTempPermission(guest,2,2));
    }

}
