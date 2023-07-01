/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class HealCmd implements CommandExecutor {

    // TODO: Make doubles possible as input
    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            switch (args.length) {
                case 0:
                    // Specifies /heal
                    try {

                        // Sender player check
                        if (!(sender instanceof Player player)) {
                            sender.sendMessage("§cYou are no player!");
                            MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the target is no player.");
                            return true;
                        }

                        // Player gamemode check
                        if (wrongGamemode(player)) {
                            player.sendMessage("§cCould not heal you as you are not in a suitable gamemode!");
                            MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the player is not in a suitable gamemode.");
                            return true;
                        }

                        // Fully heals the player
                        player.setHealth(player.getMaxHealth());
                        player.sendMessage("§aYou've been fully healed!");

                        MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                        return true;
                    } catch (Exception e) {
                        MessageMaster.sendFailMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), /heal", e);
                        return false;
                    }
                case 1:
                    // Specifies /heal [Player]
                    try {

                        double amount = Double.parseDouble(args[0]);

                        // Sender player check
                        if (!(sender instanceof Player player)) {
                            sender.sendMessage("§cYou are no player!");
                            MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player");
                            return true;
                        }

                        // Player gamemode check
                        if (wrongGamemode(player)) {
                            player.sendMessage("§cCould not heal you as you are not in a suitable mode!");
                            MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the player is not in a suitable gamemode.");
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

                        MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                        return true;
                    } catch (NumberFormatException e) {
                        // Specifies /heal [Amount], args[0] is not a double.
                        try {
                            // Specifies /heal [Player]

                            Player target = Bukkit.getPlayer(args[0]);

                            // Target online check
                            if (target == null) {
                                sender.sendMessage("§cThis player is not online!");
                                MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the specified player is not online.");
                                return true;
                            }

                            // Target sender check
                            if (target != sender) {

                                if (wrongGamemode(target)) {
                                    sender.sendMessage("§cCould not heal your target as it is not in a suitable gamemode!");
                                    MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the target is not in a suitable gamemode.");
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
                                    MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the player is not in a suitable gamemode.");
                                    return true;
                                }

                                // Heals the player
                                player.setHealth(player.getMaxHealth());
                                player.sendMessage("§aYou fully healed yourself!");
                            }

                            MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                            return true;
                        } catch (Exception e2) {
                            sender.sendMessage("§cCould not heal player §6" + args[0] + "§c!");
                            MessageMaster.sendFailMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), healing player §6" + args[0] + "§c", e2);
                            return false;
                        }
                    } catch (Exception e) {
                        sender.sendMessage("§cCould not heal you or another player!");
                        MessageMaster.sendFailMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), healing a player", e);
                        return false;
                    }
                case 2:
                    // Specifies /heal [Player]|[Amount]
                    try {

                        Player target = Bukkit.getPlayer(args[0]);
                        double amount = Double.parseDouble(args[1]);

                        // Target online check
                        if (target == null) {
                            sender.sendMessage("§cThis player is not online!");
                            MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the specified player is not online.");
                            return true;
                        }

                        // Target sender check
                        if (target != sender) {

                            if (wrongGamemode(target)) {
                                sender.sendMessage("§cCould not heal your target as it is not in a suitable gamemode!");
                                MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the target is not in a suitable gamemode.");
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
                            // The sender is the target.

                            Player player = (Player) sender;

                            if (wrongGamemode(player)) {
                                sender.sendMessage("§cCould not heal you as you are not in a suitable gamemode!");
                                MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the player is not in a suitable gamemode.");
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

                        MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                        return false;
                    }
                default:
                    // Wrong amount of arguments.
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendExitMessage("WarppointCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
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
        try {

            boolean rGM = player.getGameMode() == GameMode.SURVIVAL || player.getGameMode() == GameMode.ADVENTURE;

            MessageMaster.sendExitMessage("HealCommand", "rightGamemode(" + player + ")", "success");
            return !rGM;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("HealCommand", "rightGamemode(" + player + ")", e);
            return true;
        }
    }

}
