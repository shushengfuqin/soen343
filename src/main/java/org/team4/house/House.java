package org.team4.house;

import org.json.JSONArray;
import org.json.JSONObject;
import org.team4.house.components.Room;
import org.team4.house.components.Wall;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class House {
    public static String houseLayoutFileName = "houseLayout";
    public static ArrayList<int[]> windows = new ArrayList<int[]>();
    public static ArrayList<int[]> doors = new ArrayList<int[]>();
    public static int roomColumn = 5;
    public static int roomRow = 5;
    public static Room[][] rooms  = new Room[roomRow][roomColumn];

    /**
     * Get the status of a window
     * @param s
     * @return a boolean representing if a window is open or not
     */
    public static boolean getWindowStatus(String s) {
        int[] windowLocation = getRoomLocation(s);
        return rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
    }

    /**
     * Open or close a window
     * @param s the location of a window
     */
    public static void toggleWindow(String s) {
        int[] windowLocation = getRoomLocation(s);
        boolean windowStatus = rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
        rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open = !windowStatus;
    }

    /**
     * Get the status of a door
     * @param s
     * @return a boolean representing if a door is closed or not
     */
    public static boolean getDoorStatus(String s) {
        int[] windowLocation = getRoomLocation(s);
        return rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
    }

    /**
     * Open or close a door
     * @param s the location of the door
     */
    public static void toggleDoor(String s) {
        int[] windowLocation = getRoomLocation(s);
        boolean windowStatus = rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
        rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open = !windowStatus;
    }

    /**
     * Reset the house Layout
     */
    public static void resetParams() {
        windows = new ArrayList<int[]>();
        doors = new ArrayList<int[]>();
        rooms  = new Room[roomRow][roomColumn];;
    }

    /**
     * Get the room location from a string
     * @param s the location of a room
     * @return an array representing the row column and side
     */
    public static int[] getRoomLocation(String s) {
        s = s.replace(" ", "");
        int indexSideChar = s.indexOf("-");
        String roomRow = s.substring(1,2);
        String roomColumn = s.substring(3,4);
        String roomSide = s.substring(indexSideChar+1);
        int intRoomRow = Integer.parseInt(roomRow);
        int intRoomColumn = Integer.parseInt(roomColumn);
        int intRoomSide = Room.wallSideMapper(roomSide);

        int[] windowLocation = {intRoomRow, intRoomColumn, intRoomSide};
        return windowLocation;
    }

    /**
     * Get the location of every door and window in the house layout
     */
    public static void indexHouseWindowAndDoor() {
        for(int i = 0; i < roomRow; i++) {
            for(int j = 0; j < roomColumn; j++) {
                Room tempRoom = rooms[i][j];
                Wall[] tempWall = tempRoom.walls;
                for(int k = 0; k < tempWall.length; k++) {
                    if(tempWall[k].type.equals("window")) {
                        int[] windowLocation = {i,j,k};
                        windows.add(windowLocation);
                    }
                    else if(tempWall[k].type.equals("door")) {
                        int[] doorLocation = {i,j,k};
                        doors.add(doorLocation);
                    }
                }
            }
        }
    }

    /**
     *
     * @return an array containing the list of all the windows
     */
    public static String[] getAllWindowsOption() {
        String[] windowOption = new String[windows.size()];
        for(int i = 0; i < windows.size(); i++) {
            int[] tempWindow = windows.get(i);
            windowOption[i] = "(" + tempWindow[0] + ", " + tempWindow[1] + ") - " + Room.wallSideMapper(tempWindow[2]);
        }
        return windowOption;
    }

    /**
     *
     * @return an array containing the list of all the windows
     */
    public static String[] getAllDoorsOption() {
        String[] doorOption = new String[doors.size()];
        for(int i = 0; i < doors.size(); i++) {
            int[] tempDoor = doors.get(i);
            doorOption[i] = "(" + tempDoor[0] + ", " + tempDoor[1] + ") - " + Room.wallSideMapper(tempDoor[2]);
        }
        return doorOption;
    }

    /**
     * Randomly generate a house layout
     */
    public static void generateHouse() {
        String[] wallChoices = {"emptySpace", "wall", "window", "door"};
        String[] roomChoices = {"bedroom", "kitchen", "outside", "hallway", "living-room"};
        Random rand = new Random();
        for(int i = 0; i < roomRow; i++) {
            for(int j = 0; j < roomColumn; j++) {
                String roomName = roomChoices[rand.nextInt(5)];
                String lw = wallChoices[rand.nextInt(4)];
                String tw = wallChoices[rand.nextInt(4)];
                String rw = wallChoices[rand.nextInt(4)];
                String bw = wallChoices[rand.nextInt(4)];

                if(roomName.equals("outside")) {
                    lw = wallChoices[0];
                    tw = wallChoices[0];
                    rw = wallChoices[0];
                    bw = wallChoices[0];
                }

                rooms[i][j] = new Room(roomName, lw, tw, rw, bw);
            }
        }
    }

    /**
     * Get the house layout from the layout file
     */
    public static void getHouseLayout() {
        String houseLayout = readFromHouseFile();
        JSONObject house = new JSONObject(houseLayout);
        JSONArray rows = house.getJSONArray("layout");

        for(int i = 0; i < roomRow; i++) {
            JSONArray columns = rows.getJSONArray(i);
            for(int j = 0; j < roomColumn; j++) {
                JSONObject temp = columns.getJSONObject(j);
                rooms[i][j] = new Room(temp);
            }
        }
    }

    /**
     * Save the house layout to a file
     */
    public static void saveHouseLayout() {
        JSONObject houseLayout = new JSONObject();

        JSONArray rows = new JSONArray();

        for(int i = 0; i < roomRow; i++) {
            JSONArray column = new JSONArray();
            for(int j = 0; j < roomColumn; j++) {
                column.put(rooms[i][j].toJson());
            }
            rows.put(column);
        }

        houseLayout.put("layout", rows);

        writeToHouseFile(houseLayout.toString());
    }

    /**
     * Get the string from the house file
     * @return
     */
    public static String readFromHouseFile() {
        File houseFile = new File(houseLayoutFileName+".json");
        if (houseFile.exists()) {
            Scanner fileReader = null;
            try {
                fileReader = new Scanner(houseFile);
            } catch (FileNotFoundException e) {
                System.out.println("Error occured while reading from org.team4.house file");
                e.printStackTrace();
            }
            String data = fileReader.nextLine();
            return data;
        }
        return null;
    }

    /**
     * Write to the house layout file
     * @param s
     * @return
     */
    public static boolean writeToHouseFile(String s) {
        try {
            FileWriter myWriter = new FileWriter(houseLayoutFileName+".json");
            myWriter.write(s);
            myWriter.close();
            return true;
        }
        catch (IOException e) {
            System.out.println("Error occured while writing to org.team4.house file");
            e.printStackTrace();
            return false;
        }
    }
}
