package org.team4.shsParameters;

import org.team4.common.Settings;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.stream.Stream;

public class ShsParameterService {

    public ShsParameterService() {
    }

    public boolean setDateTime(LocalDate localDate, String timeInput) {

        if (localDate == null || timeInput == null)
            return false;
        if (!timeInput.matches("^(?:(?:([01]?\\d|2[0-3]):)?([0-5]?\\d):)?([0-5]?\\d)$"))
            return false;

        int[] time = Stream.of(timeInput.split(":")).mapToInt(Integer::parseInt).toArray();
        Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
        Date date = Date.from(instant);
        date.setHours(time[0]);
        date.setMinutes(time[1]);
        date.setSeconds(time[2]);
        Settings.simulationTime.setDateTime(date);
        return true;
   }
}
