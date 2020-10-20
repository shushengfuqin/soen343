package org.team4.common;

import org.json.JSONObject;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *
     * @return A json object of the coordinates
     */
    public JSONObject toJson() {
        JSONObject jo = new JSONObject();
        jo.put("x", this.x);
        jo.put("y", this.y);
        return jo;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
}
