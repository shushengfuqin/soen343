package org.team4.common;

import java.util.ArrayList;

public class Helper {

    /**
     * Check whether a string is alphanumeric or not
     * @param name
     * @return true if the string is alphanumeric else false
     */
    public static boolean isAlphanumeric(String name) {
        char[] chars = name.toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Round double to 2 decimal
     * @param x
     * @return
     */
    public static double roundToTwoDecimal(double x) {
        return Math.round((x) * 100.0) / 100.0;
    }

    /**
     * Check if the coordinates are in the arraylist
     * @param c coordinates array
     * @param z coordinate
     * @return a boolean
     */
    public static boolean coordInArrayList(ArrayList<Coordinate> c, Coordinate z){
        for(Coordinate coords: c){
            if(coords.x == z.x && coords.y == z.y){
                return true;
            }
        }
        return false;
    }
}
