package org.team4.house;

import org.json.JSONArray;
import org.json.JSONObject;
import org.team4.common.Coordinate;
import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.house.components.Room;
import org.team4.house.components.Wall;
import org.team4.permissions.Permission;
import org.team4.user.UserService;

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
    public static ArrayList<Coordinate> lights = new ArrayList<>();
    public static ArrayList<Coordinate> lightsAway = new ArrayList<>();
    public static ArrayList<Coordinate> indoorRooms = new ArrayList<>();
    public static ArrayList<int[]> lockDoor = new ArrayList<int[]>();
    public static int roomColumn = 5;
    public static int roomRow = 5;
    public static Room[][] rooms  = new Room[roomColumn][roomRow];
    public static UserService userService = new UserService();

    /**
     * Close all windows
     * @return a boolean if it's a success or not
     */
    public static boolean closeAllWindowsAndDoors() {
        String[] allWindows = getAllWindowsOption();
        for(String window: allWindows) {
            int[] loc = getRoomLocation(window);
            if(rooms[loc[0]][loc[1]].walls[loc[2]].open) {
                boolean success = toggleWindowOpen(window, true);
                if(!success) return false;
            }
        }

        String[] allDoors = getAllDoorsOption();
        for(String door: allDoors) {
            int[] loc = getRoomLocation(door);
            if(rooms[loc[0]][loc[1]].walls[loc[2]].open) {
                boolean success = toggleDoor(door, true);
                if(!success) return false;
            }
        }
        return true;
    }

    //Check if window are blocked. return true if blocked
    public static boolean checkWindowBlock(int x, int y){
        for(int i = 0; i < 4 ; i++){
            if(rooms[x][y].walls[i].type.equals("window")){
                if(rooms[x][y].walls[i].blocked){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Turns on all away mode lights
     */
    public static void turnOnAllAwayModeLights() {
        for(Coordinate c : lightsAway) {
            rooms[c.x][c.y].lightOn = true;
        }
    }

    /**
     * Open all windows from x, y, room
     */
    public static void openAllWindowInRoom(int x, int y){
        for(int i = 0; i < 4 ; i++){
            if(rooms[x][y].walls[i].type.equals("window")){
                rooms[x][y].walls[i].open = true;
            }
        }
    }

    /**
     * close all windows from x, y, room
     */
    public static void closeAllWindowInRoom(int x, int y){
        for(int i = 0; i < 4 ; i++){
            if(rooms[x][y].walls[i].type.equals("window")){
                rooms[x][y].walls[i].open = false;
            }
        }
    }

    /**
     * Check whether a user is in the house or not
     */
    public static boolean userInHouse() {
        for(Coordinate coord : lights) {
            ArrayList<String> allUsersInRoom = userService.userInLocation(coord.x, coord.y);
            if(!allUsersInRoom.isEmpty())
                return true;
        }
        return false;
    }

    /**
     * Get the status of a window
     * @param s
     * @return a boolean representing if a window is open or not
     */
    public static boolean getWindowStatusOpen(String s) {
        int[] windowLocation = getRoomLocation(s);
        return rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
    }

    /**
     * get the status of if a window is closed or not
     * @param s position of window
     * @return boolean
     */
    public static boolean getWindowStatusBlock(String s) {
        int[] windowLocation = getRoomLocation(s);
        return rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked;
    }

    /**
     * Open or close a window
     * @param s the location of a window
     */
    public static boolean toggleWindowOpen(String s, boolean byPassPermission) {
        int[] windowLocation = getRoomLocation(s);
        if(!byPassPermission && !Permission.checkUserWindowPermission(windowLocation[0], windowLocation[1])) return false;

        if(rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked) {
            Logger.warning("Can't open window. Blocked");
            return false;
        }
        boolean windowStatus = rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open;
        rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].open = !windowStatus;
        String location = "(" + windowLocation[0] + ", " + windowLocation[1] + ") " + Room.wallSideMapper(windowLocation[2]);
        String action = windowStatus ? "closed" : "opened";
        Logger.info("Window " + action + " at location " + location);
        return true;
    }

    /**
     * Open or close a window
     * @param s window position
     */
    public static void toggleWindowBlock(String s) {
        int[] windowLocation = getRoomLocation(s);
        boolean windowStatus = rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked;
        rooms[windowLocation[0]][windowLocation[1]].walls[windowLocation[2]].blocked = !windowStatus;
        String location = "(" + windowLocation[0] + ", " + windowLocation[1] + ") " + Room.wallSideMapper(windowLocation[2]);
        String action = windowStatus ? "unblocked" : "blocked";
        Logger.info("Window " + action + " at location " + location);
    }

    public static void toggleDoorLock(String s){
        int[] doorLocation = getRoomLocation(s);
        if(!Permission.checkUserDoorPermission(doorLocation[0], doorLocation[1])) return;
        boolean doorLockStatus = rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked;
        if(rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open && !doorLockStatus){
            rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open = false;
        }
        rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked = !doorLockStatus;
        String location = "(" + doorLocation[0] + ", " + doorLocation[1] + ") " + Room.wallSideMapper(doorLocation[2]);
        String action = doorLockStatus ? "unlocked" : "locked";
        Logger.info("Door " + action + " at location " + location);
    }

    /**
     * Function lock all doors during away mode
     */
    public static void lockAllDoor(){
        for(int doorLocation[]:lockDoor){
            boolean doorStatus = rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked;
            if(rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open && !doorStatus){
                rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open = false;
            }
            rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked = true;
            String location = "(" + doorLocation[0] + ", " + doorLocation[1] + ") " + Room.wallSideMapper(doorLocation[2]);
            Logger.info("Door locked at location " + location);
        }
    }

    /**
     * Check if a light is on or off
     * @param s location of light
     * @return a boolean
     */
    public static boolean getLightStatus(String s) {
        Coordinate lightLocation = new Coordinate(s);
        return rooms[lightLocation.x][lightLocation.y].lightOn;
    }

    /**
     * Check if the light is in away list
     * @param s location of light
     * @return boolean
     */
    public static boolean getLightAwayStatus(String s) {
        Coordinate lightLocation = new Coordinate(s);
        boolean InArray = coordInArrayList(lightsAway, lightLocation);
        return InArray;
    }

    /**
     * Get the status of a door
     * @param s
     * @return a boolean representing if a door is closed or not
     */
    public static boolean getDoorStatus(String s) {
        int[] doorLocation = getRoomLocation(s);
        return rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open;
    }

    /**
     * Get the status of the door is lock or not
     */
    public static boolean getLockDoorStatus(String s){
        int[] lockDoorLocation = getRoomLocation(s);
        return rooms[lockDoorLocation[0]][lockDoorLocation[1]].walls[lockDoorLocation[2]].blocked;
    }
    /**
     * Open or close a door
     * @param s the location of the door
     */
    public static boolean toggleDoor(String s, boolean byPassPermission) {
        int[] doorLocation = getRoomLocation(s);
        if(!byPassPermission && !Permission.checkUserDoorPermission(doorLocation[0], doorLocation[1])) return false;
        String location = "(" + doorLocation[0] + ", " + doorLocation[1] + ") " + Room.wallSideMapper(doorLocation[2]);

        boolean doorLocked = rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].blocked;
        if(doorLocked) {
            Logger.warning("Unable to open door at location " + location + ". Door is locked");
            return false;
        }

        boolean doorStatus = rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open;
        rooms[doorLocation[0]][doorLocation[1]].walls[doorLocation[2]].open = !doorStatus;

        switch (doorLocation[2]) {
            case 0:
                if(doorLocation[0] - 1 < 0) return true;
                if(rooms[doorLocation[0]-1][doorLocation[1]].walls[2].type.equals("door"))
                    rooms[doorLocation[0]-1][doorLocation[1]].walls[2].open = !doorStatus;
                break;
            case 1:
                if(doorLocation[1] - 1 < 0) return true;
                if(rooms[doorLocation[0]][doorLocation[1]-1].walls[3].type.equals("door")) {
                    rooms[doorLocation[0]][doorLocation[1] - 1].walls[3].open = !doorStatus;
                }
                break;
            case 2:
                if(doorLocation[0] + 1 >= roomColumn) return true;
                if(rooms[doorLocation[0]+1][doorLocation[1]].walls[0].type.equals("door")) {
                    rooms[doorLocation[0] + 1][doorLocation[1]].walls[0].open = !doorStatus;
                }
                break;
            case 3:
                if(doorLocation[1] + 1 >= roomRow) return true;
                if(rooms[doorLocation[0]][doorLocation[1]+1].walls[1].type.equals("door"))
                    rooms[doorLocation[0]][doorLocation[1]+1].walls[1].open = !doorStatus;
                break;
        }
        String action = doorStatus ? "closed" : "opened";
        Logger.info("Door " + action + " at location " + location);
        return true;
    }

    /**
     * Reset the house Layout
     */
    public static void resetParams() {
        windows = new ArrayList<int[]>();
        doors = new ArrayList<int[]>();
        rooms  = new Room[roomColumn][roomRow];;
        lockDoor = new ArrayList<int[]>();
        lightsAway = new ArrayList<Coordinate>();
        lights = new ArrayList<Coordinate>();
        indoorRooms = new ArrayList<Coordinate>();
        Settings.lightAutoMode = false;
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
        for(int i = 0; i < roomColumn; i++) {
            for(int j = 0; j < roomRow; j++) {
                Room tempRoom = rooms[i][j];
                Wall[] tempWall = tempRoom.walls;
                for(int k = 0; k < tempWall.length; k++) {
                    if(tempWall[k].type.equals("window")) {
                        int[] windowLocation = {i,j,k};
                        windows.add(windowLocation);
                    }
                    else if(tempWall[k].type.equals("door")) {
                        int[] doorLocation = {i,j,k};
                        if(rooms[i][j].name.equals("entrance") || rooms[i][j].name.equals("backyard") || rooms[i][j].name.equals("garage") ){
                            indexLockDoor(i,j,k);
                        }
                        doors.add(doorLocation);
                    }
                }
            }
        }
    }

    /**
     * Get the location of all lights in the house layout
     */
    public static void indexAllLights() {
        for(int i = 0; i < roomColumn; i++) {
            for(int j = 0; j < roomRow; j++) {
                Room tempRoom = rooms[i][j];
                String roomName = tempRoom.name;
                if(roomName.equals("outside")) continue;
                Coordinate coord = new Coordinate(i, j);
                lights.add(coord);
            }
        }
    }

    /**
     * Index all of the indoor rooms in the house
     */
    public static void indexAllIndoorRooms() {
        for(int i = 0; i < roomColumn; i++) {
            for(int j = 0; j < roomRow; j++) {
                Room tempRoom = rooms[i][j];
                String roomName = tempRoom.name;
                if(roomName.equals("outside") || roomName.equals("backyard")) continue;
                Coordinate coord = new Coordinate(i, j);
                indoorRooms.add(coord);
            }
        }
    }

    /**
     * turn on all lights in the house
     */
    public static void turnOnAllLights() {
        for(Coordinate coord : lights) {
            rooms[coord.x][coord.y].lightOn = true;
        }
    }

    /**
     * turn off all lights in the house
     */
    public static void turnOffAllLights() {
        for(Coordinate coord : lights) {
            rooms[coord.x][coord.y].lightOn = false;
        }
    }

    /**
     * Turn all lights in rooms containing users
     * turn off the other ones
     */
    public static void turnOnAllLightsWithUsers() {
        for(Coordinate coord : lights) {
            ArrayList<String> allUsersInRoom = userService.userInLocation(coord.x, coord.y);
            if(allUsersInRoom.isEmpty())
                rooms[coord.x][coord.y].lightOn = false;
            else
                rooms[coord.x][coord.y].lightOn = true;

        }
    }

    /**
     * Enable or disable light automatic mode
     */
    public static void toggleLightAuto() {
        boolean lightAuto = Settings.lightAutoMode;
        if(lightAuto) {
            turnOnAllLights();
            Settings.lightAutoMode = false;
            Logger.info("Light automatic mode has been disabled");
            return;
        }
        turnOnAllLightsWithUsers();
        Settings.lightAutoMode = true;
        Logger.info("Light automatic mode has been enabled");
    }

    /**
     * Turn on and off the light
     * @param c the location of a light
     */
    public static void toggleLights(String c) {
        Coordinate coord = new Coordinate(c);
        if(!Permission.checkUserLightPermission(coord.x, coord.y)) return;
        boolean lightsOn = rooms[coord.x][coord.y].lightOn;
        rooms[coord.x][coord.y].lightOn = !lightsOn;
        String action = lightsOn ? "turned off" : "turned on";
        Logger.info("Light " + action + " in location: " + coord.toString());
    }

    /**
     * Remove or add a light from away mode lights
     * @param c coordinate of a light
     */
    public static void toggleLightsAway(String c) {
        Coordinate coord = new Coordinate(c);
        boolean InArray = coordInArrayList(lightsAway, coord);
        if(InArray){
            for(Coordinate coordinate: lightsAway) {
                if(coordinate.x == coord.x && coordinate.y == coord.y)
                    coord = coordinate;
            }
            lightsAway.remove(coord);
            Logger.info("Light removed to light away list. Coordinate: " + coord.toString());
        }
        else {
            lightsAway.add(coord);
            Logger.info("Light added to light away list. Coordinate: " + coord.toString());
        }
    }

    /**
     * Check if the coordinates are in the arraylist
     * @param c coordinates array
     * @param z coordinate
     * @return a boolean
     */
    public static boolean coordInArrayList(ArrayList<Coordinate> c, Coordinate z){
        for(Coordinate coords: c){
            if(coords.x == z.x && coords.y == z.y){
                return true;
            }
        }
        return false;
    }

    /**
     * Get all the lights in the house
     * @return
     */
    public static String[] getAllLightsOption(){
        String[] lightOption = new String[lights.size()];
        for(int i = 0; i < lights.size(); i++) {
            Coordinate tempLight = lights.get(i);
            lightOption[i] = tempLight.toString();
        }
        return lightOption;
    }

    /**
     * check adjacent room if it outside
     * i column, j row.
     */
    public static void indexLockDoor(int i,int j,int k){
        //check the side
        String side = Room.wallSideMapper(k);
        int[] tempLockDoor = {i,j,k};
        switch (side){
            case "left":
                if(i>=0){
                    int leftRoomIndex = i - 1;
                    if (i == 0){
                        lockDoor.add(tempLockDoor);
                    }
                    else if(rooms[leftRoomIndex][j].name.equals("outside")){
                        lockDoor.add(tempLockDoor);
                    }
                }
                break;
            case "right":
                if(i<roomColumn){
                    int rightRoomIndex = i + 1;
                    if (i == roomColumn - 1){
                        lockDoor.add(tempLockDoor);
                    }
                    else if(rooms[rightRoomIndex][j].name.equals("outside")){
                        lockDoor.add(tempLockDoor);
                    }
                }
                break;
            case "top":
                if(j>=0){
                    int topRoomIndex = j - 1;
                    if (j == 0){
                        lockDoor.add(tempLockDoor);
                    }
                    else if(rooms[i][topRoomIndex].name.equals("outside")){
                        lockDoor.add(tempLockDoor);
                    }
                }
                break;
            case "bot":
                if(j<roomRow){
                    int botRoomIndex = j + 1;
                    if (j == roomRow-1){
                        lockDoor.add(tempLockDoor);
                    }
                    else if(rooms[i][botRoomIndex].name.equals("outside")){
                        lockDoor.add(tempLockDoor);
                    }
                }
                break;
        }
    }

    /**
     * Get all locked doors
     * @return string array with lock door locations
     */
    public static String[] getAllLockDoor(){
        String[] lockDoorOption = new String[lockDoor.size()];
        for(int i = 0; i < lockDoor.size(); i++){
            int[] tempDoor = lockDoor.get(i);
            lockDoorOption[i] = "(" + tempDoor[0] + ", " + tempDoor[1] + ") - " + Room.wallSideMapper(tempDoor[2]);
        }
        return lockDoorOption;
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
        String[] wallChoices = {"empty", "wall", "window", "door"};
        String[] roomChoices = {"bedroom", "kitchen", "outside", "hallway", "living-room", "garage", "entrance", "backyard"};
        Random rand = new Random();
        for(int i = 0; i < roomColumn; i++) {
            for(int j = 0; j < roomRow; j++) {
                String roomName = roomChoices[rand.nextInt(8)];
                String lw = "empty";
                String tw = "empty";
                String rw = "empty";
                String bw = "empty";

                if(roomName.equals("outside")) {
                    lw = wallChoices[0];
                    tw = wallChoices[0];
                    rw = wallChoices[0];
                    bw = wallChoices[0];
                }

                rooms[i][j] = new Room(roomName, lw, tw, rw, bw);
            }
        }
        cleanUpHouseLayout();
    }

    public static void cleanUpHouseLayout() {
        if(rooms[0][0] == null) return;
        Random rand = new Random();
        String[] wallChoices = {"wall", "window", "door"};

        for(int i = 0; i < roomColumn; i++) {
            for(int j =0; j < roomRow; j++) {
                Room curr = rooms[i][j];
                String lw;
                String tw;
                String rw;
                String bw;
                if(curr.name.equals("outside")) continue;

                if( i-1 < 0) {
                    lw = wallChoices[rand.nextInt(3)];
                }
                else {
                    if(curr.name.equals(rooms[i-1][j].name)) {
                        lw = "empty";
                    }
                    else if(rooms[i-1][j].walls[3].type.equals("door")) {
                        lw = "door";
                    }
                    else if(rooms[i-1][j].name.equals("outside")) {
                        lw = wallChoices[rand.nextInt(3)];
                    }
                    else if(!rooms[i-1][j].name.equals(curr.name)) {
                        lw = "door";
                    }
                    else {
                        lw = "wall";
                    }
                }

                if( j-1 < 0) {
                    tw = wallChoices[rand.nextInt(3)];
                }
                else {
                    if(curr.name.equals(rooms[i][j-1].name)) {
                        tw = "empty";
                    }
                    else if(rooms[i][j-1].walls[3].type.equals("door")) {
                        tw = "door";
                    }
                    else if(rooms[i][j-1].name.equals("outside")) {
                        tw = wallChoices[rand.nextInt(3)];
                    }
                    else if(!rooms[i][j-1].name.equals(curr.name)) {
                        tw = "door";
                    }
                    else {
                        tw = "wall";
                    }
                }

                if( i+1 >= roomColumn) {
                    rw = wallChoices[rand.nextInt(3)];
                }
                else {
                    if(curr.name.equals(rooms[i+1][j].name)) {
                        rw = "empty";
                    }
                    else if(rooms[i+1][j].walls[3].type.equals("door")) {
                        rw = "door";
                    }
                    else if(rooms[i+1][j].name.equals("outside")) {
                        rw = wallChoices[rand.nextInt(3)];
                    }
                    else if(!rooms[i+1][j].name.equals(curr.name)) {
                        rw = "door";
                    }
                    else {
                        rw = "wall";
                    }
                }

                if( j+1 >= roomRow) {
                    bw = wallChoices[rand.nextInt(3)];
                }
                else {
                    if(curr.name.equals(rooms[i][j+1].name)) {
                        bw = "empty";
                    }
                    else if(rooms[i][j+1].walls[3].type.equals("door")) {
                        bw = "door";
                    }
                    else if(rooms[i][j+1].name.equals("outside")) {
                        bw = wallChoices[rand.nextInt(3)];
                    }
                    else if(!rooms[i][j+1].name.equals(curr.name)) {
                        bw = "door";
                    }
                    else {
                        bw = "wall";
                    }
                }
                Room newRoom = new Room(curr.name, lw, tw, rw, bw);
                rooms[i][j] = newRoom;

            }
        }
    }

    /**
     * Get the house layout from the layout file
     */
    public static Room[][] getHouseLayout() {
        if(fileExist()) {
            String houseLayout = readFromHouseFile();
            JSONObject house = new JSONObject(houseLayout);
            JSONArray rows = house.getJSONArray("layout");

            for (int i = 0; i < roomColumn; i++) {
                JSONArray columns = rows.getJSONArray(i);
                for (int j = 0; j < roomRow; j++) {
                    JSONObject temp = columns.getJSONObject(j);
                    rooms[i][j] = new Room(temp);
                }
            }
        }
        else {
            generateHouse();
        }
        return rooms;
    }

    /**
     * Save the house layout to a file
     */
    public static void saveHouseLayout() {
        JSONObject houseLayout = new JSONObject();

        JSONArray rows = new JSONArray();

        for(int i = 0; i < roomColumn; i++) {
            JSONArray column = new JSONArray();
            for(int j = 0; j < roomRow; j++) {
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

    public static boolean fileExist() {
        File houseLayout = new File(houseLayoutFileName+".json");
        return houseLayout.exists();
    }

}
