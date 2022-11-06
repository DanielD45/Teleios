/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.InventoryManager;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Objects;


public class WarppouchCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activationstate check
            if (!ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // Player permission check
            if (!player.hasPermission("teleios.bettergameplay.warp pouch")) {
                player.sendMessage("§cMissing Permissions!");
                MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender doesn't have the needed permissions.");
                return true;
            }

            int storedEPs;
            try {
                storedEPs = (int) Objects.requireNonNull(ConfigEditor.get("Warppouch." + player.getName()));
                if (storedEPs < 0) {
                    throw new IllegalArgumentException("The warp pouch stores a negative amount of ender pearls!");
                }
            } catch (NullPointerException | ClassCastException | IllegalArgumentException e) {
                player.sendMessage("§cYour warp pouch is invalid!");
                MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the player's warp pouch is invalid.");
                return true;
            }

            // Specifies /warppouch (view|show)
            if (args.length == 0 || args[0].equalsIgnoreCase("view") || args[0].equalsIgnoreCase("show")) {
                if (storedEPs == 1) {
                    player.sendMessage("§aThere is §6" + storedEPs + " §aender pearl in your warp pouch.");
                }
                else {
                    player.sendMessage("§aThere are §6" + storedEPs + " §aender pearls in your warp pouch.");
                }

                MessageMaster.sendSuccessMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                return true;
            }

            // Specifies /warppouch (deposit|put [Amount])
            if (args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("put")) {
                int specifiedAmount = Integer.MAX_VALUE;

                // Specifies /warppouch deposit|put [Amount]
                // Tries to get a specified amount of ender pearls
                if (args.length >= 2) {
                    try {
                        specifiedAmount = Integer.parseInt(args[1]);
                        // Is specifiedAmount valid check
                        if (specifiedAmount <= 0) {
                            player.sendMessage("§cInvalid amount of ender pearls!");
                            MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "invalid amount of ender pearls.");
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        specifiedAmount = Integer.MAX_VALUE;
                    }
                }

                // TODO: move?
                // Warp pouch size is limited to 1'000'000 ender pearls
                if ((storedEPs + specifiedAmount) > 1000000) {
                    specifiedAmount = 1000000 - storedEPs;
                    // Is specifiedAmount valid check
                    if (specifiedAmount <= 0) {
                        player.sendMessage("§cInvalid amount of ender pearls!");
                        MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "invalid amount of ender pearls.");
                        return true;
                    }
                }

                // Tries to remove the specified amount of ender pearls from the player's inventory and returns the actual amount of items removed
                int actualAmount = InventoryManager.removeItemsPlayerSoft(player.getInventory(), new ItemStack(Material.ENDER_PEARL), specifiedAmount);

                // Ender pearl amount check
                if (actualAmount == 0) {
                    player.sendMessage("§cYou don't have enough ender pearls in your inventory!");
                    MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the player doesn't have enough ender pearls.");
                    return true;
                }

                // Adds the specified amount of ender pearls to the player's warp pouch
                ConfigEditor.set("Warppouch." + player.getName(), (int) ConfigEditor.get("Warppouch." + player.getName()) + actualAmount);

                if (specifiedAmount == 1) {
                    player.sendMessage("§aDeposited §6" + actualAmount + " §aender pearl.");
                }
                else {
                    player.sendMessage("§aDeposited §6" + actualAmount + " §aender pearls.");
                }

                player.sendMessage("§aYou now have §6" + ConfigEditor.get("Warppouch." + player.getName()) + " §aender pearls in your warp pouch.");
                MessageMaster.sendSuccessMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                return true;
            }

            player.sendMessage("§cWrong arguments!");
            MessageMaster.sendWarningMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
