/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class GlobalMethods {

    /**
     * This method compares the equality of two objects. If both are null, it outputs true.
     */
    public static boolean betterEquals(Object object1, Object object2) {
        if (object1 == null && object2 == null) return true;
        if (object1 == null || object2 == null) return false;
        return object1.equals(object2);
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

    public static double trimDouble(double d, double minValue, double maxValue) {
        if (d < minValue) d = minValue;
        else if (d > maxValue) d = maxValue;
        return d;
    }

    public static boolean stringNotUsable(String string, int lengthMin, int lengthMax) {
        if (string == null) return true;
        if (string.length() < lengthMin) return true;
        return string.length() > lengthMax;
    }

    /**
     * Reduces a List of options down to the List of options starting with the same letter.
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

    /**
     * @param subPath The commands ActivationState subpath
     * @param sender  The command sender
     * @return whether the command is inactive
     */
    public static boolean cmdOffCheck(String subPath, CommandSender sender) {
        if (!ConfigEditor.isActive(subPath)) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }
        return false;
    }

    public static boolean wrongAmountofArgs(CommandSender sender) {
        sender.sendMessage("§cWrong amount of arguments!");
        return false;
    }

    public static boolean senderPlayerCheck(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cYou are not a player!");
            return true;
        }
        return false;
    }

    public static Player getTarget(String targetName, CommandSender sender) {
        Player target = Bukkit.getPlayerExact(targetName);
        // Target online check
        if (target == null) sender.sendMessage("§cPlayer §6" + targetName + "§c is not online!");
        return target;
    }

}
