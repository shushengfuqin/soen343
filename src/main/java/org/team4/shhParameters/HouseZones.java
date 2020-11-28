package org.team4.shhParameters;

import org.team4.common.TimePeriod;
import org.team4.house.House;
import org.team4.house.components.Room;

import java.util.HashSet;

public class HouseZones {
    public HashSet<Zone> allZones;

    /**
     * constructor
     */
    public HouseZones() {
        this.allZones = new HashSet<>();
        addZone("default", 20);
    }

    /**
     * Check if a zone exists
     * @param name
     * @return boolean if zone exist or not
     */
    public boolean zoneExist(String name) {
        for(Zone zone : allZones) {
            if(zone.name.equals(name)) return true;
        }
        return false;
    }

    /**
     * Add a new zone to house
     * @param name of the zone
     * @param desiredTemp of the zone
     * @return boolean if success or not
     */
    public boolean addZone(String name, double desiredTemp) {
        if(zoneExist(name)) return false;

        Zone newZone = new Zone(name, desiredTemp);
        allZones.add(newZone);
        return true;
    }

    /**
     * Delete a zone
     * @param name name of zone
     * @return boolean if success or not
     */
    public boolean removeZone(String name) {
        if(!zoneExist(name)) return true;

        //Check if there's a room in the zone
        Room[][] rooms = House.rooms;
        for(int i = 0; i < House.roomColumn; i++) {
            for (int j = 0; j < House.roomRow; j++) {
                if(rooms[i][j].zone.equals(name)) {
                    return false;
                }
            }
        }

        Zone zoneTobeDeleted = null;
        for(Zone zone : allZones) {
            if(zone.name.equals(name)) {
                zoneTobeDeleted = zone;
                break;
            }
        }
        allZones.remove(zoneTobeDeleted);
        return true;
    }

    /**
     * Return a zone in the house
     * @param name of the zone
     * @return zone object
     */
    public Zone getHouseZone(String name) {
        for(Zone zone : allZones) {
            if(zone.name.equals(name)) return zone;
        }
        return null;
    }

    /**
     * Update the time period of zone
     * @param id of time period
     * @param name of zone
     * @param timePeriod new time period
     * @return boolean if success or not
     */
    public boolean updateTimePeriod(int id, String name, TimePeriod timePeriod) {
        for(Zone zone : allZones) {
            if(zone.name.equals(name)) {
                switch (id) {
                    case 1:
                        zone.setTimePeriod1(timePeriod);
                        return true;
                    case 2:
                        zone.setTimePeriod2(timePeriod);
                        return true;
                    case 3:
                        zone.setTimePeriod3(timePeriod);
                        return true;
                }
                break;
            }
        }
        return false;
    }

    /**
     * Get the time period of a zone
     * @param name zone name
     * @param id of time period
     * @return TimePeriod object
     */
    public TimePeriod getTimePeriod(String name, int id) {
        for(Zone zone : allZones) {
            if(zone.name.equals(name)) {
                switch (id) {
                    case 1:
                        return zone.timePeriod1;
                    case 2:
                        return zone.timePeriod2;
                    case 3:
                        return zone.timePeriod3;
                }
                break;
            }
        }
        return null;
    }

    /**
     * Set the default temperature of a zone
     * @param name of the zone
     * @param temp new temperature
     */
    public void setDefaultTemp(String name, double temp) {
        for(Zone zone : allZones) {
            if(zone.name.equals(name)) {
                zone.defaultTemp = temp;
                break;
            }
        }
    }
}
