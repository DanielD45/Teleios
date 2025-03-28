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


public class DamageCmd implements CommandExecutor {

    // Unbreakable (2024-08-28)
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

        // /damage
        if (args.length == 0) return GlobalFunctions.wrongAmountofArgs(sender);


        // /damage <Amount>
        if (args.length == 1) {

            double amount = GlobalFunctions.introduceDouble(args[0], 0, Double.MAX_VALUE, sender);
            if (amount == Double.NEGATIVE_INFINITY) return false;
            if (amount == 0) return GlobalFunctions.invalidNumber(sender);

            Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
            if (player == null) return true;

            if (GlobalFunctions.invalidGamemodePlayer(player, "", GameMode.CREATIVE, GameMode.SPECTATOR)) return true;

            // Reduces player health
            if (player.getHealth() - amount <= 0) {
                // TODO: works? Also check other occurrences
                player.setHealth(0);
                player.sendMessage("§4You killed yourself!");
            }
            else {
                player.damage(amount);
                player.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
            }
            return true;
        }


        // /damage <Player> <Amount> ...
        Player target = GlobalFunctions.introduceTargetPlayer(args[0], sender);
        if (target == null) return true;

        double amount = GlobalFunctions.introduceDouble(args[1], 0, Double.MAX_VALUE, sender);
        if (amount == Double.NEGATIVE_INFINITY) return false;
        if (amount == 0) return GlobalFunctions.invalidNumber(sender);

        if (target == sender) {
            if (GlobalFunctions.invalidGamemodePlayer(target, "", GameMode.CREATIVE, GameMode.SPECTATOR)) return true;

            // Reduces player health
            if (target.getHealth() - amount <= 0) {
                target.setHealth(0);
                target.sendMessage("§4You killed yourself!");
            }
            else {
                target.damage(amount);
                target.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
            }
        }
        else {
            if (GlobalFunctions.invalidGamemodeTarget(sender, target, "", GameMode.CREATIVE, GameMode.SPECTATOR)) return true;

            // Reduces target health
            if (target.getHealth() - amount <= 0) {
                target.setHealth(0);
                target.sendMessage("§4You've been killed!");
                sender.sendMessage("§4You killed §6" + target.getName() + "§4!");
            }
            else {
                target.damage(amount);
                target.sendMessage("§4You've been damaged by §6" + amount + " §4hp!");
                sender.sendMessage("§4You damaged §6" + target.getName() + "§4 by §6" + amount + "§4 hp!");
            }
        }
        return true;
    }

}
