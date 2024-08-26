/*
 2020-2024
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import org.checkerframework.common.reflection.qual.UnknownClass;


// This class is only used for storing code samples to copy.
@UnknownClass
interface SampleCode {

    /*
    private static boolean commandSamples(CommandSender sender, GameMode gameMode, String xINPUT_TO_INTRODUCEx, double xMIN_VALUEx, double xMAX_VALUEx) {
        // invalid input (an input value is wrong)
        return GlobalFunctions.invalidNumber(sender);
        // wrong amount of arguments
        return GlobalFunctions.wrongAmountofArgs(sender);

        // is active check
        if (GlobalFunctions.cmdOffCheck("xSUB_PATHx", sender)) return true;
        // player in wrong gamemode check
        if (GlobalFunctions.invalidGamemodePlayer(target, "", gameMode)) return true;
        // target in wrong gamemode check
        if (GlobalFunctions.invalidGamemodeTarget(sender, target, "", gameMode)) return true;
        // introduce sender as player
        Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
        if (player == null) return true;
        // introduce target player
        Player target = GlobalFunctions.introduceTargetPlayer(xINPUT_TO_INTRODUCEx, sender);
        if (target == null) return true;
        // introduce int
        // TODO
        // introduce double
        double xDOUBLE_NAMEx = GlobalFunctions.introduceDouble(xINPUT_TO_INTRODUCEx, xMIN_VALUEx, xMAX_VALUEx, sender);
        if (xDOUBLE_NAMEx == Double.NEGATIVE_INFINITY) return false;
        if (xDOUBLE_NAMEx == 0) return GlobalFunctions.invalidNumber(sender); // filters out special values
        // introduce string
        // TODO
    }

    // TODO: overhaul
    private static boolean getUserString(String xINPUTx, int xMIN_LENGTHx, int xMAX_LENGTHx, CommandSender sender) {
        //
        if (GlobalFunctions.stringNotUsable(xINPUTx, xMIN_LENGTHx, xMAX_LENGTHx)) {
            // The input is too long
            sender.sendMessage("§6" + xINPUTx + "§c is too long!");
            return true;
        }
        //
        return false;
    }

    private static void TabCompleter() {
        Class must extend TabCompleter.
        in method List<String> onTabComplete(...):
            return the list of options to complete
            return empty list => no tab completion
            return null => TabCompleter for player names
    }

    private static void markEntities() {
        Mark entities, deleted on server restart:
        Entity?.setMetadata("Key",new FixedMetadataValue(Plugin.getInstance(), Value(Object)));

        Mark entities:
        Entity?.getPersistentDataContainer();
    }
    */
}
