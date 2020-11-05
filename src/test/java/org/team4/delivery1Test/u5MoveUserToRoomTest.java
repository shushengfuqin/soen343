package org.team4.delivery1Test;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;
import org.team4.user.User;
import org.team4.user.UserService;

import java.io.IOException;

import static org.mockito.Mockito.when;

public class u5MoveUserToRoomTest {
    public User mockUser = new User(
            "test",
            "testFamily",
            12
    );

    @Ignore
    @Test
    public void addUserTest_good() throws IOException {
        UserService serviceMock = new UserService();
        PowerMockito.mockStatic(User.class);
        when(User.addNewUsers(mockUser)).thenReturn(true);
        boolean result = serviceMock.addUser("foo", "foo", 12);
        Assert.assertTrue(result);
    }
}
