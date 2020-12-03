package org.team4.common;

import java.util.Date;

public class Settings {
    public static boolean logging = true;
    public static Double defaultTemp;
    public static Integer summerBegin;
    public static Integer summerEnd;
    public static String currentUser;
    public static int outsideTemperature = 20;
    public static SimulationClock simulationTime = new SimulationClock();
    public static LayoutUpdater layoutUpdater = new LayoutUpdater();
    public static boolean simulationStarted = false;
    public static boolean lightAutoMode = false;
    public static boolean awayMode = false;
    public static int timeBeforeAlerting = 300;
    public static boolean startTimeBeforeAlerting = false;
    public static Date awayLightOnTime = null;
    public static Date awayLightOffTime = null;
    public static boolean awayLightOn = false;
    public static double tempAlertUpperBound = 100;
    public static double tempAlertLowerBound = 0;
    public static double summerTemperature = 20;
    public static double winterTemperature = 20;

    /**
     * Reset the settings to default values
     */
    public static void resetSettings() {
        currentUser = null;
        outsideTemperature = 20;
        stopClock();
        simulationStarted = false;
        lightAutoMode = false;
        awayMode = false;
        timeBeforeAlerting = 300;
        startTimeBeforeAlerting = false;
        awayLightOnTime = null;
        awayLightOffTime = null;
        awayLightOn = false;
        tempAlertUpperBound = 100;
        tempAlertLowerBound = 0;
        summerTemperature = 20;
        winterTemperature = 20;
    }

    /**
     * Start the clock
     */
    public static void startClock() {
        simulationTime.stop = false;
        simulationTime.start();
        layoutUpdater.stop = false;
        layoutUpdater.start();
    }

    /**
     * Stop the clock
     */
    public static void stopClock() {
        simulationTime.stop = true;
        simulationTime = new SimulationClock();
        layoutUpdater.stop = true;
        layoutUpdater = new LayoutUpdater();
    }
}
