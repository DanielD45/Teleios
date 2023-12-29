/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class EnderchestCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
                             @Nonnull String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("BetterGameplay.EnderchestCommand")) {
                sender.sendMessage("§cThis command is not active.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                return true;
            }

            // Specifies /enderchest
            player.openInventory(player.getEnderChest());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
