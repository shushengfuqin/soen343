package org.team4.user;

import org.team4.common.Helper;
import org.team4.common.Settings;
import org.team4.house.House;

import java.io.IOException;
import java.util.ArrayList;

public class UserService {

    public UserService() {
    }

    public ArrayList<User> getAllUsersList() {
        try {
            return User.getAllUsers();
        } catch (IOException e) {
            System.out.println("Failure while retrieving all users");
            e.printStackTrace();
        }
        return null;
    }

    public String validateName(String name, boolean checkExist) {
        String failReason = null;
        if( !Helper.isAlphanumeric(name) || name.isEmpty() )
            failReason = "Letters only";
        else if( checkExist ) {
            try {
                if(User.userExist(name))
                    failReason = "Already exists";
            } catch (IOException e) {
                System.out.println("Failed while checking if user exists");
                e.printStackTrace();
            }
        }
        return failReason;
    }

    public String validatePositiveInt(String integer) {
        String failReason = null;
        try {
            int intAge = Integer.parseInt(integer);
            if(intAge < 0) failReason = "Positive only\n";
        }
        catch (NumberFormatException  e) {
            failReason = "Integer only\n";
        }
        return failReason;
    }

    public String[] validateCoordinate(String x, String y) {
        String[] failReasons = new String[2];
        failReasons[0] = validatePositiveInt(x);
        failReasons[1] = validatePositiveInt(y);
        if(failReasons[0] == null) {
            int intX = Integer.parseInt(x);
            if(intX >= House.roomRow) failReasons[0] = "[0-"+(House.roomRow-1) +"]";
        }

        if(failReasons[1] == null) {
            int intY = Integer.parseInt(y);
            if(intY >= House.roomColumn) failReasons[1] = "[0-"+(House.roomColumn-1)+"]";
        }
        return failReasons;
    }

    public String validateAge(String age) {
        String failReason = null;
        failReason = validatePositiveInt(age);
        return failReason;
    }

    public boolean addUser(String name, String status, int age) {
        User newUser = new User(name, status, age);
        try {
            User.addNewUsers(newUser);
            return true;
        } catch (IOException e) {
            System.out.println("Failed while adding user");
            e.printStackTrace();
        }
        return false;
    }

    public boolean editUser(String name, String status, int age, int x, int y) {
        User newUser = new User(name, status, age, x, y);
        try {
            User.addNewUsers(newUser);
            return true;
        } catch (IOException e) {
            System.out.println("Failed while editing user");
            e.printStackTrace();
        }
        return false;

    }

    public User getSingleUser(String name) {
        try {
            return User.getUser(name);
        } catch (IOException e) {
            System.out.println("Failed while fetching user");
            e.printStackTrace();
        }
        return null;
    }

    public boolean deleteSingleUser(String name) {
        try {
            User.deleteUser(name);
            if(Settings.currentUser != null && Settings.currentUser.equals(name)) {
                Settings.currentUser = null;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Failed while deleting user");
            e.printStackTrace();
        }
        return false;
    }

    public ArrayList<String> userInLocation(int x, int y) {
        ArrayList<String> allUserInLocation = new ArrayList<>();
        ArrayList<User> allUsers = getAllUsersList();

        if(allUsers == null) return allUserInLocation;

        for(User u : allUsers) {
            if(u.getX() == x && u.getY() == y)
                allUserInLocation.add(u.name);
        }
        return allUserInLocation;
    }
}
