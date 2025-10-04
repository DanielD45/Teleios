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


public class UnmuteCmd implements CommandExecutor {

    // Unbreakable 2025-07-03
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // Is command active check SC-1
        if (GlobalFunctions.inactiveCmdCheck("AdminFeatures.All", sender)) return true;

        // /unmute
        if (args.length == 0) {

            // sender -> player SC-1
            Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
            if (player == null) return true;

            String name = player.getName();

            // Is player muted check
            if (!ConfigEditor.containsPath("MutedPlayers." + name)) {
                player.sendMessage("§bYou are not muted.");
                return true;
            }

            // Removes the player from the muted players list
            ConfigEditor.set("MutedPlayers." + name, null);
            player.sendMessage("§aYou have been unmuted!");
            return true;
        }

        // /unmute <Player> ...

        // Gets target player SC-1
        Player target = GlobalFunctions.introduceTargetPlayer(args[0], sender);
        if (target == null) return true;

        String name = target.getName();

        // Is target muted check
        if (!ConfigEditor.containsPath("MutedPlayers." + name)) {
            sender.sendMessage("§6" + name + " §cis not muted!");
            return true;
        }

        // Removes the player from the muted players list
        ConfigEditor.set("MutedPlayers." + name, null);
        sender.sendMessage("§aUnmuted §6" + name + "§a!");
        target.sendMessage("§aYou have been unmuted!");
        return true;
    }

}
