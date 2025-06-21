/*
 2020-2025
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Objects;


public class HealCmd implements CommandExecutor {

    // TODO: Test /heal <Amount>|<Player> & /heal <Player> <Amount> ...
    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // Is active check SC-1
        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

        // /heal
        if (args.length == 0) {
            // sender -> player SC-1
            Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
            if (player == null) return true;

            return healPlayer(player, getMaxHealth(player));
        }

        // /heal <Amount>|<Player>
        if (args.length == 1) {
            // /heal <Amount>
            if (GlobalFunctions.isDouble(args[0])) {
                // Force-gets double SC-1
                double amount = GlobalFunctions.introduceDouble(args[0], 0, Double.MAX_VALUE, sender);
                if (amount == Double.NEGATIVE_INFINITY) return false;
                if (amount == 0) return GlobalFunctions.invalidNumber(sender, args[0]);

                // sender -> player SC-1
                Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
                if (player == null) return true;

                return healPlayer(player, amount);
            }
            // /heal <Player>
            else {
                // Gets target player SC-1
                Player target = GlobalFunctions.introduceTargetPlayer(args[0], sender);
                if (target == null) return true;

                if (target == sender) {
                    Player player = (Player) sender;
                    return healPlayer(player, getMaxHealth(player));
                }
                else {
                    return healTarget(sender, target, getMaxHealth(target));
                }
            }
        }

        // /heal <Player> <Amount> ...
        // Force-gets double SC-1
        double amount = GlobalFunctions.introduceDouble(args[1], 0, Double.MAX_VALUE, sender);
        if (amount == Double.NEGATIVE_INFINITY) return false;
        if (amount == 0) return GlobalFunctions.invalidNumber(sender, args[1]);

        // Gets target player SC-1
        Player target = GlobalFunctions.introduceTargetPlayer(args[0], sender);
        if (target == null) return true;

        if (target == sender) {
            Player player = (Player) sender;
            return healPlayer(player, amount);
        }
        else {
            return healTarget(sender, target, amount);
        }

    }

    private double getMaxHealth(Player player) {
        return Objects.requireNonNull(player.getAttribute(Attribute.MAX_HEALTH)).getValue();
    }

    @SuppressWarnings("SameReturnValue")
    private boolean healPlayer(Player player, double amount) {
        if (player.getHealth() == getMaxHealth(player)) {
            player.sendMessage("§aYou are already at max health!");
            return true;
        }

        if (player.getHealth() + amount >= getMaxHealth(player)) {
            player.setHealth(getMaxHealth(player));
            player.sendMessage("§aYou fully healed yourself!");
            return true;
        }
        else {
            player.setHealth(player.getHealth() + amount);
            player.sendMessage("§aYou healed yourself by §6" + amount + " §ahp!");
            return true;
        }
    }

    @SuppressWarnings("SameReturnValue")
    private boolean healTarget(CommandSender sender, Player target, double amount) {
        if (target.getHealth() == getMaxHealth(target)) {
            sender.sendMessage("§6" + target.getName() + "§a is already at max health!");
            return true;
        }

        if (target.getHealth() + amount >= getMaxHealth(target)) {
            target.setHealth(getMaxHealth(target));
            target.sendMessage("§aYou've been fully healed!");
            sender.sendMessage("§aYou fully healed §6" + target.getName() + "§a!");
            return true;
        }
        else {
            target.setHealth(target.getHealth() + amount);
            target.sendMessage("§aYou've been healed by §6" + amount + "§a hp!");
            sender.sendMessage("§aYou healed §6" + target.getName() + "§a by §6" + amount + "§a hp!");
            return true;
        }

    }

}
