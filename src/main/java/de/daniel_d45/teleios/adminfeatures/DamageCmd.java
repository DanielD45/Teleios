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


public class DamageCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
                             @Nonnull String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }

        // Switch for arguments
        switch (args.length) {
            case 1:
                // Specifies /damage [Amount]
                try {

                    double amount = Double.parseDouble(args[0]);

                    // Sender player check
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage("§cYou are no player!");
                        return true;
                    }

                    // Player gamemode check
                    if (wrongGamemode(player)) {
                        player.sendMessage("§cCould not damage you as you are not in a suitable gamemode!");
                        return true;
                    }

                    // Reducing player health
                    if (player.getHealth() - amount <= 0) {
                        player.damage(player.getMaxHealth());
                        player.sendMessage("§4You killed yourself!");
                    } else {
                        player.damage(amount);
                        player.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
                    }
                    return true;

                } catch (Exception e) {
                    sender.sendMessage("§cWrong arguments!");
                    return false;
                }
            case 2:
                // TODO: implement /damage [Amount]
                // Specifies /damage [Player]|[Amount]
                try {

                    Player target = Bukkit.getPlayer(args[0]);
                    double amount = Double.parseDouble(args[1]);

                    // Player online check
                    if (target == null) {
                        sender.sendMessage("§cThis player is not online!");
                        return true;
                    }

                    if (target != sender) {

                        // Target gamemode check
                        if (wrongGamemode(target)) {
                            target.sendMessage("§cCould not heal your target as it is not in a suitable gamemode!");
                            return true;
                        }

                        // Reducing player health
                        if (target.getHealth() - amount <= 0) {
                            target.damage(target.getMaxHealth());
                            target.sendMessage("§4You've been killed!");
                            sender.sendMessage("§4You killed player §6" + target.getName() + "§4!");
                        } else {
                            target.damage(amount);
                            target.sendMessage("§4You've been damaged by §6" + amount + " §4hp!");
                            sender.sendMessage("§4You damaged player §6" + target.getName() + " by §6" + amount + " §4hp!");
                        }

                    } else {
                        // The sender is the target

                        Player player = (Player) sender;

                        // Player gamemode check
                        if (wrongGamemode(player)) {
                            player.sendMessage("§cCould not heal you as you are not in a suitable gamemode!");
                            return true;
                        }

                        // Reduce player health
                        if (player.getHealth() - amount <= 0) {
                            player.damage(player.getMaxHealth());
                            player.sendMessage("§4You've been killed!");
                        } else {
                            player.damage(amount);
                            player.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
                        }

                    }
                    return true;
                } catch (Exception e) {
                    sender.sendMessage("§cWrong arguments!");
                    return false;
                }
            default:
                // Wrong amount of arguments
                sender.sendMessage("§cWrong amount of arguments!");
                return false;
        }
    }

    /**
     * Returns whether the specified player is in the wrong gamemode for health manipulation.
     *
     * @param player [Player] The player to test the gamemode of.
     * @return [boolean] Whether the specified player is not in survival or adventure mode.
     */
    private boolean wrongGamemode(Player player) {
        boolean rGM = player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE;
        return !rGM;
    }

}
