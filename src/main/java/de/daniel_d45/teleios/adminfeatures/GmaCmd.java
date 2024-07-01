/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class GmaCmd implements CommandExecutor {

    // Unbreakable (2023-12-30)
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

        // variables for easy copying between /gmx commands
        GameMode gameMode = GameMode.ADVENTURE;
        String gameModeName = gameMode.toString();

        // /gma
        if (args.length == 0) {

            Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
            if (player == null) return true;

            if (GlobalFunctions.invalidGamemodePlayer(player, "§cYou are already in " + gameModeName + " mode!", gameMode)) return true;

            // Changes gamemode
            player.setGameMode(gameMode);
            player.sendMessage("§aYour gamemode has been set to §6" + gameModeName + "§a!");
            return true;
        }


        // /gma [Player] ...
        Player target = GlobalFunctions.introduceTargetPlayer(args[0], sender);
        if (target == null) return true;

        if (target == sender) {

            if (GlobalFunctions.invalidGamemodePlayer(target, "§cYou are already in " + gameModeName + " mode!", gameMode)) return true;

            // changes gamemode
            target.setGameMode(gameMode);
            target.sendMessage("§aYour gamemode has been set to §6" + gameModeName + "§a!");
        }
        else {

            String targetName = target.getName();

            if (GlobalFunctions.invalidGamemodeTarget(sender, target, "§6" + targetName + "§c is already in " + gameModeName + " mode!", gameMode))
                return true;

            // changes target's gamemode
            target.setGameMode(gameMode);
            target.sendMessage("§aYour gamemode has been set to §6" + gameModeName + "§a!");
            sender.sendMessage("§6" + targetName + "§a's gamemode has been set to §6" + gameModeName + "§a!");
        }
        return true;
    }

}
