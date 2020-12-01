package org.team4.common;

import org.team4.App;
import org.team4.common.logger.Logger;
import org.team4.dashboard.DashboardController;
import org.team4.house.services.LightService;
import org.team4.house.services.TemperatureService;

import java.util.Calendar;
import java.util.Date;

public class SimulationClock extends Thread{
    private Date date = new Date();
    private LightService lightService = new LightService();
    private boolean setting = false;
    public boolean stop = false;
    public int multiplier = 1;

    public void run(){
        Date dt;
        int timeBeforeAlert = Settings.timeBeforeAlerting;
        DashboardController dashboardController = App.fxmlLoader.getController();
        TemperatureService temperatureService = new TemperatureService();
        while(!stop){
            try {
                sleep(1000/multiplier);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!Settings.awayMode) timeBeforeAlert = Settings.timeBeforeAlerting;

            if(Settings.awayMode) {
                if(Settings.startTimeBeforeAlerting)
                    timeBeforeAlert -= 1;
                if(Settings.awayLightOnTime != null && Settings.awayLightOffTime != null)
                    toggleAwayModeLights();
            }
            if(timeBeforeAlert <= 0) {
                Settings.startTimeBeforeAlerting = false;
                Logger.error(Settings.timeBeforeAlerting + " seconds have passed, Authorities have been alerted.");
                timeBeforeAlert = Settings.timeBeforeAlerting;
            }

            Calendar c = Calendar.getInstance();
            c.setTime(this.date);
            c.add(Calendar.SECOND, 1);
            dt = c.getTime();
            setDateTime(dt);

            temperatureService.updateTemperature();
            dashboardController.updateTime(getDate());
            dashboardController.drawHouseLayout();
        }
    }

    /**
     * Automatically turn on and of light in away mode
     */
    public void toggleAwayModeLights() {
        Date curr = getDate();
        Date on = Settings.awayLightOnTime;
        Date off = Settings.awayLightOffTime;

        Boolean targetInZone = isBettweenTime(curr, on, off);

        if(targetInZone && !Settings.awayLightOn) {
            lightService.turnOnAllAwayModeLights();
            Logger.info("All away mode lights have been turned on");
            Settings.awayLightOn = true;
        }
        else if (!targetInZone && Settings.awayLightOn) {
            lightService.turnOffAllLights();
            Logger.info("All away mode lights have been turned off");
            Settings.awayLightOn = false;
        }

    }

    /**
     * Set the clock speed multiplier
     * @param m int multiplier
     */
    public synchronized void setMultiplier(int m) {
        setting = true;
        this.multiplier = m;
        setting = false;
        notifyAll();
    }

    /**
     * Gets the current date
     * @return date
     */
    public synchronized Date getDate(){
        while(setting){
            try {
                wait();
            } catch (InterruptedException e) {  }
        }
        return this.date;
    }

    /**
     * Set the current date
     * @param dt new date
     */
    public synchronized void setDateTime(Date dt){
        setting = true;
        this.date = dt;
        setting = false;
        notifyAll();
    }

    /**
     * Check if the time is between two other times
     * @param argCurr current time
     * @param argOn on time
     * @param argOff off time
     * @return a boolean
     */
    public static boolean isBettweenTime(Date argCurr, Date argOn, Date argOff) {
        boolean valid = false;

        Date curr = (Date) argCurr.clone();
        Date on  = (Date) argCurr.clone();
        on.setHours(argOn.getHours());
        on.setMinutes(argOn.getMinutes());
        on.setSeconds(argOn.getSeconds());
        Date off  = (Date) argCurr.clone();
        off.setHours(argOff.getHours());
        off.setMinutes(argOff.getMinutes());
        off.setSeconds(argOff.getSeconds());


        // Current Time
        Calendar currentCalendar = Calendar.getInstance();
        currentCalendar.setTime(curr);

        // On Time
        Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(on);

        // Off Time
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(off);

        if (curr.compareTo(off) < 0) {
            currentCalendar.add(Calendar.DATE, 1);
            curr = currentCalendar.getTime();
        }

        if (on.compareTo(off) < 0) {

            startCalendar.add(Calendar.DATE, 1);
            on = startCalendar.getTime();

        }

        if (curr.before(on)) {
            valid = false;
        }

        else {
            if (curr.after(off)) {
                endCalendar.add(Calendar.DATE, 1);
                off = endCalendar.getTime();
            }

            if (curr.before(off)) {
                valid = true;
            } else {
                valid = false;
            }
        }

        return valid;
    }
}