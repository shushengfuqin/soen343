package org.team4.SHP;

import java.util.Calendar;

public class LightPolicy {
    private String lightName;
    private Calendar startAwayModeTime;
    private Calendar stopAwayModeTime;

    public LightPolicy(String lightName) {
        this.lightName = lightName;
    }

    public LightPolicy(String lightName, Calendar startAwayModeTime, Calendar stopAwayModeTime) {
        this.lightName = lightName;
        this.startAwayModeTime = startAwayModeTime;
        this.stopAwayModeTime = stopAwayModeTime;
    }

    public Calendar getStartAwayModeTime() {
        return startAwayModeTime;
    }

    public void setStartAwayModeTime(Calendar startAwayModeTime) {
        this.startAwayModeTime = startAwayModeTime;
    }

    public Calendar getStopAwayModeTime() {
        return stopAwayModeTime;
    }

    public void setStopAwayModeTime(Calendar stopAwayModeTime) {
        this.stopAwayModeTime = stopAwayModeTime;
    }
}
