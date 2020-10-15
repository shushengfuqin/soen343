package org.team4.user;

import org.team4.settings.SettingsService;

import java.io.IOException;
import java.util.ArrayList;

public class UserService {

    public UserService() throws IOException {
        if(!SettingsService.isCurrentUserSet()) {
            ArrayList<User> allUsers = User.getAllUsers();
            if(!allUsers.isEmpty()) SettingsService.setCurrentUser(allUsers.get(0));
        }
    }

    public String validateUserInput(String name, String age) throws IOException {
        String failReason = "";
        if( !this.isAlphanumeric(name) || name.isEmpty())
            failReason += "Name should only contain letters\n";
        int intAge = 0;

        try {
            intAge = Integer.parseInt(age);
            if(intAge < 0) failReason += "Age should be a positive integer\n";
        }
        catch (NumberFormatException  e) {
            failReason += "Age should be an integer\n";
        }
        return failReason;
    }

    public String createNewUser(String name, String status, String age) throws IOException {
        String failReason = validateUserInput(name, age);
        if(User.userExist(name)) failReason += "User already exist\n";
        if( !failReason.equals("") ) return failReason;

        int intAge = Integer.parseInt(age);

        User newUser = new User(name, status, intAge);
        User.addNewUsers(newUser);

        if( !SettingsService.isCurrentUserSet() ) SettingsService.setCurrentUser(newUser);

        return null;
    }

    public boolean isAlphanumeric(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    public ArrayList<String> getAllUsersName() throws IOException {
        ArrayList<User> allUsers = User.getAllUsers();
        ArrayList<String> allUserName = new ArrayList<>();
        User currentUser = SettingsService.getCurrentUser();

        for(User u : allUsers) {
            allUserName.add(u.getName());
        }

        if(!allUserName.isEmpty() && currentUser != null) {
            String curName = currentUser.getName();
            int curIndex = allUserName.indexOf(curName);
            String temp = allUserName.get(0);
            allUserName.set(0, curName);
            allUserName.set(curIndex, temp);
        }
        return allUserName;
    }

    public User getUser(String name) throws IOException {
        return User.getUser(name);
    }

    public String editUser(String name, String status, String age) throws IOException {
        String failReason = validateUserInput(name, age);
        if(!failReason.equals("")) return failReason;

        if(!User.userExist(name)) {
            return "User does not exist";
        }

        int intAge = Integer.parseInt(age);
        User user = User.getUser(name);
        User.modifyUser(name, status, intAge, user.getCoord().x, user.getCoord().y);
        if(user.getName().equals(SettingsService.getCurrentUser().getName())) {
            user = User.getUser(name);
            SettingsService.setCurrentUser(user);
        }

        return null;
    }

    public String deleteUser(String name) throws  IOException {
        ArrayList<User> allUsers = User.getAllUsers();
        if(allUsers.size() <= 1){
            return "Unable to delete the last user";
        }
        User.deleteUser(name);
        if(!SettingsService.isCurrentUserSet() || SettingsService.getCurrentUser().getName().equals(name)) {
            allUsers = User.getAllUsers();
            SettingsService.setCurrentUser(allUsers.get(0));
        }
        return null;
    }

}
