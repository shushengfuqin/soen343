package org.team4.hvac;

import org.team4.common.Coordinate;
import org.team4.common.Helper;
import org.team4.common.Settings;
import org.team4.common.SimulationClock;
import org.team4.common.logger.Logger;
import org.team4.house.House;
import org.team4.house.components.Room;
import org.team4.shhParameters.Zone;
import org.team4.shhParameters.ZoneService;

import java.util.Date;

public class TemperatureService {
    private ZoneService zoneService;

    public TemperatureService(){
        zoneService = new ZoneService();
    }

    public void updateTemperature(){
        for(Coordinate coord : House.indoorRooms){
            Room currentRoom = House.rooms[coord.x][coord.y];
            double currentTemp = currentRoom.currentTemp;
            if(currentTemp < Settings.tempAlertLowerBound || currentTemp > Settings.tempAlertUpperBound)
                Logger.warning("The temperature in room " + coord + " is unusual");

            Date date = Settings.simulationTime.getDate();
            double desiredTemp = getDesiredTemp(currentRoom, date);
            updateRoomTemp(coord.x, coord.y , desiredTemp, currentTemp, date, currentRoom);
        }
    }

    public double getDesiredTemp(Room room, Date date) {
        //return the overwritten desired temperature
        if(room.tempOverWritten) return room.desiredTemp;

        //return the seasonal temperature
        if(Settings.awayMode) return seasonIsSummer(date) ? Settings.summerTemperature : Settings.winterTemperature;

        //return the zone desired temperature
        String zoneName = room.zone;
        Zone currentZone = zoneService.getZone(zoneName);

        if(currentZone.timePeriod3 != null && SimulationClock.isBettweenTime(date,currentZone.timePeriod3.begin, currentZone.timePeriod3.end)){
            return currentZone.timePeriod3.desiredTemperature;
        }

        if(currentZone.timePeriod2 != null && SimulationClock.isBettweenTime(date,currentZone.timePeriod2.begin, currentZone.timePeriod2.end)){
            return currentZone.timePeriod2.desiredTemperature;
        }
        if(currentZone.timePeriod1 != null && SimulationClock.isBettweenTime(date,currentZone.timePeriod1.begin, currentZone.timePeriod1.end)){
            return currentZone.timePeriod1.desiredTemperature;
        }

        return currentZone.defaultTemp;
    }

    public void updateRoomTemp(int x, int y, double desiredTemp, double currentTemp, Date date, Room room){
        double outsideTemp = Settings.outsideTemperature;
        double tempDiff = Math.abs(desiredTemp - currentTemp);
        boolean canOpenWindow = openWindowOrNot(x, y, date);
        boolean containWindow = House.hasWindow(x, y);
        boolean windowsBlocked = House.checkWindowBlock(x, y);
        boolean heaterOn = false;
        boolean airConditioningOn = false;
        boolean windowOpened = false;
        double newTemp = room.currentTemp;

        if(tempDiff <= 0.25){
            if(currentTemp > outsideTemp) newTemp = Helper.roundToTwoDecimal(currentTemp-0.05);
            if (currentTemp < outsideTemp) newTemp = Helper.roundToTwoDecimal(currentTemp+0.05);
        }
        else if (desiredTemp > currentTemp){
            if(canOpenWindow && containWindow && Settings.outsideTemperature > currentTemp){
                if(!windowsBlocked) windowOpened = true;
                else {
                    Logger.warning("Unable to open window for HVAC in room (" + x + ", " + y + ")");
                    heaterOn = true;
                }
            } else {
                heaterOn = true;
            }
            newTemp = Helper.roundToTwoDecimal(currentTemp + 0.1);
        }
        else if (desiredTemp < currentTemp){
            if(canOpenWindow && containWindow && Settings.outsideTemperature < currentTemp){
                if(!windowsBlocked) windowOpened = true;
                else {
                    Logger.warning("Unable to open window for HVAC in room (" + x + ", " + y + ")");
                    airConditioningOn = true;
                }
            } else {
                airConditioningOn = true;
            }
            newTemp = Helper.roundToTwoDecimal(currentTemp - 0.1);
        }

        room.airConditioning = airConditioningOn;
        room.heater = heaterOn;
        room.currentTemp = newTemp;
        House.toggleWindowsInRoom(x, y, windowOpened);
    }

    public boolean seasonIsSummer(Date date) {
        return date.getMonth()+1 >= Settings.summerBegin && date.getMonth()+1 <= Settings.summerEnd;
    }

    public boolean openWindowOrNot(int x, int y, Date date){
        if (Settings.awayMode) return false;
        if(seasonIsSummer(date)) return true;
        return false;
    }
}
