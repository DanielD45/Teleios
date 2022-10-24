/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.util;

import de.daniel_d45.teleios.core.main.Main;


public class MessageMaster {

    // The plugin name displayed when the plugin prints something to the console
    public static String pluginPrefix = "§5[DD's Teleios Plugin] ";

    /**
     * This method sends out a message on plugin start
     */
    public static void sendEnableMessage() {
        if (Main.getDebugLevel() >= 3) {
            // Prints message to the chat
            Main.getServerObject().broadcastMessage(pluginPrefix + "§bPlugin enabled");
        }
        else {
            // Prints message to the console
            Main.getServerObject().getConsoleSender().sendMessage(pluginPrefix + "§bPlugin enabled");
        }
    }

    /**
     * This method sends out a message on plugin stop
     */
    public static void sendDisableMessage() {
        if (Main.getDebugLevel() >= 3) {
            // Prints message to the chat
            Main.getServerObject().broadcastMessage(pluginPrefix + "§3Plugin disabled");
        }
        else {
            // Prints message to the console
            Main.getServerObject().getConsoleSender().sendMessage(pluginPrefix + "§3Plugin disabled");
        }
    }

    /**
     * This method sends out a skip message in gray/yellow with the specified class and method when the
     * debug level is high enough.
     *
     * @param className [String] The class's name.
     * @param message   [String] The message to print.
     */
    public static void sendSkipMessage(String className, String message) {
        if (Main.getDebugLevel() >= 3) {
            // Prints message to the chat
            Main.getServerObject().broadcastMessage(pluginPrefix + "§e" + className + " class: " + message);
        }
        else if (Main.getDebugLevel() >= 2) {
            // Prints message to the console
            Main.getServerObject().getConsoleSender().sendMessage(pluginPrefix + "§e" + className + " class: " + message);
        }
    }

    /**
     * This method sends out a success message in green with the specified class and method when the debug
     * level is high enough.
     *
     * @param className  [String] The class's name.
     * @param methodName [String] The method's name.
     */
    public static void sendSuccessMessage(String className, String methodName) {
        if (Main.getDebugLevel() >= 3) {
            // Prints message to the chat
            Main.getServerObject().broadcastMessage(pluginPrefix + "§2" + className + " §aclass: Successfully executed method §2" + methodName + "§a.");
        }
        else if (Main.getDebugLevel() >= 2) {
            // Prints message to the console
            Main.getServerObject().getConsoleSender().sendMessage(pluginPrefix + "§2" + className + "§a" + " class: Successfully executed method " + "§2" + methodName + "§a" + ".");
        }
    }

    // TODO: Integrate class and method name

    /**
     * This method sends out an error message in red with the specified class and method when the debug
     * level is high enough.
     *
     * @param className  [String] The class's name.
     * @param methodName [String] The method's name.
     * @param exception  [Exception] The exception causing the error.
     */
    public static void sendFailMessage(String className, String methodName, Exception exception) {
        if (Main.getDebugLevel() >= 3) {
            // Prints message to the chat
            Main.getServerObject().broadcastMessage(pluginPrefix + "§4" + className + " §cclass: §4" + exception.getClass() + " §coccured while running method §4" + methodName + "§c!");
        }
        else if (Main.getDebugLevel() >= 1) {
            // Prints message to the console
            Main.getServerObject().getConsoleSender().sendMessage(pluginPrefix + "§4" + className + "§c" + " class: " + "§4" + exception.getClass() + "§c" + " occured while running method " + "§4" + methodName + "§c" + "!");
        }
    }

}
