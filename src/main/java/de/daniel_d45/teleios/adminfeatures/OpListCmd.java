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


public class OpListCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
                             @Nonnull String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }

        // Specifies /oplist
        if (args.length == 0) {

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                return true;
            }

            // Sender already op check
            if (player.isOp()) {
                player.sendMessage("§cYou are already an operator!");
                return true;
            }

            Set<String> keys = ConfigEditor.getSectionKeys("OPList");
            // OP list empty check
            if (keys == null) {
                player.sendMessage("§cThe OP list is empty!");
                return true;
            }

            String[] listedOps = keys.toArray(new String[0]);

            boolean match = false;
            for (String current : listedOps) {
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

            player.setOp(true);
            player.sendMessage("§aYou are now an operator!");
            return true;
        }

        // Specifies /oplist add [Name]
        if (args.length >= 2 && args[0].equals("add")) {

            // Sender permission check
            if (!sender.hasPermission("teleios.adminfeatures.oplistAdd")) {
                sender.sendMessage("§cMissing permissions!");
                return true;
            }

            ConfigEditor.set("OPList." + args[1], 1);
            sender.sendMessage("§aAdded §6" + args[1] + "§a to the OP list.");
            return true;
        }

        // Specifies /oplist remove|delete [Name]
        if (args.length >= 2 && (args[0].equals("remove") || args[0].equals("delete"))) {

            // Sender permission check
            if (!sender.hasPermission("teleios.adminfeatures.oplistAdd")) {
                sender.sendMessage("§cMissing permissions!");
                return true;
            }

            // TODO: input, exception handling
            // Target on OP list check
            if (ConfigEditor.get("OPList." + args[1]) == null) {
                sender.sendMessage("§cThis player is not on the OP list!");
                return true;
            }

            try {
                ConfigEditor.set("OPList." + args[1], null);
            } catch (Exception e) {
                sender.sendMessage("§6" + args[1] + "§c is not on the OP list!");
                return true;
            }
            sender.sendMessage("§aRemoved §6" + args[1] + "§a from the OP list.");
            return true;
        }

        return false;
    }

}
