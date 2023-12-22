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


public class HealCmd implements CommandExecutor {

    // TODO: Make doubles possible as input
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }

        switch (args.length) {
            case 0:
                // Specifies /heal

                // Sender player check
                if (!(sender instanceof Player player)) {
                    sender.sendMessage("§cYou are no player!");
                    return true;
                }

                // Player gamemode check
                if (wrongGamemode(player)) {
                    player.sendMessage("§cCould not heal you as you are not in a suitable gamemode!");
                    return true;
                }

                // Fully heals the player
                player.setHealth(player.getMaxHealth());
                player.sendMessage("§aYou've been fully healed!");

                return true;

            case 1:
                // Specifies /heal [Player]
                try {
                    // TODO: heal the specified player, not the sender

                    double amount = Double.parseDouble(args[0]);

                    // Sender player check
                    if (!(sender instanceof Player player)) {
                        sender.sendMessage("§cYou are no player!");
                        return true;
                    }

                    // Player gamemode check
                    if (wrongGamemode(player)) {
                        player.sendMessage("§cCould not heal you as you are not in a suitable mode!");
                        return true;
                    }

                    // Heals the player
                    if (player.getHealth() + amount > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                        player.sendMessage("§aYou fully healed yourself!");
                    }
                    else {
                        player.setHealth(player.getHealth() + amount);
                        player.sendMessage("§aYou healed yourself by §6" + amount + " §ahp!");
                    }

                    return true;
                } catch (NumberFormatException e) {
                    // Specifies /heal [Amount], args[0] is not a double.
                    // Specifies /heal [Player]

                    Player target = Bukkit.getPlayer(args[0]);

                    // Target online check
                    if (target == null) {
                        sender.sendMessage("§cThis player is not online!");
                        return true;
                    }

                    // Target sender check
                    if (target != sender) {

                        if (wrongGamemode(target)) {
                            sender.sendMessage("§cCould not heal your target as it is not in a suitable gamemode!");
                            return true;
                        }

                        target.setHealth(target.getMaxHealth());
                        target.sendMessage("§aYou've been fully healed!");
                        sender.sendMessage("§aYou fully healed §6" + target.getName() + "§a!");
                    }
                    else {
                        // The sender himself is the target.

                        Player player = (Player) sender;

                        // Player gamemode check
                        if (wrongGamemode(player)) {
                            player.sendMessage("§cCould not heal you as you are not in a suitable gamemode!");
                            return true;
                        }

                        // Heals the player
                        player.setHealth(player.getMaxHealth());
                        player.sendMessage("§aYou fully healed yourself!");
                    }
                }
                return true;
            case 2:
                // Specifies /heal [Player]|[Amount]
                // TODO: revise

                Player target = Bukkit.getPlayer(args[0]);
                double amount = Double.parseDouble(args[1]);

                // Target online check
                if (target == null) {
                    sender.sendMessage("§cThis player is not online!");
                    return true;
                }

                // Target sender check
                if (target != sender) {

                    if (wrongGamemode(target)) {
                        sender.sendMessage("§cCould not heal your target as it is not in a suitable gamemode!");
                        return true;
                    }

                    // Heals the target
                    if (target.getHealth() + amount > target.getMaxHealth()) {
                        target.setHealth(target.getMaxHealth());
                        target.sendMessage("§aYou've been fully healed!");
                        sender.sendMessage("§aYou fully healed §6" + target.getName() + "§a!");
                    }
                    else {
                        target.setHealth(target.getHealth() + amount);
                        target.sendMessage("§aYou've been healed by §6" + amount + " §ahp!");
                        sender.sendMessage("§aYou healed " + target.getName() + " by §6" + amount + " §ahp!");
                    }

                }
                else {
                    // The sender is the target

                    Player player = (Player) sender;

                    if (wrongGamemode(player)) {
                        sender.sendMessage("§cCould not heal you as you are not in a suitable gamemode!");
                        return true;
                    }

                    if (player.getHealth() + amount > player.getMaxHealth()) {
                        player.setHealth(player.getMaxHealth());
                        player.sendMessage("§aYou fully healed yourself!");
                    }
                    else {
                        player.setHealth(player.getHealth() + amount);
                        player.sendMessage("§aYou healed yourself by §6" + amount + " §ahp!");
                    }

                }

                return true;
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
        return !(player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE);
    }

}
