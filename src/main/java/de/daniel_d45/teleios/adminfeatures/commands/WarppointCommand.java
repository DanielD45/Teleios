/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures.commands;

import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.MessageMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class WarppointCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activationstate check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the command is deactivated.");
                return true;
            }

            // Sender permission check
            if (!sender.hasPermission("teleios.bettergameplay.warppoint")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender doesn't have the needed permissions.");
                return true;
            }

            switch (args.length) {
                case 1:
                    // Specifies /warppoint list|clear
                    if (args[0].equalsIgnoreCase("list")) {
                        try {

                            String[] warppoints = ConfigEditor.getSectionKeys("Warppoints").toArray(new String[0]);

                            // Warppoints existance check
                            if (warppoints.length <= 0) {
                                sender.sendMessage("§eThere are no warppoints yet! Add one by using §6/warppoint add [name]§e!");
                                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), there are no warppoints.");
                                return true;
                            }

                            // LISTS ALL WARPPOINTS IF THERE ARE ANY
                            sender.sendMessage("§a--------------------");
                            sender.sendMessage("§aExisting warppoints:");

                            StringBuilder message = new StringBuilder();

                            // Iterates through the warppoints
                            for (int i = 0; i < warppoints.length; i++) {

                                message.append("§6").append(warppoints[i]);

                                if (i < warppoints.length - 1) {
                                    message.append("§a, ");
                                }
                            }

                            sender.sendMessage(message.toString());
                            sender.sendMessage("§a--------------------");

                            MessageMaster.sendSuccessMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (NullPointerException e) {
                            // TODO: Necessary?
                            sender.sendMessage("§eThere are no warppoints yet! Add one by using §6/warppoint add [name]§e!");
                            MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), there are no warppoints.");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not list all warppoints!");
                            MessageMaster.sendFailMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), listing all warppoints", e);
                            return false;
                        }

                    }
                    else if (args[0].equalsIgnoreCase("clear")) {
                        try {

                            // Warppoints existance check
                            if (ConfigEditor.getSectionKeys("Warppoints") == null) {
                                sender.sendMessage("§cThere are no warppoints!");
                                MessageMaster.sendSkipMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), there are no warppoints.");
                                return true;
                            }

                            // Clears the warppoints
                            for (String current : ConfigEditor.getSectionKeys("Warppoints")) {
                                ConfigEditor.clearPath("Warppoints." + current);
                            }

                            sender.sendMessage("§aAll warppoints have been removed!");
                            MessageMaster.sendSuccessMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not clear all warppoints!");
                            MessageMaster.sendFailMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), clearing all warppoints", e);
                            return false;
                        }
                    }
                    else {
                        // Wrong arguments
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
                        return false;
                    }
                case 2:
                    // Specifies /warppoint add|remove|override ([Name])
                    if (args[0].equalsIgnoreCase("add")) {
                        // Specifies /warppoint add [Name]
                        try {

                            // Sender player check
                            if (!(sender instanceof Player player)) {
                                sender.sendMessage("§cYou are no player!");
                                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender is not a player.");
                                return true;
                            }

                            String warppointName = args[1];

                            // Unique warppoint name check
                            if (ConfigEditor.containsPath("Warppoints." + warppointName) || ConfigEditor.containsPath("Teleporters." + warppointName)) {
                                player.sendMessage("§cA warppoint or teleporter with this name already exists!");
                                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), a warppoint or teleporter with this name already exists.");
                                return true;
                            }

                            // Add a warppoint with the specified name and the player's location
                            ConfigEditor.set("Warppoints." + warppointName, player.getLocation());
                            player.sendMessage("§aAdded warppoint §6" + warppointName + "§a!");

                            MessageMaster.sendSuccessMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not add the warppoint!");
                            MessageMaster.sendFailMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), adding the warppoint §4" + args[1] + "§c.", e);
                            return false;
                        }
                    }
                    else if (args[0].equalsIgnoreCase("remove")) {
                        // Specifies /warppoint remove [Name]
                        try {

                            String warppointName = args[1];

                            // Warppoint exists check
                            if (!ConfigEditor.containsPath("Warppoints." + warppointName)) {
                                sender.sendMessage("§cThe warppoint §e" + warppointName + " §cdoesn't exist!");
                                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the specified warppoint doesn't exist.");
                                return true;
                            }

                            ConfigEditor.clearPath("Warppoints." + warppointName);
                            sender.sendMessage("§aRemoved warppoint §6" + warppointName + "§a!");
                            MessageMaster.sendSuccessMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not remove the warppoint §e" + args[1] + "§c!");
                            MessageMaster.sendFailMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), removing the warppoint §4" + args[1] + "§c.", e);
                            return false;
                        }
                    }
                    else if (args[0].equalsIgnoreCase("override")) {
                        // Specifies /warppoint override [Name]
                        try {

                            // Sender player check
                            if (!(sender instanceof Player player)) {
                                sender.sendMessage("§cYou are no player!");
                                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender is not a player.");
                                return true;
                            }

                            String warppointName = args[1];

                            // Warppoint existance check
                            if (!ConfigEditor.containsPath("Warppoints." + warppointName)) {
                                player.sendMessage("§cA warppoint with this name doesn't exist!");
                                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), a warppoint with this name doesn't exist.");
                                return true;
                            }

                            // Adds the player's location to the warppoint with the specified name
                            ConfigEditor.set("Warppoints." + warppointName, player.getLocation());
                            player.sendMessage("§aOverrode the location of warppoint §6" + warppointName + "§a!");

                            MessageMaster.sendSuccessMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not override the warppoint §e" + args[1] + "§c!");
                            MessageMaster.sendFailMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), overriding the warppoint §4" + args[1] + "§c.", e);
                            return false;
                        }
                    }
                    else {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
                        return false;
                    }

                default:
                    // Wrong amount of arguments
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
                    return false;
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }

    }

}
