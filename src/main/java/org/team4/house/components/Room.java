package org.team4.house.components;

import org.json.JSONObject;
import org.team4.common.Settings;

public class Room {
    public String name;
    public String zone;
    public boolean tempOverridden;
    public double desiredTemp;
    public double currentTemp;
    public boolean airConditioning = false;
    public boolean heater = false;

    //Left wall = 0 ; Top wall = 1 ; Right wall = 2 ; Bottom wall = 3;
    public Wall[] walls;

    public boolean lightOn;
    // #1. constructor
    public Room(String name, String lw, String tw, String rw, String bw)
    {
        walls = new Wall[4];
        this.name = name;
        this.tempOverridden = false;
        this.currentTemp = Settings.defaultTemp;
        this.desiredTemp = Settings.defaultTemp;
        this.zone = name.equals("outside") || name.equals("backyard") ? null : "default";
        walls[0] = new Wall(lw);
        walls[1] = new Wall(tw);
        walls[2] = new Wall(rw);
        walls[3] = new Wall(bw);
        this.lightOn = true;
    }

    // #2. constructor which accepts the room from the house layout.
    public Room(JSONObject jo) {
        walls = new Wall[4];
        this.tempOverridden = false;
        this.currentTemp = Settings.defaultTemp;
        this.desiredTemp = Settings.defaultTemp;
        this.zone = "default";
        this.name = jo.getString("name");

        String leftWallType = jo.getString("leftWall");
        walls[0] = new Wall(leftWallType);

        String topWallType = jo.getString("topWall");
        walls[1] = new Wall(topWallType);

        String rightWallType = jo.getString("rightWall");
        walls[2] = new Wall(rightWallType);

        String botWallType = jo.getString("botWall");
        walls[3] = new Wall(botWallType);
        this.lightOn = true;
    }

    /**
     * Maps an index to a wall side
     * @param index
     * @return a string indicating the side of the wall
     */
    public static String wallSideMapper(int index) {
        switch (index) {
            case 0:
                return "left";
            case 1:
                return "top";
            case 2:
                return "right";
            case 3:
                return "bot";
        }
        return null;
    }

    /**
     * Maps a string side to an index
     * @param side
     * @return an index indicating the side of the wall
     */
    public static int wallSideMapper(String side) {
        switch (side) {
            case "left":
                return 0;
            case "top":
                return 1;
            case "right":
                return 2;
            case "bot":
                return 3;
        }
        return -1;
    }

    public String toString() {
        return "Name: " + name;
    }

    /**
     * Create a json object from the room object
     * @return a json object representing the room
     */
    public JSONObject toJson() {
        JSONObject jo = new JSONObject();
        jo.put("name", this.name);
        jo.put("leftWall", this.walls[0].type);
        jo.put("topWall", this.walls[1].type);
        jo.put("rightWall", this.walls[2].type);
        jo.put("botWall", this.walls[3].type);
        return jo;
    }


}
