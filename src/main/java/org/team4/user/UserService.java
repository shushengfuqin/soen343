package org.team4.user;

import org.team4.common.Coordinate;
import org.team4.common.Helper;
import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.common.observer.ObservableHandler;
import org.team4.house.HouseService;

import java.io.IOException;
import java.util.ArrayList;

public class UserService {

    private HouseService houseService;

    @Deprecated
    public static ObservableHandler userMovementObserver = new ObservableHandler();

    public UserService() {
        houseService = new HouseService();
    }

    /**
     * Get the arraylist containing all users
     * @return an arraylist of all users
     */
    public ArrayList<User> getAllUsersList() {
        try {
            return User.getAllUsers();
        } catch (IOException e) {
            System.out.println("Failure while retrieving all users");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Check whether a name is valid
     * @param name name to check
     * @param checkExist check if the user already exist
     * @return the reason why it is not valid
     */
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

    /**
     * Check if an string is a valid integer
     * @param integer
     * @return the reason why it's not valid
     */
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

    /**
     * Check if x and y coordinates are valid
     * @param x
     * @param y
     * @return the reason why a coordinate is not valid
     */
    public String[] validateCoordinate(String x, String y) {
        String[] failReasons = new String[2];
        failReasons[0] = validatePositiveInt(x);
        failReasons[1] = validatePositiveInt(y);
        if(failReasons[0] == null) {
            int intX = Integer.parseInt(x);
            if(intX >= houseService.house.roomRow) failReasons[0] = "[0-"+(houseService.house.roomRow-1) +"]";
        }

        if(failReasons[1] == null) {
            int intY = Integer.parseInt(y);
            if(intY >= houseService.house.roomColumn) failReasons[1] = "[0-"+(houseService.house.roomColumn-1)+"]";
        }
        return failReasons;
    }

    /**
     * Check if the age is valid or not
     * @param age
     * @return the reason why the age is not valid
     */
    public String validateAge(String age) {
        String failReason = null;
        failReason = validatePositiveInt(age);
        return failReason;
    }


    /**
     * Add a new user to the user lists
     * @param name
     * @param status
     * @param age
     * @return a boolean whether the operation was successful or not
     */
    public boolean addUser(String name, String status, int age) {
        User newUser = new User(name, status, age);
        try {
            User.addNewUsers(newUser);
            userMovementObserver.notifyUserMovement();
            Logger.info("New user has been added: " + newUser);
            return true;
        } catch (IOException e) {
            System.out.println("Failed while adding user");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Modify a user
     * @param name
     * @param status
     * @param age
     * @param x
     * @param y
     * @return a boolean representing if the operation failed or not
     */
    public boolean editUser(String name, String status, int age, int x, int y) {
        User newUser = new User(name, status, age, x, y);
        try {
            User.addNewUsers(newUser);
            userMovementObserver.notifyUserMovement();
            Logger.info("User has been edited: " + newUser);
            return true;
        } catch (IOException e) {
            System.out.println("Failed while editing user");
            e.printStackTrace();
        }
        return false;

    }

    /**
     * Get a user from a user list
     * @param name of the user
     * @return the user
     */
    public User getSingleUser(String name) {
        try {
            return User.getUser(name);
        } catch (IOException e) {
            System.out.println("Failed while fetching user");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Delete a user of the user list
     * @param name of the user
     * @return a boolean whether the operation was successful or not
     */
    public boolean deleteSingleUser(String name) {
        try {
            User.deleteUser(name);
            if(Settings.currentUser != null && Settings.currentUser.equals(name)) {
                Settings.currentUser = null;
            }
            userMovementObserver.notifyUserMovement();
            Logger.info("User has been deleted: " + name);
            return true;
        } catch (IOException e) {
            System.out.println("Failed while deleting user");
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Find whether or not a user is in the location x,y
     * @param x
     * @param y
     * @return an array containing all users in that location
     */
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

    /**
     * Check whether a user is in the house or not
     */
    public boolean userInHouse() {
        for(Coordinate coord : houseService.house.lights) {
            ArrayList<String> allUsersInRoom = userInLocation(coord.x, coord.y);
            if(!allUsersInRoom.isEmpty())
                return true;
        }
        return false;
    }
}
