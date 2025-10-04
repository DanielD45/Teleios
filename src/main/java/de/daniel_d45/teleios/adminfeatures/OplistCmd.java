/*
 2020-2025
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Set;


public class OplistCmd implements CommandExecutor {

    // Unbreakable 2025-07-03
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // Is command active check SC-1
        if (GlobalFunctions.inactiveCmdCheck("AdminFeatures.All", sender)) return true;

        // /oplist
        if (args.length == 0) {

            // sender -> player SC-1
            Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
            if (player == null) return true;

            // Try-gets config section keys SC-1
            Set<String> keys = GlobalFunctions.getConfigKeys("OPList", "§cYou are not on the OP list!", sender);
            if (keys == null) return true;

            boolean match = false;
            for (String current : keys) {
                if (current.equals(player.getName())) {
                    match = true;
                    break;
                }
            }

            // Player not on OP list check
            if (!match) {
                player.sendMessage("§cYou are not on the OP list!");
                return true;
            }

            // Sender already op check
            if (player.isOp()) {
                player.sendMessage("§cYou are already an operator!");
                return true;
            }

            // Makes player an op
            player.setOp(true);
            player.sendMessage("§aYou are now an operator!");
            return true;
        }

        // /oplist add <Name> ...
        if (args.length >= 2 && args[0].equals("add")) {

            // Sender permission check SC-1
            if (GlobalFunctions.permissionCheck(sender, "adminfeatures.oplistAdd")) return true;

            // Adds player to op list
            ConfigEditor.set("OPList." + args[1], 1);
            sender.sendMessage("§aAdded §6" + args[1] + "§a to the OP list.");
            return true;
        }

        // /oplist remove|delete <Name> ...
        if (args.length >= 2 && (args[0].equals("remove") || args[0].equals("delete"))) {

            // Sender permission check SC-1
            if (GlobalFunctions.permissionCheck(sender, "adminfeatures.oplistAdd")) return true;

            // Target on OP list check
            if (ConfigEditor.get("OPList." + args[1]) == null) {
                sender.sendMessage("§cPlayer §6" + args[1] + "§c is not on the OP list!");
                return true;
            }

            // Removes player from op list
            ConfigEditor.set("OPList." + args[1], null);
            sender.sendMessage("§aRemoved §6" + args[1] + "§a from the OP list.");
            return true;
        }

        return false;
    }

}
