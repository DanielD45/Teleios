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


public class GmsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the command is deactivated.");
                return true;
            }

            // Permission check
            if (!sender.hasPermission("teleios.adminfeatures.gma")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender doesn't have the needed permissions.");
                return true;
            }

            switch (args.length) {
                case 0:
                    // Specifies /gma
                    try {

                        // Sender player check
                        if (!(sender instanceof Player player)) {
                            sender.sendMessage("§cYou are no player!");
                            MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender is not a player.");
                            return true;
                        }

                        // Player gamemode check
                        if (player.getGameMode() == GameMode.SURVIVAL) {
                            player.sendMessage("§cYou are already in survival mode!");
                            MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player is already in survival mode.");
                            return true;
                        }

                        // Changes gamemode to survival
                        player.setGameMode(GameMode.SURVIVAL);
                        player.sendMessage("§aYour gamemode has been set to §6survival§a!");
                        MessageMaster.sendSuccessMessage("GmsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;

                    } catch (Exception e) {
                        MessageMaster.sendFailMessage("GmsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                case 1:
                    // Specifies /gma [Player]
                    try {

                        Player target = Bukkit.getPlayer(args[0]);

                        if (target != sender) {
                            // The sender is not the target

                            // Target online check
                            if (target == null) {
                                sender.sendMessage("§cThis player is not online!");
                                MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the specified player is not online.");
                                return true;
                            }

                            // Target gamemode check
                            if (target.getGameMode() == GameMode.SURVIVAL) {
                                sender.sendMessage("§cYour target is already in survival mode!");
                                MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the target is already in survival mode.");
                                return true;
                            }

                            // Sets the target in survival mode
                            target.setGameMode(GameMode.SURVIVAL);
                            target.sendMessage("§aYour gamemode has been set to §6survival§a!");
                            sender.sendMessage("§6" + target.getName() + "§a's gamemode has been set to §6survival§a!");
                            MessageMaster.sendSuccessMessage("GmcCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");

                        }
                        else {
                            // The sender targets himself
                            Player player = (Player) sender;

                            // Player gamemode check
                            if (player.getGameMode() == GameMode.SURVIVAL) {
                                sender.sendMessage("§cYou are already in survival mode!");
                                MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player is already in survival mode.");
                                return true;
                            }

                            // Sets the player in survival mode
                            player.setGameMode(GameMode.SURVIVAL);
                            player.sendMessage("§aYour gamemode has been set to §6survival§a!");
                            MessageMaster.sendSuccessMessage("GmsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        }
                        return true;
                    } catch (Exception e) {
                        MessageMaster.sendFailMessage("GmsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                default:
                    // Wrong amount of arguments
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendSkipMessage("GmsCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("GmsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
