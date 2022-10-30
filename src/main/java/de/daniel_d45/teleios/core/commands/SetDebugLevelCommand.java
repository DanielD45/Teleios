/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.commands;

import de.daniel_d45.teleios.core.main.Teleios;
import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.MessageMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;


/**
 * This class implements the /setdebuglevel command.
 *
 * @author Daniel_D45
 */
public class SetDebugLevelCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Switch for the amount of arguments
            switch (args.length) {
                case 0:
                    // Specifes /setdebuglevel
                    try {

                        // TODO: Add exception handling for invalid values
                        int level = Teleios.getDebugLevel();

                        sender.sendMessage("§aThe debug level is currently §6" + level + "§a.");
                        MessageMaster.sendSuccessMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (Exception e) {
                        MessageMaster.sendFailMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }

                case 1:
                    // Specifies /setdebuglevel [Level]

                    int newDebugLevel;

                    try {
                        // Switches "uppercase number" chars to their corresponding chars
                        args[0] = args[0].replace('=', '0');
                        args[0] = args[0].replace('!', '1');
                        args[0] = args[0].replace('\"', '2');
                        args[0] = args[0].replace('§', '3');
                        args[0] = args[0].replace('$', '4');
                        args[0] = args[0].replace('%', '5');
                        args[0] = args[0].replace('&', '6');
                        args[0] = args[0].replace('/', '7');
                        args[0] = args[0].replace('(', '8');
                        args[0] = args[0].replace(')', '9');

                        newDebugLevel = Integer.parseInt(args[0]);
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§cThis debug level is invalid");
                        MessageMaster.sendSkipMessage("SetDebugLevelCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the specified debug level is invalid.");
                        return true;
                    }

                    if (newDebugLevel < 0) {
                        ConfigEditor.setDebugLevel(0);
                    }
                    else if (newDebugLevel > 3) {
                        ConfigEditor.setDebugLevel(3);
                    }
                    else {
                        // The specified integer matches a valid debug level
                        ConfigEditor.setDebugLevel(newDebugLevel);
                    }

                    sender.sendMessage("§aThe debug level is now §6" + Teleios.getDebugLevel() + "§a.");
                    MessageMaster.sendSuccessMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                    return true;
                default:
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendSkipMessage("SetDebugLevelCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
