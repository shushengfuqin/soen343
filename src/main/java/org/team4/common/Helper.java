package org.team4.common;

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

}
