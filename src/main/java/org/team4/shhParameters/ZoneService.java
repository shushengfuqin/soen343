package org.team4.shhParameters;

import org.team4.common.Coordinate;
import org.team4.common.Settings;
import org.team4.common.TimePeriod;
import org.team4.common.logger.Logger;
import org.team4.exceptionClass.InvalidTimeEntryException;
import org.team4.house.HouseService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.stream.Stream;

public class ZoneService {
    public static HouseZones houseZones= new HouseZones();
    public HouseService houseService;

    /**
     * Constructor
     */
    public ZoneService() {
        houseService = new HouseService();
    }

    /**
     * Check if a string of time is valid
     * @param time
     * @return date if valid else null
     */
    public Date validateTimeEntry(String time) {
        try {
            if (!time.matches("^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$"))
                throw new InvalidTimeEntryException();
        }
        catch (InvalidTimeEntryException e){
            e.printStackTrace();
        }
        int[] newTime = Stream.of(time.split(":")).mapToInt(Integer::parseInt).toArray();
        Date date = new Date();
        date.setHours(newTime[0]);
        date.setMinutes(newTime[1]);
        date.setSeconds(newTime[2]);
        return date;
    }

    /**
     * Set the time period 1 of a zone
     * @param name of the zone
     * @param begin time of period
     * @param end time of period
     * @param temp temperature
     * @return a boolean if success or not
     */
    public boolean setTimePeriod1(String name, Date begin, Date end, double temp) {
        Logger.info("Time period 1 has been updated");
        return setTimePeriod(1, name, begin, end, temp);
    }

    /**
     * Set the time period 2 of a zone
     * @param name of the zone
     * @param begin time of period
     * @param end time of period
     * @param temp temperature
     * @return a boolean if success or not
     */
    public boolean setTimePeriod2(String name, Date begin, Date end, double temp) {
        Logger.info("Time period 2 has been updated");
        return setTimePeriod(2, name, begin, end, temp);
    }

    /**
     * Set the time period 3 of a zone
     * @param name of the zone
     * @param begin time of period
     * @param end time of period
     * @param temp temperature
     * @return a boolean if success or not
     */
    public boolean setTimePeriod3(String name, Date begin, Date end, double temp) {
        Logger.info("Time period 3 has been updated");
        return setTimePeriod(3, name, begin, end, temp);
    }

    /**
     * Set the time period of a zone
     * @param name of the zone
     * @param begin time of period
     * @param end time of period
     * @param temp temperature
     * @return a boolean if success or not
     */
    public boolean setTimePeriod(int id, String name, Date begin, Date end, double temp) {
        TimePeriod timePeriod = new TimePeriod(begin, end, temp);
        return houseZones.updateTimePeriod(id, name, timePeriod);
    }

    /**
     * Returns a zone
     * @param name of the zone
     * @return zone object
     */
    public Zone getZone(String name) {
        return houseZones.getHouseZone(name);
    }

    /**
     * Edit the temperature of a zone
     * @param name of the zone
     * @param temp temperature
     */
    public void editZoneTemperature(String name, double temp) {
        Logger.info("Zone temperature has been updated");
        houseZones.setDefaultTemp(name, temp);
    }

    /**
     * gets the set of all zones
     * @return set of all zones
     */
    public HashSet<Zone> getAllZones() {
        return houseZones.allZones;
    }

    /**
     * verify if a zone name is valid
     * @param name of a zone
     * @return a boolean
     */
    public boolean verifyName(String name) {
        return !houseZones.zoneExist(name);
    }

    /**
     * Verify if the temperature of a zone is valid
     * @param temp of a zone
     * @return a boolean
     */
    public Double verifyTemp(String temp) {
        try {
            return Double.parseDouble(temp);
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Add a new zone to the set of zones
     * @param name
     * @param temp
     */
    public void addNewZone(String name, double temp) {
        houseZones.addZone(name, temp);
        Logger.info("New zone has been added");
    }

    /**
     * Delete a zone
     * @param name
     * @return a boolean if success or not
     */
    public boolean deleteZone(String name) {
        boolean success = houseZones.removeZone(name);
        if(success){
            Logger.info("Zone has been deleted successfully");
        }
        else {
            Logger.error("Failed to delete zone");
        }
        return success;
    }

    /**
     * Get a list of all indoor rooms
     * @return return array of indoor rooms
     */
    public ArrayList<Coordinate> getAllIndoorRooms() {
        return houseService.house.indoorRooms;
    }

    /**
     * set a zone to a room
     * @param zone name
     * @param x coordinate of room
     * @param y coordinate of room
     */
    public void setRoomZone(String zone, int x, int y) {
        houseService.house.rooms[x][y].zone = zone;
        Logger.info("Room has been added to zone");
    }

    /**
     * Get the room temperature
     * @param coord of a room
     */
    public void requestRoomTemperature(Coordinate coord) {
        Double currentTemp = houseService.house.rooms[coord.x][coord.y].currentTemp;
        Logger.info("As requested: Current temp of room " + coord + " is " + currentTemp + " °C");
    }

    /**
     * Overwrite the temperature of a room
     * @param coord of room
     * @param temp new temp
     */
    public void overwriteTemperature(Coordinate coord, double temp) {
        houseService.house.rooms[coord.x][coord.y].desiredTemp = temp;
        houseService.house.rooms[coord.x][coord.y].tempOverWritten = true;
        Logger.info("Room temperature has been overwritten");
    }

    /**
     * Set the default summer and winter temperature
     * @param summer temperature
     * @param winter winter temperature
     */
    public void setSeasonalTemperature(double summer, double winter) {
        Settings.summerTemperature = summer;
        Settings.winterTemperature = winter;
        Logger.info("Seasonal temperature have been updated");
    }

    /**
     * Set temperature alert thresholds
     * @param upper bound
     * @param lower lower
     */
    public void setTemperatureAlertThreshold(double upper, double lower) {
        Settings.tempAlertUpperBound = upper;
        Settings.tempAlertLowerBound = lower;
        Logger.info("Alert temperature thresholds have been updated");
    }
}
