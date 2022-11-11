/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.InventoryManager;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;


public class InventoriesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            switch (args.length) {
                case 0:
                    // Specifies /inventories
                    args = new String[1];
                    args[0] = "list";
                case 1:
                    // Specifes /inventories list|clear|remove
                    if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("remove")) {
                        try {

                            String[] inventories = ConfigEditor.getSectionKeys("Inventories").toArray(new String[0]);

                            // Inventory existance check
                            if (inventories.length <= 0) {
                                sender.sendMessage("§eThere are no inventories yet! Add one by using §6/inventories create [name] [rows]§e!");
                                MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "there are no inventories.");
                            }

                            // Creates a string of inventories
                            sender.sendMessage("§a--------------------");
                            sender.sendMessage("§aExisting inventories:");

                            StringBuilder message = new StringBuilder();

                            // Iterates through the inventories
                            for (int i = 0; i < inventories.length; i++) {

                                message.append("§6").append(inventories[i]);

                                if (i < inventories.length - 1) {
                                    message.append("§a, ");
                                }
                            }

                            sender.sendMessage(message.toString());
                            sender.sendMessage("§a--------------------");

                            MessageMaster.sendSuccessMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (NullPointerException e) {
                            sender.sendMessage("§eThere are no inventories yet! Add one by using §6/inventories create [name] [rows]§e!");
                            MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "there are no inventories.");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not list all inventories!");
                            MessageMaster.sendFailMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                            return false;
                        }

                    }
                    else if (args[0].equalsIgnoreCase("clear")) {
                        try {

                            // Inventories existance check
                            if (ConfigEditor.getSectionKeys("Inventories") == null) {
                                sender.sendMessage("§cThere are no inventories!");
                                MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "there are no inventories.");
                                return true;
                            }

                            // Iterates through the inventories
                            for (String current : ConfigEditor.getSectionKeys("Inventories")) {
                                ConfigEditor.clearPath("Inventories." + current);
                            }

                            sender.sendMessage("§aAll inventories have been removed!");
                            MessageMaster.sendSuccessMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCould not clear all inventories!");
                            MessageMaster.sendFailMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), clearing all inventories", e);
                            return false;
                        }
                    }
                    else {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                        return false;
                    }
                case 2:
                    // Specifies /inventories open|remove [Name]
                    if (args[0].equalsIgnoreCase("open")) {
                        // Specifies /inventories remove [Name]

                        if (!(sender instanceof Player player)) {
                            sender.sendMessage("§cYou are no player!");
                            MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                            return true;
                        }

                        try {

                            Inventory inventory = (Inventory) ConfigEditor.get("Inventories." + args[1]);

                            if (inventory == null) {
                                player.sendMessage("§cCould not find the inventory §6" + args[1] + "§c!");
                                MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "inventory doesn't exist.");
                                return true;
                            }

                            player.openInventory(inventory);

                            MessageMaster.sendSuccessMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            player.sendMessage("§cCould not open the inventory §6" + args[1] + "§c!");
                            MessageMaster.sendFailMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                            return false;
                        }

                    }
                    // Specifies /inventories remove [Name]
                    else if (args[0].equalsIgnoreCase("remove")) {

                        String name = args[1];

                        if (ConfigEditor.getSectionKeys("Inventories") == null) {
                            sender.sendMessage("§cThere are no inventories!");
                            MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "there are no inventories.");
                            return true;
                        }

                        // Iterates through the inventory names
                        for (String current : ConfigEditor.getSectionKeys("Inventories")) {
                            if (current.equals(name)) {
                                ConfigEditor.clearPath("Inventories." + name);
                                sender.sendMessage("§aRemoved the inventory §6" + name + "§a!");
                                MessageMaster.sendSuccessMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                                return true;
                            }
                        }

                        sender.sendMessage("§cCould not find the inventory §6" + name + "§c!");
                        MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "couldn't find an inventory with this name.");
                        return true;
                    }
                    else {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                        return false;
                    }
                case 3:
                    // Specifies /inventories create [Name] [Rows]
                    if (args[0].equalsIgnoreCase("create")) {

                        String name = args[1];

                        // Inventory unique name check
                        if (ConfigEditor.containsPath("Inventories." + name)) {
                            sender.sendMessage("§cAn inventory with this name already exists!");
                            MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "an inventory with this name already exists.");
                            return true;
                        }

                        try {

                            int rows = Integer.parseInt(args[2]);

                            // Creates the inventory
                            Inventory inventory = InventoryManager.createNormalInventory(rows, name);
                            ConfigEditor.set("Inventories." + name, inventory);

                            sender.sendMessage("§aCreated inventory §6" + name + "§a!");
                            MessageMaster.sendSuccessMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        } catch (Exception e) {
                            sender.sendMessage("§cCouldn't create an inventory with the arguments §6name = " + name + "§c, §6rows = " + args[2] + "§c!");
                            MessageMaster.sendFailMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), creating an inventory", e);
                            return false;
                        }
                    }
                    else {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                        return false;
                    }
                case 4:
                    // Specifies /inventories modify access|name [InventoryName] [NewName]
                    if (args[0].equalsIgnoreCase("modify")) {

                        // Specifies /inventories modify access
                        if (args[0].equalsIgnoreCase("access")) {
                            // TODO: Implement

                        }
                        else {

                        }
                    }
                    else {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                        return false;
                    }
                    break;
                default:
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendWarningMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong amount of arguments.");
                    return false;
            }

            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoriesCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
