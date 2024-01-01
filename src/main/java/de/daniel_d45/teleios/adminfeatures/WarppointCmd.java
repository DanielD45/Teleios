/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Set;


public class WarppointCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
                             @Nonnull String[] args) {
        try {

            // Activationstate check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                return true;
            }

            switch (args.length) {
                case 1:
                    // /warppoint list|clear
                    if (args[0].equalsIgnoreCase("list")) {
                        try {

                            String[] warppoints = ConfigEditor.getSectionKeys("Warppoints").toArray(new String[0]);

                            // Warppoints existance check
                            if (warppoints.length == 0) {
                                sender.sendMessage("§eThere are no warppoints yet! Add one by using §6/warppoint add [name]§e!");
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

                            return true;
                        } catch (NullPointerException e) {
                            // TODO: Necessary?
                            sender.sendMessage("§eThere are no warppoints yet! Add one by using §6/warppoint add [name]§e!");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not list all warppoints!");
                            return false;
                        }

                    } else if (args[0].equalsIgnoreCase("clear")) {
                        try {

                            // Warppoints existance check
                            if (ConfigEditor.getSectionKeys("Warppoints") == null) {
                                sender.sendMessage("§cThere are no warppoints!");
                                return true;
                            }

                            // Clears the warppoints
                            for (String current : ConfigEditor.getSectionKeys("Warppoints")) {
                                ConfigEditor.clearPath("Warppoints." + current);
                            }

                            sender.sendMessage("§aAll warppoints have been removed!");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not clear all warppoints!");
                            return false;
                        }
                    } else {
                        // Wrong arguments
                        sender.sendMessage("§cWrong arguments!");
                        return false;
                    }
                case 2:
                    // /warppoint add|remove|override ([Name])
                    if (args[0].equalsIgnoreCase("add")) {
                        // /warppoint add [Name]
                        try {

                            // Sender player check
                            if (!(sender instanceof Player player)) {
                                sender.sendMessage("§cYou are no player!");
                                return true;
                            }

                            Set<String> warppointNames = ConfigEditor.getSectionKeys("Warppoints");
                            Set<String> teleporterNames = ConfigEditor.getSectionKeys("Teleporters");

                            String specifiedName = args[1];

                            if (warppointNames != null) {
                                // Checks for warppoints with the same name
                                for (String currentWPName : warppointNames) {
                                    if (currentWPName.equalsIgnoreCase(specifiedName)) {
                                        // Name match
                                        player.sendMessage("§cA warppoint with this name already exists!");
                                        return true;
                                    }
                                }
                                // No warppoint name match
                            }
                            // No warppoints or no warppoint name match

                            if (teleporterNames != null) {
                                // Checks for teleporters with the same name
                                for (String currentTPName : teleporterNames) {
                                    if (currentTPName.equalsIgnoreCase(specifiedName)) {
                                        // Name match
                                        player.sendMessage("§cA teleporter with this name already exists!");
                                        return true;
                                    }
                                }
                                // No name matches
                            }
                            // No warppoints and/or no teleporters and/or no name matches

                            // Adds a warppoint with the specified name and the player's location
                            ConfigEditor.set("Warppoints." + specifiedName, player.getLocation());
                            player.sendMessage("§aAdded warppoint §6" + specifiedName + "§a!");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not add the warppoint!");
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("remove")) {
                        // /warppoint remove [Name]
                        try {

                            String warppointName = args[1];

                            // Warppoint exists check
                            if (!ConfigEditor.containsPath("Warppoints." + warppointName)) {
                                sender.sendMessage("§cThe warppoint §e" + warppointName + " §cdoesn't exist!");
                                return true;
                            }

                            ConfigEditor.clearPath("Warppoints." + warppointName);
                            sender.sendMessage("§aRemoved warppoint §6" + warppointName + "§a!");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not remove the warppoint §e" + args[1] + "§c!");
                            return false;
                        }
                    } else if (args[0].equalsIgnoreCase("override")) {
                        // /warppoint override [Name]
                        try {

                            // Sender player check
                            if (!(sender instanceof Player player)) {
                                sender.sendMessage("§cYou are no player!");
                                return true;
                            }

                            String warppointName = args[1];

                            // Warppoint existance check
                            if (!ConfigEditor.containsPath("Warppoints." + warppointName)) {
                                player.sendMessage("§cA warppoint with this name doesn't exist!");
                                return true;
                            }

                            // Adds the player's location to the warppoint with the specified name
                            ConfigEditor.set("Warppoints." + warppointName, player.getLocation());
                            player.sendMessage("§aOverrode the location of warppoint §6" + warppointName + "§a!");

                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not override the warppoint §e" + args[1] + "§c!");
                            return false;
                        }
                    } else {
                        sender.sendMessage("§cWrong arguments!");
                        return false;
                    }

                default:
                    // Wrong amount of arguments
                    sender.sendMessage("§cWrong amount of arguments!");
                    return false;
            }
        } catch (Exception e) {
            return false;
        }

    }

}
