package org.team4.user;

import org.team4.common.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class User {
    public static int adultThreshold = 18;
    public static String userFileName = "users.json";

    public Coordinate coord;
    public int age;
    public String status;
    public String name;

    public User(String name, String status, int age) {
        this.status = status;
        this.age = age;
        this.name = name;
        this.coord = new Coordinate(0, 0);
    }

    public User(String name, String status, int age, int x, int y) {
        this.status = status;
        this.age = age;
        this.coord = new Coordinate(x,y);
        this.name = name;
    }

    public User(JSONObject user) {
        this.age = user.getInt("age");
        this.name = user.getString("name");
        this.status = user.getString("status");
        JSONObject coordinate = user.getJSONObject("coord");
        int x = coordinate.getInt("x");
        int y = coordinate.getInt("y");
        this.coord = new Coordinate(x,y);
    }

    public int getAge() {
        return age;
    }

    public String getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return coord.x;
    }

    public int getY() {
        return coord.y;
    }

    public boolean isAdult() {
        return this.age >= User.adultThreshold;
    }

    /**
     * Convert the user into a json object
     * @return
     */
    private JSONObject toJson() {
        JSONObject jo = new JSONObject();
        jo.put("age", this.age);
        jo.put("status", this.status);
        jo.put("coord", this.coord.toJson());
        jo.put("name", this.name);

        return jo;
    }

    public String toString() {
        return "Name: " + this.name + "\nStatus: " + this.status + "\nAge: " + this.age + "\nCoordinate: " + this.coord;
    }


    /**
     * Get a user from all the users
     * @param name of the user
     * @return the User object
     * @throws IOException
     */
    public static User getUser(String name) throws IOException {
        ArrayList<User> userList = User.getAllUsers();
        for(User user: userList) {
            if(user.name.equals(name)) return user;
        }
        return null;
    }

    /**
     * Get all users from the user file
     * @return an array of users
     * @throws IOException
     */
    public static ArrayList<User> getAllUsers() throws IOException {
        ArrayList<User> allUsers = new ArrayList<User>();
        String userJson = User.readFromUserFile();
        if(userJson == null || userJson.equals("[]")) {
            return allUsers;
        }

        JSONArray ja = new JSONArray(userJson);

        for(int i = 0; i < ja.length(); i++) {
            JSONObject userJO = ja.getJSONObject(i);
            User tempUser = new User(userJO);
            allUsers.add(tempUser);
        }

        return allUsers;
    }

    /**
     * Check if a user exist or not
     * @param name of the user
     * @return true if the user exists
     * @throws IOException
     */
    public static boolean userExist(String name) throws IOException {
        ArrayList<User> userList = User.getAllUsers();
        for(User user: userList) {
            if(user.name.equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    /**
     * Add a new user to the users list
     * @param u a user
     * @return a boolean if the operation succeeded or not
     * @throws IOException
     */
    public static boolean addNewUsers(User u) throws IOException {
        String userName = u.name;
        boolean exist = User.userExist(userName);

        ArrayList<User> userList = User.getAllUsers();
        if(exist) {
            for(User user: userList) {
                if(user.name.equals(userName)) {
                    userList.remove(user);
                    break;
                }
            }
        }
        userList.add(u);

        JSONArray ja = new JSONArray();

        for(User user : userList ) {
            JSONObject jo = user.toJson();
            ja.put(jo);
        }

        String userStr = ja.toString();

        if(User.writeToUserFile(userStr)) {
            System.out.println("User add success");
            return true;
        }
        System.out.println("User add failed - Reason: Failed to write to file");
        return false;
    }

    /**
     * Deletes a user from a user list
     * @param name the name of the user
     * @return a boolean whether the operation succeeded or not
     * @throws IOException
     */
    public static boolean deleteUser(String name) throws IOException {
        if(!User.userExist(name)) {
            System.out.println("User delete failed - Reason: User does not exist");
            return false;
        }
        ArrayList<User> userList = User.getAllUsers();
        JSONArray ja = new JSONArray();

        for(User user : userList ) {
            if(user.name.equals(name)) continue;
            JSONObject jo = user.toJson();
            ja.put(jo);
        }

        String userStr = ja.toString();
        if(User.writeToUserFile(userStr)) {
            System.out.println("User delete success");
            return true;
        }

        System.out.println("User delete failed - Reason: Failed to write to file");
        return false;

    }

    /**
     * Read the data from a user file
     * @return a string containing the list of users
     * @throws IOException
     */
    public static String readFromUserFile() throws IOException {
        File userFile = new File(userFileName);
        if (userFile.exists()) {
            Scanner fileReader = new Scanner(userFile);
            String data = fileReader.nextLine();
            return data;
        }
        return null;
    }

    /**
     * Writes a string to the user file
     * @param s
     * @return
     * @throws IOException
     */
    public static boolean writeToUserFile(String s) throws IOException {
        try {
            FileWriter myWriter = new FileWriter(userFileName);
            myWriter.write(s);
            myWriter.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Error occured while writing to org.team4.user file");
            e.printStackTrace();
            return false;
        }
    }
}
