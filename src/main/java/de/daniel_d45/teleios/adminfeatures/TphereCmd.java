/*
 2020-2025
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class TphereCmd implements CommandExecutor {

    // TODO: Unbreakable
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // Is command active check SC-1
        if (GlobalFunctions.inactiveCmdCheck("AdminFeatures.All", sender)) return true;

        // /tphere
        if (args.length == 0) return false;

        // /tphere <Player> ...
        // sender -> player SC-1
        Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
        if (player == null) return true;

        // Gets target player SC-1
        Player target = GlobalFunctions.introduceTargetPlayer(args[0], sender);
        if (target == null) return true;

        // Target sender check
        if (target == sender) {
            player.sendMessage("§cCan't teleport you to yourself!");
            return true;
        }

        target.teleport(player.getLocation());
        player.sendMessage("§aTeleported §6" + target.getName() + "§a to you!");
        return true;
    }

}
