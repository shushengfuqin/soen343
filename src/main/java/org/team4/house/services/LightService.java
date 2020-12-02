package org.team4.house.services;

import org.team4.common.Coordinate;
import org.team4.common.Helper;
import org.team4.common.Settings;
import org.team4.common.logger.Logger;
import org.team4.house.HouseService;
import org.team4.house.components.Room;
import org.team4.permissions.Permission;
import org.team4.user.UserService;

import java.util.ArrayList;

public class LightService {
    private HouseService houseService;
    private UserService userService;

    public LightService() {
        houseService = new HouseService();
        userService = new UserService();
    }

    /**
     * Turns on all away mode lights
     */
    public void turnOnAllAwayModeLights() {
        Room[][] rooms = houseService.getRooms();
        for(Coordinate c : houseService.house.lightsAway) {
            rooms[c.x][c.y].lightOn = true;
        }
    }

    /**
     * Check if a light is on or off
     * @param s location of light
     * @return a boolean
     */
    public boolean getLightStatus(String s) {
        Room[][] rooms = houseService.getRooms();
        Coordinate lightLocation = new Coordinate(s);
        return rooms[lightLocation.x][lightLocation.y].lightOn;
    }

    /**
     * Check if the light is in away list
     * @param s location of light
     * @return boolean
     */
    public boolean getLightAwayStatus(String s) {
        Coordinate lightLocation = new Coordinate(s);
        boolean InArray = Helper.coordInArrayList(houseService.house.lightsAway, lightLocation);
        return InArray;
    }

    /**
     * turn on all lights in the house
     */
    public void turnOnAllLights() {
        Room[][] rooms = houseService.getRooms();
        for(Coordinate coord : houseService.house.lights) {
            rooms[coord.x][coord.y].lightOn = true;
        }
    }

    /**
     * turn off all lights in the house
     */
    public void turnOffAllLights() {
        Room[][] rooms = houseService.getRooms();
        for(Coordinate coord : houseService.house.lights) {
            rooms[coord.x][coord.y].lightOn = false;
        }
    }

    /**
     * Turn all lights in rooms containing users
     * turn off the other ones
     */
    public void turnOnAllLightsWithUsers() {
        Room[][] rooms = houseService.getRooms();
        for(Coordinate coord : houseService.house.lights) {
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
    public void toggleLightAuto() {
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
    public void toggleLights(String c) {
        Room[][] rooms = houseService.getRooms();

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
    public void toggleLightsAway(String c) {
        Coordinate coord = new Coordinate(c);
        boolean InArray = Helper.coordInArrayList(houseService.house.lightsAway, coord);
        if(InArray){
            for(Coordinate coordinate: houseService.house.lightsAway) {
                if(coordinate.x == coord.x && coordinate.y == coord.y)
                    coord = coordinate;
            }
            houseService.house.lightsAway.remove(coord);
            Logger.info("Light removed to light away list. Coordinate: " + coord.toString());
        }
        else {
            houseService.house.lightsAway.add(coord);
            Logger.info("Light added to light away list. Coordinate: " + coord.toString());
        }
    }

    /**
     * Get all the lights in the house
     * @return
     */
    public String[] getAllLightsOption(){
        String[] lightOption = new String[houseService.house.lights.size()];
        for(int i = 0; i < houseService.house.lights.size(); i++) {
            Coordinate tempLight = houseService.house.lights.get(i);
            lightOption[i] = tempLight.toString();
        }
        return lightOption;
    }
}
