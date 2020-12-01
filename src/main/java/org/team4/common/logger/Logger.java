package org.team4.common.logger;

import javafx.application.Platform;
import org.team4.App;
import org.team4.common.Settings;
import org.team4.common.logException;
import org.team4.dashboard.DashboardController;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class Logger {
    public static String fileName = "output.jsonl";


    /**
     * Creates a log, saves it to a file and display it in the output box
     * @param message of the log
     * @param level severity level
     * @param date current date/time
     * @param user current user
     */
    public static boolean log(String message, String level, Date date, String user) throws logException {
        Log newLog = new Log(message, level, date, user);
        writeToLogFile(newLog);
        Platform.runLater(
                () -> {
                    DashboardController dashboardController = App.fxmlLoader.getController();
                    dashboardController.displayLog(newLog);
                }
        );
        return true;
    }

    /**
     * Generate a log level info
     * @param message
     */
    public static boolean info(String message) throws logException {
        String user = Settings.currentUser;
        Date date = Settings.simulationTime.getDate();
        String level = "info";
        return log(message, level, date, user);
    }

    /**
     * Generate a log level warning
     * @param message
     */
    public static boolean warning(String message) throws logException {
        String user = Settings.currentUser;
        Date date = Settings.simulationTime.getDate();
        String level = "warning";
        return log(message, level, date, user);
    }

    /**
     * Generate a log level error
     * @param message
     */
    public static boolean error(String message) throws logException {
        String user = Settings.currentUser;
        Date date = Settings.simulationTime.getDate();
        String level = "error";
        return log(message, level, date, user);
    }

    /**
     * Write logs to file
     * @param log
     */
    private static void writeToLogFile(Log log) throws logException {
        try {
            FileWriter myWriter = new FileWriter(fileName, true);
            String logStr = log.toJson().toString();
            myWriter.write(logStr + "\n");
            myWriter.close();
        } catch (IOException e) {
            throw new logException("Cannot find the file",e);
        }
    }
}
