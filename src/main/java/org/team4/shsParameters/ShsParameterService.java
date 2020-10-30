package org.team4.shsParameters;

import org.team4.common.Settings;

import java.time.LocalDate;
import java.util.Calendar;
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

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, localDate.getYear());
        calendar.set(Calendar.MONTH, localDate.getMonthValue());
        calendar.set(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
        calendar.set(Calendar.HOUR, time[0]);
        calendar.set(Calendar.MINUTE, time[1]);
        calendar.set(Calendar.SECOND, time[2]);
        Settings.simulationTime.setDateTime(calendar);
        return true;
   }
}
