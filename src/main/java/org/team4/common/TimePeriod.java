package org.team4.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimePeriod {
    public Date begin;
    public Date end;
    public double desiredTemperature;

    /**
     * Constructor
     * @param begin time of the period
     * @param end time of the period
     * @param temp desired temperature during that period
     */
    public TimePeriod(Date begin, Date end, double temp) {
        this.begin = begin;
        this.end = end;
        this.desiredTemperature = temp;
    }

    /**
     * Get the string format of a time period
     * @return a string
     */
    public String toString() {
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        String b = timeFormat.format(begin);
        String e = timeFormat.format(end);
        String temp = Double.toString(desiredTemperature);

        return b + "-" + e + "-" + temp + "°C";
    }
}
