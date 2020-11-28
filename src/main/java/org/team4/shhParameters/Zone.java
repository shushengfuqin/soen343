package org.team4.shhParameters;

import org.team4.common.TimePeriod;

public class Zone {
    public String name;
    public double defaultTemp;
    public TimePeriod timePeriod1;
    public TimePeriod timePeriod2;
    public TimePeriod timePeriod3;

    /**
     * Constructor
     * @param name of the zone
     * @param desiredTemp of the zone
     */
    public Zone(String name, double desiredTemp) {
        this.name = name;
        this.defaultTemp = desiredTemp;
        this.timePeriod1 = null;
        this.timePeriod2 = null;
        this.timePeriod3 = null;
    }

    /**
     * Set the time period
     * @param time time period object
     */
    public void setTimePeriod1(TimePeriod time) {
        this.timePeriod1 = time;
    }

    /**
     * Set the time period
     * @param time time period object
     */
    public void setTimePeriod2(TimePeriod time) {
        this.timePeriod2 = time;
    }

    /**
     * Set the time period
     * @param time time period object
     */
    public void setTimePeriod3(TimePeriod time) {
        this.timePeriod3 = time;
    }

    /**
     * Get name of the zone
     * @return name of the zone
     */
    public String getName() {
        return name;
    }

    /**
     * Get default temp
     * @return default temp as string
     */
    public String getDefaultTemp() {
        return (defaultTemp) + "°C";
    }

    /**
     * Get time period 1
     * @return time period 1 as a string
     */
    public String getTimePeriod1() {
        if(timePeriod1 != null) return  timePeriod1.toString();
        return "None";
    }

    /**
     * Get time period 2
     * @return time period 2 as a string
     */
    public String getTimePeriod2() {
        if(timePeriod2 != null) return  timePeriod2.toString();
        return "None";
    }

    /**
     * Get time period 3
     * @return time period 3 as a string
     */
    public String getTimePeriod3() {
        if(timePeriod3 != null) return  timePeriod3.toString();
        return "None";
    }
}
