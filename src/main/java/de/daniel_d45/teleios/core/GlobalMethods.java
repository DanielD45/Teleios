/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class GlobalMethods {

    /**
     * This method compares the equality of two objects. If both are null, it outputs true.
     *
     * @param obj1 [Object] The first object
     * @param obj2 [Object] The second object
     * @return [boolean] Whether both objects are null or equal
     */
    public static boolean betterEquals(Object obj1, Object obj2) {

        if (obj1 == null && obj2 == null) {
            return true;
        }

        if (obj1 == null || obj2 == null) {
            return false;
        }
        return obj1.equals(obj2);
    }

    public static void sendErrorFeedbackCmd(CommandSender recipient) {
        recipient.sendMessage("§cError occurred while running command!");
    }

    public static boolean intUsable(int i, int minValue, int maxValue) {
        if (i < minValue) return false;
        else return i <= maxValue;
    }

    public static int trimInt(int i, int minValue, int maxValue) {
        if (i < minValue) i = minValue;
        else if (i > maxValue) i = maxValue;
        return i;
    }

    public static boolean stringUsable(String string, int lengthMin, int lengthMax) {
        if (string == null) return false;
        if (string.length() < lengthMin) return false;
        return string.length() <= lengthMax;
    }

    /**
     * Reduces a List of options down to the List of options starting with the same letter.
     *
     * @param argument -
     * @param options  -
     * @return -
     */
    public static List<String> getFittingOptions(String argument, List<String> options) {

        if (options == null) return null;

        List<String> fittingOptions = new ArrayList<>();
        for (String option : options) {
            if (option.toLowerCase().startsWith(argument.toLowerCase())) {
                fittingOptions.add(option);
            }
        }
        return fittingOptions;
    }
}
