/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class UnmuteCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }

        // Sender player check
        if (!(sender instanceof Player player)) {
            sender.sendMessage("§cYou are no player!");
            return true;
        }

        // Specifies /unmute [Player]

        Player target = Bukkit.getPlayer(args[0]);

        // Target online check
        if (target == null) {
            player.sendMessage("§cThis player is not online!");
            return true;
        }

        // Is target muted check
        if (!ConfigEditor.containsPath("MutedPlayers." + player.getName())) {
            player.sendMessage("§6" + target.getName() + " §ais not muted!");
            return true;
        }

        // Removes the player from the muted players list
        ConfigEditor.set("MutedPlayers." + target.getName(), null);
        player.sendMessage("§aUnmuted §6" + target.getName() + "§a!");
        target.sendMessage("§aYou have been unmuted!");
        return true;
    }

}
