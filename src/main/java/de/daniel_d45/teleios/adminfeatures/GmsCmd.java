/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class GmsCmd implements CommandExecutor {

    // Unbreakable (2023-12-30)
    GameMode gameMode = GameMode.SURVIVAL;
    String gameModeName = gameMode.toString().toLowerCase();

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

        // /gms
        if (args.length == 0) {

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are not a player!");
                return true;
            }

            // Player gamemode check
            if (player.getGameMode() == gameMode) {
                player.sendMessage("§cYou are already in " + gameModeName + " mode!");
                return true;
            }

            // Changes gamemode
            player.setGameMode(gameMode);
            player.sendMessage("§aYour gamemode has been set to §6" + gameModeName + "§a!");
            return true;
        }

        // /gms [Player] ...

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);

        if (target != sender) {

            // Target online check
            if (target == null) {
                sender.sendMessage("§cPlayer §6" + targetName + "§c is not online!");
                return true;
            }

            // Target gamemode check
            if (target.getGameMode() == gameMode) {
                sender.sendMessage("§6" + targetName + "§c is already in " + gameModeName + " mode!");
                return true;
            }

            // Changes target's gamemode
            target.setGameMode(gameMode);
            target.sendMessage("§aYour gamemode has been set to §6" + gameModeName + "§a!");
            sender.sendMessage("§6" + target.getName() + "§a's gamemode has been set to §6" + gameModeName + "§a!");
            return true;
        }
        else {
            // The sender targets themselves

            Player player = (Player) sender;

            // Player gamemode check
            if (player.getGameMode() == gameMode) {
                sender.sendMessage("§cYou are already in " + gameModeName + " mode!");
                return true;
            }

            // Changes player's gamemode
            player.setGameMode(gameMode);
            player.sendMessage("§aYour gamemode has been set to §6" + gameModeName + "§a!");
            return true;
        }

    }

}
