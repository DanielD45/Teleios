/*
 2020-2024
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;


public class GlobalFunctions {

    /**
     * Compares the equality of two objects. If both are null, it outputs true.
     */
    public static boolean betterEquals(Object object1, Object object2) {
        if (object1 == null && object2 == null) return true;
        if (object1 == null || object2 == null) return false;
        return object1.equals(object2);
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

    /**
     * minValue and maxValue are the valid interval's inclusive borders.
     */
    public static double introduceDouble(String inputValue, double minValue, double maxValue, CommandSender sender) {
        try {
            double d = Double.parseDouble(inputValue);
            return trimDouble(d, minValue, maxValue);
        } catch (ClassCastException e) {
            invalidNumber(sender);
            return Double.NEGATIVE_INFINITY;
        }
    }

    public static boolean invalidGamemodePlayer(Player player, String specialMessage, GameMode... gameModes) {
        GameMode playerGM = player.getGameMode();
        for (GameMode currentGM : gameModes) {
            if (playerGM.equals(currentGM)) {
                if (specialMessage.isEmpty()) {
                    player.sendMessage("§cYou are not in a suitable gamemode!");
                }
                else {
                    player.sendMessage(specialMessage);
                }
                return true;

            }
        }
        return false;
    }

    public static boolean invalidGamemodeTarget(CommandSender sender, Player target, String specialMessage, GameMode... gameModes) {
        GameMode targetGM = target.getGameMode();
        for (GameMode currentGM : gameModes) {
            if (targetGM.equals(currentGM)) {
                if (specialMessage.isEmpty()) {
                    sender.sendMessage("§cYour target ist not in a suitable gamemode!");
                }
                else {
                    sender.sendMessage(specialMessage);
                }
                return true;
            }
        }
        return false;
    }

    // TODO: introduceString
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

    public static boolean invalidNumber(CommandSender sender) {
        sender.sendMessage("§cInvalid number!");
        return false;
    }

    // TODO: invalidString?

    public static Player introduceSenderAsPlayer(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cYou are not a player!");
            return null;
        }
        return player;
    }

    public static Player introduceTargetPlayer(String targetName, CommandSender sender) {
        Player target = Bukkit.getPlayerExact(targetName);
        // target online check
        if (target == null) sender.sendMessage("§cPlayer §6" + targetName + "§c is not online!");
        return target;
    }

}
