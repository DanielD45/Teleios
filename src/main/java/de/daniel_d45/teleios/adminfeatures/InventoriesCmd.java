/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalFunctions;
import de.daniel_d45.teleios.core.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import javax.annotation.Nonnull;


public class InventoriesCmd implements CommandExecutor {

    // TODO: Fix inventory is not recognised when reloading
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

        switch (args.length) {
            case 0:
                // /inventories
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

                        return true;
                    } catch (NullPointerException e) {
                        sender.sendMessage("§eThere are no inventories yet! Add one by using §6/inventories create [name] [rows]§e!");
                        return true;
                    }
                }
                else if (args[0].equalsIgnoreCase("clear")) {
                    try {

                        // Inventories existance check
                        if (ConfigEditor.getSectionKeys("Inventories") == null) {
                            sender.sendMessage("§cThere are no inventories!");
                            return true;
                        }

                        // Iterates through the inventories
                        for (String current : ConfigEditor.getSectionKeys("Inventories")) {
                            ConfigEditor.clearPath("Inventories." + current);
                        }

                        sender.sendMessage("§aAll inventories have been removed!");
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("§cCould not clear all inventories!");
                        return false;
                    }
                }
                else {
                    sender.sendMessage("§cWrong arguments!");
                    return false;
                }
            case 2:
                // /inventories open|remove [Name]
                if (args[0].equalsIgnoreCase("open")) {
                    // /inventories remove [Name]

                    if (!(sender instanceof Player player)) {
                        sender.sendMessage("§cYou are no player!");
                        return true;
                    }

                    Inventory inventory = (Inventory) ConfigEditor.get("Inventories." + args[1]);

                    if (inventory == null) {
                        player.sendMessage("§cCould not find the inventory §6" + args[1] + "§c!");
                        return true;
                    }

                    player.openInventory(inventory);
                    return true;
                }
                // /inventories remove [Name]
                else if (args[0].equalsIgnoreCase("remove")) {

                    String name = args[1];

                    if (ConfigEditor.getSectionKeys("Inventories") == null) {
                        sender.sendMessage("§cThere are no inventories!");
                        return true;
                    }

                    // Iterates through the inventory names
                    for (String current : ConfigEditor.getSectionKeys("Inventories")) {
                        if (current.equals(name)) {
                            ConfigEditor.clearPath("Inventories." + name);
                            sender.sendMessage("§aRemoved the inventory §6" + name + "§a!");
                            return true;
                        }
                    }

                    sender.sendMessage("§cCould not find the inventory §6" + name + "§c!");
                    return true;
                }
                else {
                    sender.sendMessage("§cWrong arguments!");
                    return false;
                }
            case 3:
                // /inventories create [Name] [Rows]
                if (args[0].equalsIgnoreCase("create")) {

                    String name = args[1];

                    // Inventory unique name check
                    if (ConfigEditor.containsPath("Inventories." + name)) {
                        sender.sendMessage("§cAn inventory with this name already exists!");
                        return true;
                    }

                    int rows = Integer.parseInt(args[2]);

                    // Creates the inventory
                    Inventory inventory = InventoryManager.createNormalInv(rows, name);
                    ConfigEditor.set("Inventories." + name, inventory);

                    sender.sendMessage("§aCreated inventory §6" + name + "§a!");
                    return true;

                }
                else {
                    sender.sendMessage("§cWrong arguments!");
                    return false;
                }
            case 4:
                // /inventories modify access|name [InventoryName] [NewName]
                if (args[0].equalsIgnoreCase("modify")) {

                    // /inventories modify access
                    if (args[0].equalsIgnoreCase("access")) {
                        // TODO: Implement

                    }
                    else {

                    }
                }
                else {
                    sender.sendMessage("§cWrong arguments!");
                    return false;
                }
                break;
            default:
                sender.sendMessage("§cWrong amount of arguments!");
                return false;
        }
        return false;
    }

}
