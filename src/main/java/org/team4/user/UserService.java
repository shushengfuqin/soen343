package org.team4.user;

import org.team4.common.Settings;

import java.io.IOException;
import java.util.ArrayList;

public class UserService {

    public UserService() {
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
        if( !this.isAlphanumeric(name) || name.isEmpty() )
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

    public String validateAge(String age) {
        String failReason = null;
        try {
            int intAge = Integer.parseInt(age);
            if(intAge < 0) failReason = "Positive only\n";
        }
        catch (NumberFormatException  e) {
            failReason = "Integer only\n";
        }
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
            if(Settings.currentUser.equals(name)) {
                Settings.currentUser = null;
            }
            return true;
        } catch (IOException e) {
            System.out.println("Failed while deleting user");
            e.printStackTrace();
        }
        return false;
    }

}
