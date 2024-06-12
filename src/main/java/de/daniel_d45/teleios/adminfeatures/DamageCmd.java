/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.GlobalMethods;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class DamageCmd implements CommandExecutor {

    // Unbreakable (2023-12-30)
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label,
                             @Nonnull String[] args) {

        if (GlobalMethods.cmdOffCheck("AdminFeatures.All", sender)) return true;

        double amount;

        // /damage
        if (args.length == 0) return GlobalMethods.wrongAmountofArgs(sender);

        // /damage <Amount>
        if (args.length == 1) {

            try {
                amount = GlobalMethods.trimDouble(Double.parseDouble(args[0]), 0, Double.MAX_VALUE);
                if (amount == 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                // The input is not a double
                sender.sendMessage("§cInvalid number!");
                return false;
            }

            GlobalMethods.getDouble(args[0], );

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are not a player!");
                return true;
            }

            // Player gamemode check
            if (wrongGamemode(player)) {
                player.sendMessage("§cYou are not in a suitable gamemode!");
                return true;
            }

            // Reduces player health
            if (player.getHealth() - amount <= 0) {
                player.damage(player.getMaxHealth());
                player.sendMessage("§4You killed yourself!");
                return true;
            } else {
                player.damage(amount);
                player.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
                return true;
            }
        }

        // /damage <Player> <Amount> ...

        String targetName = args[0];
        Player target = Bukkit.getPlayerExact(targetName);

        try {
            amount = GlobalMethods.trimDouble(Double.parseDouble(args[1]), 0, Double.MAX_VALUE);
            if (amount == 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            // The input is not a suitable double
            sender.sendMessage("§cInvalid number!");
            return false;
        }

        if (target != sender) {

            // Target online check
            if (target == null) {
                sender.sendMessage("§cPlayer §6" + targetName + "§c is not online!");
                return true;
            }

            // Target gamemode check
            if (wrongGamemode(target)) {
                target.sendMessage("§6" + targetName + "§c is not in a suitable gamemode!");
                return true;
            }

            // Reduces target health
            if (target.getHealth() - amount <= 0) {
                target.damage(target.getMaxHealth());
                target.sendMessage("§4You've been killed!");
                sender.sendMessage("§4You killed §6" + target.getName() + "§4!");
                return true;
            } else {
                target.damage(amount);
                target.sendMessage("§4You've been damaged by §6" + amount + " §4hp!");
                sender.sendMessage("§4You damaged §6" + target.getName() + "§4 by §6" + amount + "§4 hp!");
                return true;
            }

        } else {
            // The sender targets themselves

            Player player = (Player) sender;

            // Player gamemode check
            if (wrongGamemode(player)) {
                player.sendMessage("§cYou are not in a suitable gamemode!");
                return true;
            }

            // Reduces player health
            if (player.getHealth() - amount <= 0) {
                player.damage(player.getMaxHealth());
                player.sendMessage("§4You killed yourself!");
                return true;
            } else {
                player.damage(amount);
                player.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
                return true;
            }
        }
    }


    /**
     * Returns whether the specified player is in the wrong gamemode for health manipulation
     */
    private boolean wrongGamemode(Player player) {
        return player.getGameMode() != GameMode.SURVIVAL && player.getGameMode() != GameMode.ADVENTURE;
    }

}
