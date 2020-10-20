package org.team4.shsParameters;

import org.team4.common.Settings;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ShsParameterService {

    /**
     * Set the new date of the simulation
     * @param date
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public boolean setDate(Date date, int hours, int minutes, int seconds) {
        date.setHours(hours);
        date.setMinutes(minutes);
        date.setSeconds(seconds);
        Settings.simulationTime.setDateTime(date);
        return true;
    }

    /**
     * Set the new time of the simulation
     * @param localDate
     * @param time
     * @return
     */
    public boolean setDateTime(LocalDate localDate, String time) {
        if(time == null || localDate == null) {
            return false;
        }

        String[] timeArr = time.split(":");
        int[] timeIntArr = new int[3];
        try {
            if(timeArr.length == 3) {
                timeIntArr[0] = Integer.parseInt(timeArr[0]);

                if (timeIntArr[0] > 24 || timeIntArr[0] < 0) throw new NumberFormatException();

                timeIntArr[1] = Integer.parseInt(timeArr[1]);
                if (timeIntArr[1] > 60 || timeIntArr[1] < 0) throw new NumberFormatException();

                timeIntArr[2] = Integer.parseInt(timeArr[2]);
                if (timeIntArr[2] > 60 || timeIntArr[2] < 0) throw new NumberFormatException();

                Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
                Date date = Date.from(instant);
                setDate(date, timeIntArr[0], timeIntArr[1], timeIntArr[2]);
                return true;
            }
        }
        catch (NumberFormatException e) {
            e.printStackTrace();
            System.out.println("Invalid time format");
        }
        return false;
    }
}
