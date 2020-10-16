package org.team4.userTest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.team4.common.Settings;
import org.team4.user.User;

import java.io.IOException;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(User.class)
public class ModelTest {

    User mockUser = new User("foo", "faa", 12);

    @Test
    public void test() {
        Assert.assertEquals(1,1);
    }

    @Test
    public void testGetters() {
        String name = mockUser.getName();
        String status = mockUser.getStatus();
        int age = mockUser.getAge();
        int x = mockUser.getX();
        int y = mockUser.getY();
        Assert.assertEquals(name, "foo");
        Assert.assertEquals(status, "faa");
        Assert.assertEquals(age, 12);
        Assert.assertEquals(x, 0);
        Assert.assertEquals(y, 0);
    }

    @Test
    public void testIsAdult() {
        Assert.assertFalse(mockUser.isAdult());
    }

    @Test
    public void testGetUser_notFound() throws IOException {
        ArrayList<User> mockList = new ArrayList<>();
        PowerMockito.mockStatic(User.class);
        when(User.getAllUsers()).thenReturn(mockList);
        User result = User.getUser("foo");
        Assert.assertEquals(null, result);
    }
}
