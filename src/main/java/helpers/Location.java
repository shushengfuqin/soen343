package helpers;

import org.json.JSONObject;

public class Location {
    public int x;
    public int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public JSONObject toJson() {
        JSONObject jo = new JSONObject();
        jo.put("x", this.x);
        jo.put("y", this.y);
        return jo;
    }

    public String toString() {
        return "x: " + this.x + " Y: " + this.y;
    }
}
