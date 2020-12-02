package org.team4.house.components;

import org.json.JSONArray;
import org.json.JSONObject;
import org.team4.common.Coordinate;
import org.team4.common.Settings;
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
    public ArrayList<int[]> windows = new ArrayList<int[]>();
    public ArrayList<int[]> doors = new ArrayList<int[]>();
    public ArrayList<Coordinate> lights = new ArrayList<>();
    public ArrayList<Coordinate> lightsAway = new ArrayList<>();
    public ArrayList<Coordinate> indoorRooms = new ArrayList<>();
    public ArrayList<int[]> lockDoor = new ArrayList<int[]>();
    public int roomColumn = 5;
    public int roomRow = 5;
    public Room[][] rooms  = new Room[roomColumn][roomRow];

    /**
     * Reset the house Layout
     */
    public void resetParams() {
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
     * Get the location of every door and window in the house layout
     */
    public void indexHouseWindowAndDoor() {
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
    public void indexAllLights() {
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
    public void indexAllIndoorRooms() {
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
     * check adjacent room if it outside
     * i column, j row.
     */
    public void indexLockDoor(int i,int j,int k){
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
     * Randomly generate a house layout
     */
    public void generateHouse() {
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

    public void cleanUpHouseLayout() {
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
    public Room[][] getHouseLayout() {
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
    public void saveHouseLayout() {
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
    public String readFromHouseFile() {
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
    public boolean writeToHouseFile(String s) {
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

    public boolean fileExist() {
        File houseLayout = new File(houseLayoutFileName+".json");
        return houseLayout.exists();
    }
}
