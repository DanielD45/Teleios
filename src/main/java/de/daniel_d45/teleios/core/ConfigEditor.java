/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.adminfeatures.SegmentManagerAF;
import de.daniel_d45.teleios.bettergameplay.SegmentManagerBG;
import de.daniel_d45.teleios.core.main.Teleios;
import de.daniel_d45.teleios.passiveskills.SegmentManagerPS;
import de.daniel_d45.teleios.worldmaster.SegmentManagerWM;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;


/**
 * This class creates the needed config file for the plugin, reads and manipulates it.
 *
 * @author Daniel_D45
 */
public class ConfigEditor {

    private static final FileConfiguration config = Teleios.getConfigObject();

    public static int getDebugLevel() {
        int debugLevel;
        try {
            debugLevel = Integer.parseInt(Objects.requireNonNull(get("DebugLevel")).toString());
        } catch (Exception e) {
            Teleios.getServerObject().getConsoleSender().sendMessage(MessageMaster.getPluginPrefix() + "§4ConfigEditor§c class: Warning in method §4getDebugLevel()§c: the DebugLevel is invalid!");
            debugLevel = Teleios.getStandardDebugLevel();
        }
        return debugLevel;
    }

    public static void setupConfig() {
        try {

            initiateDebugLevel();
            initiateJoinMessage();
            initiateActivationstates();
            initiateWarppoints();
            initiateTeleporters();
            initiateBlocksPerPearl();

            for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                SegmentManagerBG.initiateWarppouch(currentPlayer.getName());
                SegmentManagerPS.initiatePlayerRecords(currentPlayer.getName());
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "setupConfig()", e);
        }
    }

    private static void initiateDebugLevel() {
        try {

            // Tries to get the debug level
            int debugLevel = Integer.parseInt(config.get("DebugLevel").toString());

            if (debugLevel > 3 || debugLevel < 0) {
                // Debug level is out of bounds
                // Sets the debug level to the standard value of 1
                ConfigEditor.set("DebugLevel", 1);
            }
        } catch (NullPointerException e) {
            // Sets the debug level to the standard value of 1
            ConfigEditor.set("DebugLevel", 1);
            MessageMaster.sendWarningMessage("ConfigEditor", "initiateDebugLevel()", "the DebugLevel path doesn't exist.");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "initiateDebugLevel()", e);
        }
    }

    private static void initiateJoinMessage() {
        try {

            if (ConfigEditor.get("JoinMessage") == null || !ConfigEditor.get("JoinMessage").equals(true)) {

                ConfigEditor.set("JoinMessage", false);
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "initiateJoinMessage()", e);
        }
    }

    private static void initiateActivationstates() {
        try {

            ArrayList<String> paths = new ArrayList<>();

            // TODO: Keep up-to-date
            Collections.addAll(paths, SegmentManagerAF.getActivationstatePaths());

            Collections.addAll(paths, SegmentManagerBG.getActivationstatePaths());

            Collections.addAll(paths, SegmentManagerPS.getActivationstatePaths());

            Collections.addAll(paths, SegmentManagerWM.getActivationstatePaths());

            // Initiates the Activationstates for all the segments and functions
            for (String current : paths) {
                if (!isActive(current)) {
                    set("Activationstates." + current, "OFF");
                }
            }

            RecipeManager.enableTeleporterRecipe(isActive("BetterGameplay.Teleporters"));

            MessageMaster.sendSuccessMessage("ConfigEditor", "initiateActivationstates()");
        } catch (IllegalStateException e) {
            MessageMaster.sendWarningMessage("ConfigEditor", "Skipped method initiateActivationstates()", "IllegalStateException");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "initiateActivationstates()", e);
        }
    }

    private static void initiateWarppoints() {
        try {

            if (!ConfigEditor.containsPath("Warppoints")) {
                ConfigEditor.set("Warppoints.Setup", "setup");
                ConfigEditor.set("Warppoints.Setup", null);
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "initiateWarppoints()", e);
        }
    }

    private static void initiateTeleporters() {
        try {

            if (!ConfigEditor.containsPath("Teleporters")) {
                ConfigEditor.set("Teleporters.Setup", "setup");
                ConfigEditor.set("Teleporters.Setup", null);
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "initiateTeleporters()", e);
        }
    }

    private static void initiateBlocksPerPearl() {
        try {

            if (!ConfigEditor.containsPath("BlocksPerPearl")) {
                ConfigEditor.set("BlocksPerPearl", 100);
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "initiateBlocksPerPearl()", e);
        }
    }

    /**
     * Returns whether the segment's activationstate is on or not.
     *
     * @param subPath [String] The segment's name to get the activationstate of.
     * @return [boolean] Whether the segment is active or not. Returns false if the process
     * failed.
     */
    public static boolean isActive(String subPath) {
        try {

            // TODO: Is null check working?
            if (get("Activationstates." + subPath) != null && get("Activationstates." + subPath).equals("ON")) {

                MessageMaster.sendSuccessMessage("ConfigEditor", "isActive(" + subPath + "), segment is active");
                return true;
            }
            else {
                MessageMaster.sendSuccessMessage("ConfigEditor", "isActive(" + subPath + "), segment is not active");
                return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "isActive(" + subPath + ")", e);
            return false;
        }
    }

    /*
    public static void setDebugLevel(int level) {
        try {

            if (level >= 0) {
                ConfigEditor.set("DebugLevel", level);
            }
            else {
                MessageMaster.sendSkipMessage("ConfigEditor", "setDebugLevel(" + level + "), the specified level is invalid.");
                return;
            }

            MessageMaster.sendSuccessMessage("ConfigEditor", "setDebugLevel(" + level + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "setDebugLevel(" + level + ")", e);
            ConfigEditor.set("DebugLevel", Teleios.getStandardDebugLevel());
        }
    }
*/

    public static void switchActivationstate(String subPath) {
        try {

            if (isActive(subPath)) {
                set("Activationstates." + subPath, "OFF");
            }
            else {
                set("Activationstates." + subPath, "ON");
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "switchActivationstate(" + subPath + ")", e);
        }
    }

    /**
     * Sets the specified value to the specified path.
     *
     * @param path  [String] path
     * @param value [Object] value
     */
    public static void set(String path, Object value) {
        try {

            config.set(path, value);
            saveConfig();

            MessageMaster.sendSuccessMessage("ConfigEditor", "set(" + path + ", " + value + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "set(" + path + ", " + value + ")", e);
        }
    }

    /**
     * Gets the value of the specified path in the config file.
     *
     * @param path [String] The path to get the value from.
     */
    public static Object get(String path) {
        try {

            Object value = config.get(path);

            //MessageMaster.sendSuccessMessage("ConfigEditor", "get(" + path + ")");
            return value;
        } catch (Exception e) {
            // MessageMaster.sendFailMessage("ConfigEditor", "get(" + path + ")", e);
            return null;
        }
    }

    /**
     * Returns the first level keys from the specified path in the config file.
     *
     * @param path [String] The section's path.
     * @return [Set<String>] The specified section's first level keys.
     */
    public static Set<String> getSectionKeys(String path) {
        try {

            ConfigurationSection configSection = config.getConfigurationSection(path);
            // Represents only the direct keys
            Set<String> keys = configSection.getKeys(false);

            if (keys.toArray().length > 0) {
                // Found keys
                MessageMaster.sendSuccessMessage("ConfigEditor", "getSectionKeys(" + path + ")");
            }
            else {
                // No keys found
                MessageMaster.sendWarningMessage("ConfigEditor", "getSectionKeys(" + path + ")", "no keys found");
            }

            return keys;
        } catch (NullPointerException e) {
            MessageMaster.sendWarningMessage("ConfigEditor", "getSectionKeys(" + path + ")", "the path doesn't exist.");
            return null;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "getSectionKeys(" + path + ")", e);
            return null;
        }
    }

    /**
     * Returns whether the specified path exists in the config file.
     *
     * @param path [String] The path to search for.
     * @return isThere [boolean] Whether the path is contained.
     */
    public static boolean containsPath(String path) {
        try {

            boolean isThere = config.contains(path, false);

            MessageMaster.sendSuccessMessage("ConfigEditor", "containsPath(" + path + ")");
            return isThere;
        } catch (NullPointerException e) {
            MessageMaster.sendWarningMessage("ConfigEditor", "containsPath(" + path + ")", "the path doesn't exist.");
            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "containsPath(" + path + ")", e);
            return false;
        }
    }

    /**
     * Returns whether the specified value exists under the specified path in the config file.
     *
     * @param path  [String] The path to search for.
     * @param value [Object] The object to search for.
     * @return hasValue [boolean] Whether the path is contained.
     */
    public static boolean hasValue(String path, Object value) {
        try {

            Object foundValue = get(path);
            boolean hasValue = BetterMethods.betterEquals(foundValue, value);

            MessageMaster.sendSuccessMessage("ConfigEditor", "hasValue(" + path + ")");
            return hasValue;
        } catch (NullPointerException e) {
            MessageMaster.sendWarningMessage("ConfigEditor", "hasValue(" + path + ")", "the path doesn't exist.");
            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "hasValue(" + path + ")", e);
            return false;
        }
    }

    /**
     * This method clears the specified path in the config file. Only works with paths that have a value
     * (keys).
     */
    public static void clearPath(String path) {
        try {

            config.set(path, null);
            saveConfig();

            MessageMaster.sendSuccessMessage("ConfigEditor", "clearPath(" + path + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "clearPath(" + path + ")", e);
        }
    }

    /**
     * Saves the config file.
     */
    private static void saveConfig() {
        try {

            Teleios.getPlugin().saveConfig();

            MessageMaster.sendSuccessMessage("ConfigEditor", "saveConfig()");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigEditor", "saveConfig()", e);
        }
    }

}
