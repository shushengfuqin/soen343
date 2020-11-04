package org.team4.permissions;


import org.json.JSONArray;
import org.json.JSONObject;
import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.user.User;
import org.team4.user.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Permission {

    public static String permissionFileName = "permissions";

    /**
     * Permissions are comprised of a boolean of 5 index
     * family, guest, stranger, adult, location
     * If any of the requirement are true then they are allowed
     */
    public static boolean windowPermission[]  = {true, true, false, true, true};
    public static boolean doorPermission[]  = {true, true, false, true, true};
    public static boolean lightPermission[]  = {true, true, false, true, true};

    public static UserService userService = new UserService();;

    /**
     * Check if the user can toggle a window
     * @param x coordinate of the room
     * @param y coordinate of the room
     * @return a boolean
     */
    public static boolean checkUserWindowPermission(int x, int y) {
        String currUser = Settings.currentUser;
        if(currUser == null) return false;

        User user = userService.getSingleUser(currUser);

        if(windowPermission[3] && !user.isAdult()) return false;

        if(windowPermission[4] && (user.getX() != x || user.getX() != y)) return false;

        switch (user.getStatus()) {
            case "family":
                return windowPermission[0];
            case "guest":
                return windowPermission[1];
            case "stranger":
                return windowPermission[2];
        }
        return false;
    }

    /**
     * Check if the user can toggle a door
     * @param x coordinate of the room
     * @param y coordinate of the room
     * @return a boolean
     */
    public static boolean checkUserDoorPermission(int x, int y) {
        String currUser = Settings.currentUser;
        if(currUser == null) return false;

        User user = userService.getSingleUser(currUser);

        if(doorPermission[3] && !user.isAdult()) return false;

        if(doorPermission[4] && (user.getX() != x || user.getX() != y)) return false;

        switch (user.getStatus()) {
            case "family":
                return doorPermission[0];
            case "guest":
                return doorPermission[1];
            case "stranger":
                return doorPermission[2];
        }
        return false;
    }

    /**
     * Check if the user can toggle a light
     * @param x coordinate of the room
     * @param y coordinate of the room
     * @return a boolean
     */
    public static boolean checkUserLightPermission(int x, int y) {
        String currUser = Settings.currentUser;
        if(currUser == null) return false;

        User user = userService.getSingleUser(currUser);

        if(lightPermission[3] && !user.isAdult()) return false;

        if(lightPermission[4] && (user.getX() != x || user.getX() != y)) return false;

        switch (user.getStatus()) {
            case "family":
                return windowPermission[0];
            case "guest":
                return windowPermission[1];
            case "stranger":
                return windowPermission[2];
        }
        return false;
    }

    /**
     * Set the new permissions to the static variables and save to file
     * @param window permissions
     * @param door permissions
     * @param light permissions
     */
    public static void saveNewPermission(boolean[] window, boolean[] door, boolean[] light) {
        windowPermission = window;
        doorPermission = door;
        lightPermission = light;
        savePermissionToFile();
    }


    /**
     * Save the permissions to a file as a json object
     */
    public static void savePermissionToFile() {
        JSONObject jo = new JSONObject();
        jo.put("windowPermissions", windowPermission);
        jo.put("lightPermissions", lightPermission);
        jo.put("doorPermissions", doorPermission);

        String permissionStr = jo.toString();
        writeToPermissionFile(permissionStr);
        Logger.info("New permissions have been saved");
    }

    /**
     * Get the permissions from the file
     */
    public static void updatePermissionsFromFile() {
        File userFile = new File(permissionFileName+".json");
        if(!userFile.exists()) return;

        String data = readFromPermissionFile();

        JSONObject jo = new JSONObject(data);
        JSONArray windows = jo.getJSONArray("windowPermissions");
        JSONArray lights = jo.getJSONArray("lightPermissions");
        JSONArray doors = jo.getJSONArray("doorPermissions");

        for(int i = 0; i < windowPermission.length; i++) {
            windowPermission[i] = windows.getBoolean(i);
            doorPermission[i] = doors.getBoolean(i);
            lightPermission[i] = lights.getBoolean(i);
        }
    }

    /**
     * Read the data from a user file
     * @return a string of an array results shows the permission of user
     * @throws IOException
     */
    public static String readFromPermissionFile() {
        File PermissionFile = new File(permissionFileName+".json");
        if (PermissionFile.exists()) {
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(PermissionFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            String data = fileReader.nextLine();
            return data;
        }
        return null;
    }
    /**
     * Writes a string to the permission file
     * @param
     * @return
     * @throws IOException
     */
    public static boolean writeToPermissionFile(String s) {
        try {
            FileWriter myWriter = new FileWriter(permissionFileName+".json");
            myWriter.write(s);
            myWriter.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Error occured while writing to org.team4.permission file");
            e.printStackTrace();
            return false;
        }
    }
}
