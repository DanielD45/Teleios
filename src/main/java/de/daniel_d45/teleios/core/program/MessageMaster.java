/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.program;

import de.daniel_d45.teleios.core.main.Teleios;

import java.util.Arrays;


public class MessageMaster {

    // The plugin name displayed when the plugin prints something to the console
    public static String pluginPrefix = "§5[DD's Teleios Plugin]§r ";

    /**
     * This method sends out a message on plugin start.
     */
    public static void sendEnableMessage() {

        String message = pluginPrefix + "§bPlugin enabled";

        if (Teleios.getDebugLevel() >= 3) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(message);
        }
        else {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(message);
        }
    }

    /**
     * This method sends out a message on plugin stop.
     */
    public static void sendDisableMessage() {

        String message = pluginPrefix + "§3Plugin disabled";

        if (Teleios.getDebugLevel() >= 3) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(message);
        }
        else {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(message);
        }
    }

    /**
     * This method sends out a skip message in yellow with the specified class and method if the
     * debug level is high enough. Skip messages are sent out when an anticipated error happens.
     *
     * @param className        [String] The class's name.
     * @param MethodAndMessage [String] The message to print.
     */
    public static void sendSkipMessage(String className, String MethodAndMessage) {

        String FullMessage = pluginPrefix + "§6" + className + "§e class: Skipped method §6" + MethodAndMessage;

        if (Teleios.getDebugLevel() >= 3) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(FullMessage);
        }
        else if (Teleios.getDebugLevel() >= 2) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(FullMessage);
        }
    }

    /**
     * This method sends out a success message in green with the specified class and method if the debug
     * level is high enough.
     *
     * @param className  [String] The class's name.
     * @param methodName [String] The method's name.
     */
    public static void sendSuccessMessage(String className, String methodName) {

        String message = pluginPrefix + "§2" + className + " §aclass: Successfully executed method §2" + methodName + "§a.";

        if (Teleios.getDebugLevel() >= 3) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(message);
        }
        else if (Teleios.getDebugLevel() >= 2) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(message);
        }
    }

    // TODO: Remove?, Integrate class and method name

    /**
     * This method sends out a fail message in red with the specified class, method and the exception's stack trace if the debug
     * level is high enough. Fail messages are sent out when an error happens that is not supposed to occur.
     *
     * @param className  [String] The class's name.
     * @param methodName [String] The method's name.
     * @param exception  [Exception] The exception causing the error.
     */
    public static void sendFailMessage(String className, String methodName, Exception exception) {

        String message = pluginPrefix + "§4" + className + " §cclass: §4" + exception.getClass() + " §coccured while running method §4" + methodName + "§c!\n" + Arrays.toString(exception.getStackTrace());
        if (Teleios.getDebugLevel() >= 3) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(message);
        }
        else if (Teleios.getDebugLevel() >= 1) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(message);
        }
    }

}
