/*
 2020-2025
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

/**
 * This class is only used for storing code samples to copy.
 */
interface SampleCode {

    ///*
    // Changes: update version of sample code (SC-1 -> SC-2)
    private static boolean commandSamples() {
        // USER FEEDBACK
        // Invalid number (an input value is wrong) SC-1
        return GlobalFunctions.invalidNumber(sender, args[xINDEXx]);
        // Wrong amount of arguments SC-1
        return GlobalFunctions.wrongAmountofArgs(sender);

        // CHECKS
        // Is active check SC-1
        if (GlobalFunctions.cmdOffCheck("xSUB_PATHx", sender)) return true;
        // Sender permission check SC-1
        if (GlobalFunctions.permissionCheck(sender, "xSUB_PATHx")) return true;
        // Player in wrong gamemode check SC-1
        if (GlobalFunctions.invalidGamemodePlayer(target, "", gameMode)) return true;
        // Target in wrong gamemode check SC-1
        if (GlobalFunctions.invalidGamemodeTarget(sender, target, "", gameMode)) return true;

        // VARIABLE INTRODUCTIONS
        // sender -> player SC-1
        Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
        if (player == null) return true;
        // Gets target player SC-1
        Player target = GlobalFunctions.introduceTargetPlayer(args[xINPUT_TO_INTRODUCEx], sender);
        if (target == null) return true;
        // Try-gets config entry SC-1
        xDATA_TYPEx xVARIABLEx = (xDATA_TYPEx) GlobalFunctions.getConfigEntry(xCONFIG_PATHx, xNULL_MESSAGEx, xSENDERx);
        if (xVARIABLEx == null) return true;
        // Try-gets config section keys SC-1
        Set<String> xVARIABLEx = GlobalFunctions.getConfigKeys(xCONFIG_PATHx, xNULL_MESSAGEx, xSENDERx);
        if (xVARIABLEx == null) return true;
        // Force-Gets int SC-1
        int xINT_NAMEx = GlobalFunctions.introduceInt(args[xINPUT_TO_INTRODUCEx], xMIN_VALUEx, xMAX_VALUEx, sender);
        if (xINT_NAMEx == Integer.MIN_VALUE) return false;
        if (xINT_NAMEx == 0) return GlobalFunctions.invalidNumber(sender, args[xINPUT_TO_INTRODUCEx]);
        // Force-gets double SC-1
        double xDOUBLE_NAMEx = GlobalFunctions.introduceDouble(args[xINPUT_TO_INTRODUCEx], xMIN_VALUEx, xMAX_VALUEx, sender);
        if (xDOUBLE_NAMEx == Double.NEGATIVE_INFINITY) return false;
        if (xDOUBLE_NAMEx == 0) return GlobalFunctions.invalidNumber(sender, args[xINPUT_TO_INTRODUCEx]);
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
        Entity ?.setMetadata("Key", new FixedMetadataValue(Plugin.getInstance(), Value(Object)));

        Mark entities:
        Entity ?.getPersistentDataContainer();
    }
    */
}
