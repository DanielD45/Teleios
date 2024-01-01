/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.common.reflection.qual.UnknownClass;

/**
 * This class is only used for storing code samples for copying in its methods.
 */
@UnknownClass
@SuppressWarnings("unused")
interface SampleCode {

    @SuppressWarnings("ALL")
    private static boolean cmdOffCheck(CommandSender sender) {
        //
        if (GlobalMethods.cmdOffCheck("AdminFeatures.All", sender)) return true;
        //
        return false;
    }

    private static boolean wrongAmountofArgs(CommandSender sender) {
        //
        return GlobalMethods.wrongAmountofArgs(sender);
        //
    }

    private static boolean senderPlayerCheck(CommandSender sender) {
        //
        if (GlobalMethods.senderPlayerCheck(sender)) return true;
        Player player = (Player) sender;
        //
        return false;
    }

    @SuppressWarnings("ALL")
    private static boolean getTarget(Player target, String[] args, CommandSender sender) {
        //
        target = GlobalMethods.getTarget(args[0], sender);
        if (target == null) return true;
        //
        return false;
    }

    private static boolean getUserInt(String xINPUTx, int xMIN_VALUEx, int xMAX_VALUEx, CommandSender sender) {
        //
        try {
            int xINPUTNAMEx = GlobalMethods.trimInt(Integer.parseInt(xINPUTx), xMIN_VALUEx, xMAX_VALUEx);
        } catch (NumberFormatException e) {
            // The input is not an int
            sender.sendMessage("§cInvalid number!");
            return false;
        }
        //
        return true;
    }

    // TODO: imp
    private static boolean getUserDouble(String xINPUTx, double xMIN_VALUEx, double xMAX_VALUEx,
                                         double xEXCLUDED_VALUEx, CommandSender sender) {
        //
        try {
            double xINPUTNAMEx = GlobalMethods.trimDouble(Double.parseDouble(xINPUTx), xMIN_VALUEx, xMAX_VALUEx);
            if (xINPUTNAMEx == xEXCLUDED_VALUEx) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            // The input is not a suitable double
            sender.sendMessage("§cInvalid number!");
            return false;
        }
        //
        return true;
    }

    // TODO: imp
    private static boolean getUserString(String xINPUTx, int xMIN_LENGTHx, int xMAX_LENGTHx, CommandSender sender) {
        //
        if (GlobalMethods.stringNotUsable(xINPUTx, xMIN_LENGTHx, xMAX_LENGTHx)) {
            // The input is too long
            sender.sendMessage("§6" + xINPUTx + "§c is too long!");
            return true;
        }
        //
        return false;
    }

    private static void TabCompleter() {
        /*
        Class must extend TabCompleter.
        in method List<String> onTabComplete(...):
            return the list of options to complete
            return empty list => no tab completion
            return null => TabCompleter for player names
         */
    }

    private static void markEntities() {
    /*
    Mark entities, deleted on server restart:
    Entity?.setMetadata("Key",new FixedMetadataValue(Plugin.getInstance(), Value(Object)));

    Mark entities:
    Entity?.getPersistentDataContainer();
     */
    }

}
