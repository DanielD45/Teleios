/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay.commands;

import de.daniel_d45.teleios.core.util.ConfigEditor;
import de.daniel_d45.teleios.core.util.InventoryManager;
import de.daniel_d45.teleios.core.util.MessageMaster;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class WarppouchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Command activationstate check
            if (!ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the command is deactivated.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender is not a player.");
                return true;
            }

            // Player permission check
            if (!player.hasPermission("teleios.bettergameplay.warppouch")) {
                player.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender doesn't have the needed permissions.");
                return true;
            }

            switch (args.length) {
                case 0:
                    // Specifies /warppouch
                    try {

                        int storedEPs = (int) ConfigEditor.get("Warppouch." + player.getName());

                        if (storedEPs == 1) {
                            player.sendMessage("§aThere is §6" + storedEPs + " §aender pearl in your warppouch.");
                        }
                        else {
                            player.sendMessage("§aThere are §6" + storedEPs + " §aender pearls in your warppouch.");
                        }

                        MessageMaster.sendSuccessMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (NullPointerException e) {
                        player.sendMessage("§aThere are §60 §aender pearls in your warppouch.");
                        MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the Warppouch path doesn't exist for this player.");
                        return true;
                    } catch (Exception e) {
                        player.sendMessage("§cCould not view the warppouch!");
                        MessageMaster.sendFailMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), viewing the warppouch", e);
                        return false;
                    }
                case 1:
                    // Specifies /warppouch view|show
                    if (args[0].equalsIgnoreCase("view") || args[0].equalsIgnoreCase("show")) {
                        try {

                            int storedEPs = (int) ConfigEditor.get("Warppouch." + player.getName());

                            if (storedEPs == 1) {
                                player.sendMessage("§aThere is §6" + storedEPs + " §aender pearl in your warppouch.");
                            }
                            else {
                                player.sendMessage("§aThere are §6" + storedEPs + " §aender pearls in your warppouch.");
                            }

                            MessageMaster.sendSuccessMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (NullPointerException e) {
                            player.sendMessage("§aThere are §60 §aender pearls in your warppouch.");
                            MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the Warppouch path doesn't exist for this player.");
                            return true;
                        } catch (Exception e) {
                            player.sendMessage("§cCould not view the warppouch!");
                            MessageMaster.sendFailMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), viewing the warppouch", e);
                            return false;
                        }

                    }
                    else {
                        // Wrong arguments
                        player.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
                        return false;
                    }
                case 2:
                    // Specifies /warppouch deposit|put [Amount]
                    if (args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("put")) {
                        try {

                            int specifiedAmount = Integer.parseInt(args[1]);

                            // SpecifiedAmount valid check
                            if (specifiedAmount <= 0) {
                                player.sendMessage("§cInvalid amount of ender pearls!");
                                MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), invalid amount of ender pearls.");
                                return true;
                            }

                            // TODO: Limit warppouch size?
                            if (((int) ConfigEditor.get("Warppouch." + player.getName()) + specifiedAmount) > 1000000) {
                                specifiedAmount = 1000000 - (int) ConfigEditor.get("Warppouch." + player.getName());
                            }

                            // Removes the ender pearls from the player and returns the actual removed amount of items
                            int transferredItems = InventoryManager.removeItemsPlayerSoft(player.getInventory(), new ItemStack(Material.ENDER_PEARL), specifiedAmount);

                            // Ender pearl amount check
                            if (transferredItems == 0) {
                                player.sendMessage("§cYou don't have enough ender pearls in your inventory!");
                                MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player doesn't have enough ender pearls.");
                                return true;
                            }

                            // Adds the specified amount of ender pearls to the player's warppouch
                            ConfigEditor.set("Warppouch." + player.getName(), (int) ConfigEditor.get("Warppouch." + player.getName()) + transferredItems);

                            if (specifiedAmount == 1) {
                                player.sendMessage("§aDeposited §6" + transferredItems + " §aender pearl.");
                            }
                            else {
                                player.sendMessage("§aDeposited §6" + transferredItems + " §aender pearls.");
                            }

                            player.sendMessage("§aYou now have §6" + ConfigEditor.get("Warppouch." + player.getName()) + " §aender pearls in your warppouch.");
                            MessageMaster.sendSuccessMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            player.sendMessage("§cCould not deposit in your warppouch!");
                            MessageMaster.sendFailMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), paying in your warppouch", e);
                            return false;
                        }
                    }
                    else {
                        player.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
                        return false;
                    }
                default:
                    player.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
