package user;

import helpers.Coordinate;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class UserModel {
    public static int adultThreshold = 18;
    public static String userFileName = "users.json";
    private Coordinate coord;
    private int age;
    private String status;
    private String name;

    public UserModel(String name, String status, int age) {
        this.status = status;
        this.age = age;
        this.name = name;
        this.coord = new Coordinate(0, 0);
    }

    public UserModel(String name, String status, int age, int x, int y) {
        this.status = status;
        this.age = age;
        this.coord = new Coordinate(x,y);
        this.name = name;
    }

    public UserModel(JSONObject user) {
        this.age = user.getInt("age");
        this.name = user.getString("name");
        this.status = user.getString("status");
        JSONObject coordinate = user.getJSONObject("coord");
        int x = coordinate.getInt("x");
        int y = coordinate.getInt("y");
        this.coord = new Coordinate(x,y);
    }

    public boolean isAdult() {
        return this.age >= UserModel.adultThreshold;
    }

    public String getName() {
        return name;
    }

    public Coordinate getCoord() {
        return this.coord;
    }

    public String getStatus() {
        return status;
    }

    public int getAge() {
        return age;
    }

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

    public static boolean userExist(String name) throws IOException {
        ArrayList<UserModel> userList = UserModel.getAllUsers();
        for(UserModel user: userList) {
            if(user.getName().equalsIgnoreCase(name)) return true;
        }
        return false;
    }

    public static boolean addNewUsers(UserModel u) throws IOException {
        String userName = u.getName();
        if(UserModel.userExist(userName)) {
            System.out.println("User add failed - Reason: Already exist");
            return false;
        }

        ArrayList<UserModel> userList = UserModel.getAllUsers();
        userList.add(u);

        JSONArray ja = new JSONArray();

        for(UserModel user : userList ) {
            JSONObject jo = user.toJson();
            ja.put(jo);
        }

        String userStr = ja.toString();

        if(UserModel.writeToUserFile(userStr)) {
            System.out.println("User add success");
            return true;
        }
        System.out.println("User add failed - Reason: Failed to write to file");
        return false;
    }

    public static boolean deleteUser(String name) throws IOException {
        if(!UserModel.userExist(name)) {
            System.out.println("User delete failed - Reason: User does not exist");
            return false;
        }
        ArrayList<UserModel> userList = UserModel.getAllUsers();
        JSONArray ja = new JSONArray();

        for(UserModel user : userList ) {
            if(user.getName().equals(name)) continue;
            JSONObject jo = user.toJson();
            ja.put(jo);
        }

        String userStr = ja.toString();
        if(UserModel.writeToUserFile(userStr)) {
            System.out.println("User delete success");
            return true;
        }

        System.out.println("User delete failed - Reason: Failed to write to file");
        return false;

    }

    public static boolean modifyUser(String name, String status, int age, int x, int y) throws IOException {
        if(!UserModel.userExist(name)) {
            System.out.println("User modification failed - Reason: User does not exist");
            return false;
        }

        ArrayList<UserModel> userList = UserModel.getAllUsers();
        JSONArray ja = new JSONArray();

        UserModel tempUser = new UserModel(name, status, age, x, y);

        for(UserModel user : userList ) {
            JSONObject jo = user.toJson();
            if(user.getName().equals(name)) {
                jo = tempUser.toJson();
            }
            ja.put(jo);
        }

        String userStr = ja.toString();
        if(UserModel.writeToUserFile(userStr)) {
            System.out.println("User modification success");
            return true;
        }

        System.out.println("User modification failed - Reason: Failed to write to file");
        return false;

    }

    public static UserModel getUser(String name) throws IOException {
        ArrayList<UserModel> userList = UserModel.getAllUsers();
        for(UserModel user: userList) {
            if(user.getName().equals(name)) return user;
        }
        return null;
    }

    public static boolean updateUserCoordinate(String name, int x, int y) throws IOException {
        UserModel user = UserModel.getUser(name);
        return UserModel.modifyUser(name, user.getStatus(), user.getAge(), x,y);
    }

    public static ArrayList<UserModel> getAllUsers() throws IOException {
        ArrayList<UserModel> allUsers = new ArrayList<UserModel>();
        String userJson = UserModel.readFromUserFile();
        if(userJson == null || userJson.equals("[]")) {
            System.out.println("User list is empty");
            return allUsers;
        }

        JSONArray ja = new JSONArray(userJson);

        for(int i = 0; i < ja.length(); i++) {
            JSONObject userJO = ja.getJSONObject(i);
            UserModel tempUser = new UserModel(userJO);
            allUsers.add(tempUser);
        }

        return allUsers;
    }

    public static String readFromUserFile() throws IOException {
        File userFile = new File(userFileName);
        if (userFile.exists()) {
            Scanner fileReader = new Scanner(userFile);
            String data = fileReader.nextLine();
            return data;
        }
        return null;
    }

    public static boolean writeToUserFile(String s) throws IOException {
        try {
            FileWriter myWriter = new FileWriter(userFileName);
            myWriter.write(s);
            myWriter.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Error occured while writing to user file");
            e.printStackTrace();
            return false;
        }
    }
}
