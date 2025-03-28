/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.adminfeatures.AdminFeatures;
import de.daniel_d45.teleios.bettergameplay.BetterGameplay;
import de.daniel_d45.teleios.core.main.Teleios;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;


/**
 * This class creates the config file needed as the plugin's storage, reads and manipulates it.
 *
 * @author Daniel_D45
 */
public class ConfigEditor {

    private static final FileConfiguration config = Teleios.getConfigObject();

    public static void setupConfig() {

        initiateJoinMessage();
        initiateActivationstates();
        initiateWarppoints();
        initiateTeleporters();
        initiateBlocksPerPearl();

        for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
            BetterGameplay.initiateWarppouch(currentPlayer.getName());
            //PassiveSkills.initiatePlayerRecords(currentPlayer.getName());
        }

        initiateLootChests();
    }

    private static void initiateJoinMessage() {
        // Initiates JoinMessage
        Object activationstate = ConfigEditor.get("JoinMessage");
        if (activationstate == null) {
            ConfigEditor.set("JoinMessage", false);
        }
        if (!ConfigEditor.containsPath("JoinMessage")) {
            ConfigEditor.set("JoinMessage", false);
            return;
        }

        if (!ConfigEditor.get("JoinMessage").equals(true)) {
            ConfigEditor.set("JoinMessage", false);
        }
    }

    private static void initiateActivationstates() {
        ArrayList<String> paths = new ArrayList<>();

        Collections.addAll(paths, AdminFeatures.getActivationstatePaths());
        Collections.addAll(paths, BetterGameplay.getActivationstatePaths());
        //Collections.addAll(paths, PassiveSkills.getActivationstatePaths());

        // Initiates the Activationstates for all the segments and functions
        for (String current : paths) {
            if (!isActive(current)) set("Activationstates." + current, "OFF");
        }
        RecipeManager.enableTeleporterRecipe(isActive("BetterGameplay.Teleporters"));
    }

    private static void initiateWarppoints() {
        if (!ConfigEditor.containsPath("Warppoints")) {
            ConfigEditor.set("Warppoints.Setup", "setup");
            ConfigEditor.set("Warppoints.Setup", null);
        }
    }

    private static void initiateTeleporters() {
        if (!ConfigEditor.containsPath("Teleporters")) {
            ConfigEditor.set("Teleporters.Setup", "setup");
            ConfigEditor.set("Teleporters.Setup", null);
        }
    }

    private static void initiateBlocksPerPearl() {
        if (!ConfigEditor.containsPath("BlocksPerPearl")) {
            ConfigEditor.set("BlocksPerPearl", 300);
        }
    }

    private static void initiateLootChests() {
        if (!ConfigEditor.containsPath("LootChests")) {
            ConfigEditor.set("LootChests.Setup", "setup");
            ConfigEditor.set("LootChests.Setup", null);
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

        // TODO: rem, activating segments doesn't work
        // System.out.println(subPath + " active? " + get("Activationstates." + subPath).equals("ON"));

        if (get("Activationstates." + subPath) == null) return false;
        return get("Activationstates." + subPath).equals("ON");

        // TODO: Is null check working?
        //return get("Activationstates." + subPath) != null && get("Activationstates." + subPath).equals("ON");
    }

    public static void switchActivationstate(String subPath) {
        if (isActive(subPath)) {
            set("Activationstates." + subPath, "OFF");
        }
        else {
            set("Activationstates." + subPath, "ON");
        }
    }

    /**
     * Sets the specified value to the specified path.
     *
     * @param path  [String] path
     * @param value [Object] value
     */
    public static void set(String path, Object value) {
        config.set(path, value);
        saveConfig();
    }

    /**
     * Gets the value stored under the specified path in the config file.
     *
     * @param path [String] The path to get the value from.
     */
    public static Object get(String path) {
        return config.get(path);
    }

    /**
     * Returns the first level keys from the specified path in the config file.
     *
     * @param path [String] The section's path.
     * @return [Set<String>] The specified section's first level keys.
     */
    public static Set<String> getSectionKeys(String path) {
        ConfigurationSection configSection = config.getConfigurationSection(path);
        if (configSection == null) return null;
        // Represents only the direct keys
        return configSection.getKeys(false);
    }

    /**
     * Returns whether the specified path exists in the config file.
     *
     * @param path [String] The path to search for.
     * @return isThere [boolean] Whether the path is contained.
     */
    public static boolean containsPath(String path) {
        return config.contains(path, false);
    }

    /**
     * Returns whether the specified value exists under the specified path in the config file.
     *
     * @param path  [String] The path to search for.
     * @param value [Object] The object to search for.
     * @return hasValue [boolean] Whether the path is contained.
     */
    public static boolean hasValue(String path, Object value) {
        Object foundValue = get(path);
        return GlobalFunctions.betterEquals(foundValue, value);
    }

    /**
     * This method clears the specified path in the config file. Only works with paths that have a value
     * (keys).
     */
    public static void clearPath(String path) {
        config.set(path, null);
        saveConfig();
    }

    /**
     * Saves the config file.
     */
    private static void saveConfig() {
        Teleios.getPlugin().saveConfig();
    }

}
