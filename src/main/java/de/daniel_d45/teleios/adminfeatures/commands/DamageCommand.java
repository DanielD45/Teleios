/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures.commands;

import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.MessageMaster;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class DamageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendSkipMessage("DamageCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the command is deactivated.");
                return true;
            }

            // Permission check
            if (!sender.hasPermission("teleios.adminfeatures.damage")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("DamageCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender doesn't have the needed permissions.");
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
                            MessageMaster.sendSkipMessage("DamageCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender is not a player.");
                            return true;
                        }

                        // Player gamemode check
                        if (wrongGamemode(player)) {
                            player.sendMessage("§cCould not damage you as you are not in a suitable gamemode!");
                            MessageMaster.sendSkipMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player is not in a suitable gamemode.");
                            return true;
                        }

                        // Reducing player health
                        if (player.getHealth() - amount <= 0) {
                            player.damage(player.getMaxHealth());
                            player.sendMessage("§4You killed yourself!");
                        }
                        else {
                            player.damage(amount);
                            player.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
                        }
                        MessageMaster.sendSuccessMessage("DamageCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;

                    } catch (Exception e) {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("DamageCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
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
                            MessageMaster.sendSkipMessage("HealCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the specified player is not online.");
                            return true;
                        }

                        if (target != sender) {

                            // Target gamemode check
                            if (wrongGamemode(target)) {
                                target.sendMessage("§cCould not heal your target as it is not in a suitable gamemode!");
                                MessageMaster.sendSkipMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player is not in a suitable gamemode.");
                                return true;
                            }

                            // Reducing player health
                            if (target.getHealth() - amount <= 0) {
                                target.damage(target.getMaxHealth());
                                target.sendMessage("§4You've been killed!");
                                sender.sendMessage("§4You killed player §6" + target.getName() + "§4!");
                            }
                            else {
                                target.damage(amount);
                                target.sendMessage("§4You've been damaged by §6" + amount + " §4hp!");
                                sender.sendMessage("§4You damaged player §6" + target.getName() + " by §6" + amount + " §4hp!");
                            }

                        }
                        else {
                            // The sender is the target

                            Player player = (Player) sender;

                            // Player gamemode check
                            if (wrongGamemode(player)) {
                                player.sendMessage("§cCould not heal you as you are not in a suitable gamemode!");
                                MessageMaster.sendSkipMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player is not in a suitable gamemode.");
                                return true;
                            }

                            // Reduce player health
                            if (player.getHealth() - amount <= 0) {
                                player.damage(player.getMaxHealth());
                                player.sendMessage("§4You've been killed!");
                            }
                            else {
                                player.damage(amount);
                                player.sendMessage("§4You damaged yourself by §6" + amount + " §4hp!");
                            }

                        }

                        MessageMaster.sendSuccessMessage("DamageCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("DamageCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
                        return false;
                    }
                default:
                    // Wrong amount of arguments
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendSkipMessage("SkillsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("DamageCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
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

            MessageMaster.sendSuccessMessage("HealCommand", "rightGamemode(" + player + ")");
            return !rGM;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("HealCommand", "rightGamemode(" + player + ")", e);
            return true;
        }
    }

}
