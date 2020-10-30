package org.team4.common.logger;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log {
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
    public String level;
    public String message;
    public Date date;
    public String user;

    /**
     * Constructor
     * @param m the message
     * @param l the severity level
     * @param d the date
     * @param u the current user
     */
    public Log(String m, String l, Date d, String u) {
        this.message = m;
        this.level = l;
        this.date = d;
        this.user = u;
    }

    /**
     * Constructor
     * @param jo a log as a json object
     */
    public Log(JSONObject jo) {
        String dateStr = jo.getString("date");
        try {
            this.date = dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        this.message = jo.getString("message");
        this.level = jo.getString("level");
        this.user = jo.getString("user");
    }

    /**
     * Generates a json from a log
     * @return a json object log
     */
    public JSONObject toJson() {
        JSONObject jo = new JSONObject();
        String dateStr = dateFormat.format(date);
        jo.put("date", dateStr);
        jo.put("user", user);
        jo.put("level", level);
        jo.put("message", message);
        return jo;
    }
}
