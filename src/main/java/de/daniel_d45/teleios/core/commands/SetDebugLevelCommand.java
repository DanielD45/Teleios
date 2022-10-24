/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.commands;

import de.daniel_d45.teleios.core.main.Main;
import de.daniel_d45.teleios.core.util.ConfigEditor;
import de.daniel_d45.teleios.core.util.MessageMaster;
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

            // Sender permission check
            if (!sender.hasPermission("teleios.core.setdebuglevel")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("SetDebugLevelCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), missing permissions.");
                return true;
            }

            // Switch for the amount of arguments
            switch (args.length) {
                case 0:
                    // Specifes /setdebuglevel
                    try {

                        int level = Main.getDebugLevel();

                        sender.sendMessage("§aThe debug level is currently §6" + level + "§a.");
                        MessageMaster.sendSuccessMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (Exception e) {
                        MessageMaster.sendFailMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }

                case 1:
                    // Specifies /setdebuglevel [Level]
                    try {

                        // Try to get the first argument as an int
                        int level = Integer.parseInt(args[0]);

                        if (level < 0) {
                            ConfigEditor.setDebugLevel(0);
                        }
                        else if (level > 3) {
                            ConfigEditor.setDebugLevel(3);
                        }
                        else {
                            // The specified integer matches a debug level
                            ConfigEditor.setDebugLevel(level);
                        }

                        sender.sendMessage("§aThe debug level is now §6" + Main.getDebugLevel() + "§a.");
                        MessageMaster.sendSuccessMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("SetDebugLevelCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
                        return false;
                    }

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
