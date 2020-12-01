package org.team4.house;

import org.team4.common.Settings;
import org.team4.common.SimulationClock;
import org.team4.house.components.Room;
import org.team4.shhParameters.Zone;
import org.team4.shhParameters.ZoneService;

import java.util.Date;

import static org.team4.house.House.*;

public class TemperatureService {
    private ZoneService zoneService;

    public TemperatureService(){
        zoneService = new ZoneService();
    }

    public void updateTemperature(){
        Room[][] rooms = House.rooms;
        for(int i = 0; i < roomColumn; i++){
            for(int j = 0; j < roomRow; j++){
                double currentTemp = House.rooms[i][j].currentTemp;
                Room currentRoom = House.rooms[i][j];
                double desiredTemp = 20;
                Date date = new Date();
                if(currentRoom.tempOverWritten){
                    continue;
                }

                //set the desired temp
                if (Settings.awayMode){
                    //check season,
                    if(date.getMonth() >= Settings.summerBegin && date.getMonth() <= Settings.summerEnd){
                        //desiredTemp for summer
                        desiredTemp = 30;
                    } else {
                        //desiredTemp for winter
                        desiredTemp = 10;
                    }
                } else {
                    String zoneName = currentRoom.zone;
                    Zone currentZone = zoneService.getZone(zoneName);
                    //default zonetemp
                    desiredTemp = currentZone.defaultTemp;
                    //check
                    if(currentZone.timePeriod1 != null){
                        if(SimulationClock.isBettweenTime(date,currentZone.timePeriod1.begin, currentZone.timePeriod1.end)){
                            desiredTemp = currentZone.timePeriod1.desiredTemperature;
                        }
                    }
                    if(currentZone.timePeriod2 != null){
                        if(SimulationClock.isBettweenTime(date,currentZone.timePeriod2.begin, currentZone.timePeriod2.end)){
                            desiredTemp = currentZone.timePeriod2.desiredTemperature;
                        }
                    }
                    if(currentZone.timePeriod3 != null){
                        if(SimulationClock.isBettweenTime(date,currentZone.timePeriod3.begin, currentZone.timePeriod3.end)){
                            desiredTemp = currentZone.timePeriod3.desiredTemperature;
                        }
                    }
                }
                System.out.println(desiredTemp);
                updateRoomTemp(i, j , desiredTemp, currentTemp);
            }
        }
    }

    //compare the desiredTemp and the currentTemp
    public void updateRoomTemp(int x, int y, double desiredTemp, double currentTemp){
        double outsideTemp = Settings.outsideTemperature;
        double tempDif = Math.abs(desiredTemp - currentTemp);

        if(tempDif <= 0.25){
            House.rooms[x][y].airConditioning = false;
            House.rooms[x][y].heater = false;
            currentTempVSoutSideTemp(x, y, currentTemp,outsideTemp);
            return;
        }
        if (desiredTemp > currentTemp){
            House.rooms[x][y].heater = true;
            House.rooms[x][y].airConditioning = false;
            House.closeAllWindowInRoom(x,y);
            House.rooms[x][y].currentTemp += 0.1;
        }
        if (desiredTemp < currentTemp){
            if(openWindowOrNot(x,y)){
                House.rooms[x][y].heater = false;
                House.rooms[x][y].airConditioning = false;
                House.openAllWindowInRoom(x,y);
                House.rooms[x][y].currentTemp -= 0.1;
            } else {
                House.rooms[x][y].heater = false;
                House.rooms[x][y].airConditioning = true;
                House.closeAllWindowInRoom(x,y);
                House.rooms[x][y].currentTemp -= 0.1;
            }
        }
    }


    public void currentTempVSoutSideTemp(int x, int y, double currentTemp, double outsideTemp){
        if(currentTemp > outsideTemp){
            House.rooms[x][y].currentTemp -= 0.05;
        } else if (currentTemp < outsideTemp){
            House.rooms[x][y].currentTemp += 0.05;
        }
    }

    //check season
    public boolean openWindowOrNot(int x, int y){
        Date date = new Date();
        if (Settings.awayMode){
            return false;
        }
        if(date.getMonth() >= Settings.summerBegin && date.getMonth() <= Settings.summerEnd){
            if(House.checkWindowBlock(x, y)){
                return false;
            }
            return true;
        } else {
            return false;
        }
    }
    //get current season

    //get current zone time period.
}
