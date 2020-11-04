package org.team4.simulationParameters;


import org.json.JSONArray;
import org.json.JSONObject;
import org.team4.common.Settings;
import org.team4.user.User;
import org.team4.user.UserService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Permission {

    //family, guest, stranger, adult, location
    public static boolean windowPermission[]  = {true, true, false, true, true};
    public static boolean doorPermission[]  = {true, true, false, true, true};
    public static boolean lightPermission[]  = {true, true, false, true, true};

    public static UserService userService = new UserService();;
    public Permission() { }

    //Todo: do this method for doors and light
    public static boolean checkUserWindowPermission(int x, int y) {
        String currUser = Settings.currentUser;
        if(currUser == null) return true;

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

    public static void saveNewPermission(boolean[] window) {
        windowPermission = window;
        //todo: Door permission and light permission
        //todo: Save the new permission to the file
        savePermissionToFile();
    }


    public static void savePermissionToFile() {
        JSONObject jo = new JSONObject();
        jo.put("windowPermissions", windowPermission);
        //todo: add door permission and light permission

        String permissionStr = jo.toString();
        writeToPermissionFile(permissionStr);
    }

    public static void updatePermissionsFromFile() {
        File userFile = new File("permissionFileName.json");
        if(!userFile.exists()) return;

        String data = readFromPermissionFile();

        JSONObject jo = new JSONObject(data);
        JSONArray arr = jo.getJSONArray("windowPermissions");
        for(int i = 0; i < windowPermission.length; i++) {
            windowPermission[i] = arr.getBoolean(i);
            //todo: Do the same for doors and light
        }
    }

    /**
     * Read the data from a user file
     * @return a string of an array results shows the permission of user
     * @throws IOException
     */
    public static String readFromPermissionFile() {
        File userFile = new File("permissionFileName.json");
        if (userFile.exists()) {
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(userFile);
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
            FileWriter myWriter = new FileWriter("permissionFileName.json");
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
