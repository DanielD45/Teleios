/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class GmspCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
                             @Nonnull String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }

        switch (args.length) {
            case 0:
                // Specifies /gma

                // Sender player check
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("§cYou are no player!");
                    return true;
                }

                // Player gamemode check
                if (player.getGameMode() == GameMode.SPECTATOR) {
                    player.sendMessage("§cYou are already in spectator mode!");
                    return true;
                }

                // Changes gamemode to spectator
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage("§aYour gamemode has been set to §6spectator§a!");
                return true;
            case 1:
                // Specifies /gma [Player]

                Player target = Bukkit.getPlayer(args[0]);

                if (target != sender) {
                    // The sender is not the target

                    // Target online check
                    if (target == null) {
                        sender.sendMessage("§cThis player is not online!");
                        return true;
                    }

                    // Target gamemode check
                    if (target.getGameMode() == GameMode.SPECTATOR) {
                        sender.sendMessage("§cYour target is already in spectator mode!");
                        return true;
                    }

                    // Sets the target in spectator mode
                    target.setGameMode(GameMode.SPECTATOR);
                    target.sendMessage("§aYour gamemode has been set to §6spectator§a!");
                    sender.sendMessage("§6" + target.getName() + "§a's gamemode has been set to §6spectator§a!");

                } else {
                    // The sender targets himself
                    Player player = (Player) sender;

                    // Player gamemode check
                    if (player.getGameMode() == GameMode.SPECTATOR) {
                        sender.sendMessage("§cYou are already in spectator mode!");
                        return true;
                    }

                    // Changes gamemode to spectator
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage("§aYour gamemode has been set to §6spectator§a!");
                }
                return true;
            default:
                // Wrong amount of arguments
                sender.sendMessage("§cWrong amount of arguments!");
                return false;
        }

    }

}
