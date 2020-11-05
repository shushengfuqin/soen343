package org.team4.common;

import org.json.JSONObject;

public class Coordinate {
    public int x;
    public int y;

    public Coordinate(String s){
        s = s.replaceAll("[()]", "");
        s = s.replace(" ", "");
        String[] coords = s.split(",");
        this.x = Integer.parseInt(coords[0]);
        this.y = Integer.parseInt(coords[1]);
    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){
        return this.x;
    }

    public int getY() {
        return this.y;
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
