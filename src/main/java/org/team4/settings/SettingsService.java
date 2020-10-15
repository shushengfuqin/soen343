package org.team4.settings;

import org.team4.user.User;
import org.team4.user.UserService;

import java.io.IOException;

public class SettingsService {

    public static void setCurrentUser(User selectedUser) throws IOException {
        Settings.currentUser = selectedUser;
    }

    public static boolean isCurrentUserSet() {
        if(Settings.currentUser == null) return false;
        return true;
    }

    public static User getCurrentUser() {
        return Settings.currentUser;
    }
}
