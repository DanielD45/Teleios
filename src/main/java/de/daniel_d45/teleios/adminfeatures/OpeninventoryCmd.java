/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class OpeninventoryCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;


        if (GlobalFunctions.introduceSenderAsPlayer(sender)) return true;
        Player player = (Player) sender;

        // TODO: get input, exception handling
        // /openinventory [Player]
        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            player.sendMessage("§cThis player is not online!");
            return true;
        }

        // TODO: Change inventory name to the target's name
        player.openInventory(target.getInventory());
        return true;
    }

}
