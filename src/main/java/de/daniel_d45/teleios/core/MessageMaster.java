/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.core.main.Teleios;


public class MessageMaster {

    // The plugin name displayed when the plugin prints something to the console or the in-game chat
    public static String pluginPrefix = "§5[DD's Teleios Plugin]§r ";

    public static String getPluginPrefix() {
        return pluginPrefix;
    }

    /**
     * This method sends out a message on plugin start if the DebugLevel is high enough.
     */
    public static void sendEnableMessage() {
        try {
            String message = pluginPrefix + "§bPlugin enabled§r";
            int debugLevel = ConfigEditor.getDebugLevel();

            if (debugLevel % 2 == 0 && debugLevel >= 2) {
                // Prints message to the chat and in the console
                Teleios.getServerObject().broadcastMessage(message);
            }
            else if (debugLevel >= 1) {
                // Prints message to the console
                Teleios.getServerObject().getConsoleSender().sendMessage(message);
            }
        } catch (Exception e) {
            Teleios.getServerObject().getConsoleSender().sendMessage("ERROR");
        }
    }

    /**
     * This method sends out a message on plugin stop if the DebugLevel is high enough.
     */
    public static void sendDisableMessage() {
        String message = pluginPrefix + "§3Plugin disabled§r";
        int debugLevel = ConfigEditor.getDebugLevel();

        if (debugLevel % 2 == 0 && debugLevel >= 2) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(message);
        }
        else if (debugLevel >= 1) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(message);
        }
    }

    // TODO: Integrate class and method name

    /**
     * This method sends out a fail message in red with the specified class, method and the exception's stack trace if the DebugLevel is high enough.
     * Fail messages are sent out when an unexpected exception happens.
     *
     * @param className  [String] The class's name.
     * @param methodName [String] The method's name.
     * @param exception  [Exception] The exception causing the error.
     */
    public static void sendFailMessage(String className, String methodName, Exception exception) {
        String message = pluginPrefix + "§4" + className + "§c.§4" + methodName + "§c failed due to §4" + exception.getClass() + "§c!";
        exception.printStackTrace();
        int debugLevel = ConfigEditor.getDebugLevel();

        if (debugLevel % 2 == 0 && debugLevel >= 4) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(message);
        }
        else if (debugLevel >= 3) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(message);
        }
    }

    /**
     * This method sends out a warning message in yellow with the specified class and method if the DebugLevel is high enough.
     * Warning messages are sent out after handling an exception which should not occur.
     *
     * @param className  [String] The class's name.
     * @param methodName [String] The message to print.
     * @param message    [String] A part of the message to send
     */
    public static void sendWarningMessage(String className, String methodName, String message) {
        String outputMessage = pluginPrefix + "§6" + className + "§e.§6" + methodName + "§e: Warning: §6" + message + "§r";
        int debugLevel = ConfigEditor.getDebugLevel();

        if (debugLevel % 2 == 0 && debugLevel >= 6) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(outputMessage);
        }
        else if (debugLevel >= 5) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(outputMessage);
        }
    }

    /**
     * This method sends out info messages at specific parts of a method to comprehend the method execution if the DebugLevel is high enough.
     *
     * @param className  [String] The calling class's name
     * @param methodName [String] The calling method's name
     * @param message    [String] A part of the message to send
     */
    public static void sendInfoMessage(String className, String methodName, String message) {
        String outputMessage = pluginPrefix + "§f" + className + "§7.§f" + methodName + "§7: §f" + message + "§r";
        int debugLevel = ConfigEditor.getDebugLevel();

        if (debugLevel % 2 == 0 && debugLevel >= 8) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(outputMessage);
        }
        else if (debugLevel >= 7) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(outputMessage);
        }
    }

    /**
     * This method sends out a success message in green with the specified class and method if the DebugLevel is high enough.
     *
     * @param className  [String] The class's name.
     * @param methodName [String] The method's name.
     */
    public static void sendSuccessMessage(String className, String methodName) {
        String message = pluginPrefix + "§2" + className + "§a.§2" + methodName + "§a ran successfully.§r";
        int debugLevel = ConfigEditor.getDebugLevel();

        if (debugLevel % 2 == 0 && debugLevel >= 10) {
            // Prints message to the chat and in the console
            Teleios.getServerObject().broadcastMessage(message);
        }
        else if (debugLevel >= 9) {
            // Prints message to the console
            Teleios.getServerObject().getConsoleSender().sendMessage(message);
        }
    }

}
