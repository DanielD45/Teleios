/*
 2020-2025
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
import java.util.Set;


public class InventoriesCmd implements CommandExecutor {

    // TODO: Fix inventory is not recognised when reloading
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // Is command active check SC-1
        if (GlobalFunctions.inactiveCmdCheck("AdminFeatures.All", sender)) return true;

        // /inventories, /inventories list
        if (args.length == 0 || args[0].equalsIgnoreCase("list")) {

            Set<String> existing_invs = ConfigEditor.getSectionKeys("Inventories");

            // Inventories exist check
            if (existing_invs == null || existing_invs.isEmpty()) {
                sender.sendMessage("§eThere are no inventories yet! Add one by using §6/inventories create <name> <rows>§e!");
                return true;
            }

            StringBuilder message = new StringBuilder("§bExisting inventories: ");

            int i = 0;
            for (String curr_inv_name : existing_invs) {
                i++;
                message.append("§6").append(curr_inv_name);

                if (i < existing_invs.size()) {
                    message.append("§b, ");
                }
            }

            sender.sendMessage(message.toString());
            return true;
        }

        // /inventories clear
        if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {

            sender.sendMessage("§aDo you really want to §6remove all inventories§a? Then use §6/inventories clear yes§a.");
            return true;
        }

        // /inventories clear yes
        if (args.length == 2 && args[0].equalsIgnoreCase("clear") && args[1].equalsIgnoreCase("yes")) {

            Set<String> existing_invs = ConfigEditor.getSectionKeys("Inventories");

            // Inventories exist check
            if (existing_invs == null || existing_invs.isEmpty()) {
                sender.sendMessage("§eThere are no inventories!");
                return true;
            }

            // Iterates through the inventories
            for (String curr_inv : existing_invs) {
                ConfigEditor.clearPath("Inventories." + curr_inv);
            }

            sender.sendMessage("§aAll inventories have been removed!");
            return true;
        }

        // /inventories open <Name>
        if (args.length >= 2 && args[0].equalsIgnoreCase("open")) {

            // sender -> player SC-1
            Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
            if (player == null) return true;

            // Try-gets config entry SC-1
            Inventory inventory = (Inventory) GlobalFunctions.getConfigObject("Inventories." + args[1], "§cCould not find the inventory §6" + args[1] + "§c!", player);
            if (inventory == null) return true;

            player.openInventory(inventory);
            return true;
        }

        // /inventories remove <Name>
        if (args.length >= 2 && args[0].equalsIgnoreCase("remove")) {

            // Try-gets config section keys SC-1
            Set<String> allInvs = GlobalFunctions.getConfigKeys("Inventories", "§cThere are no inventories!", sender);
            if (allInvs == null) return true;

            String name = args[1];

            // Iterates through the inventories
            for (String currInv : allInvs) {
                if (currInv.equals(name)) {
                    ConfigEditor.clearPath("Inventories." + name);
                    sender.sendMessage("§aRemoved the inventory §6" + name + "§a!");
                    return true;
                }

                sender.sendMessage("§cCould not find the inventory §6" + name + "§c!");
                return true;
            }
        }

        // /inventories create <Name> <Rows>
        if (args.length >= 3 && args[0].equalsIgnoreCase("create")) {

            String name = args[1];

            // Inventory unique name check
            if (ConfigEditor.containsPath("Inventories." + name)) {
                sender.sendMessage("§cAn inventory with the name §6" + name + "§c already exists!");
                return true;
            }

            // Force-Gets int SC-1
            int rows = GlobalFunctions.introduceInt(args[2], 1, Integer.MAX_VALUE, sender);
            if (rows == Integer.MIN_VALUE) return false;

            // Creates the inventory
            Inventory inventory = InventoryManager.createNormalInv(rows, name);
            ConfigEditor.set("Inventories." + name, inventory);

            sender.sendMessage("§aCreated inventory §6" + name + "§a with §6" + rows + "§a rows!");
            return true;
        }

        // TODO
        /*
        // /inventories modify <InventoryName> name <NewName>
        if (args.length >= 4 && args[0].equalsIgnoreCase("modify") && args[2].equalsIgnoreCase("name")) {

        }
        */

        // /inventories modify <InventoryName> access add <PlayerName|@a>

        // /inventories modify <InventoryName> access remove <PlayerName|@a>

        return false;
    }

}
