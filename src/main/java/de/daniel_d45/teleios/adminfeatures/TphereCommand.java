/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class TphereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendWarningMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            // Sender permission check
            if (!sender.hasPermission("teleios.adminfeatures.tphere")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendWarningMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender doesn't have the needed permissions.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendWarningMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // Specifies /tphere [Player]
            try {

                Player target = Bukkit.getPlayer(args[0]);

                // Target online check
                if (target == null) {
                    sender.sendMessage("§cThis player is not online!");
                    MessageMaster.sendWarningMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the specified player is not online.");
                    return true;
                }

                // Target sender check
                if (target == sender) {
                    // The player wants to teleport to himself
                    player.sendMessage("§cCan't teleport you to yourself!");
                    MessageMaster.sendWarningMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender wants to teleport to himself.");
                }
                else {
                    target.teleport(player.getLocation());
                    target.sendMessage("§aTeleported §6" + target.getName() + " §ato you!");
                    MessageMaster.sendSuccessMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                }

                return true;
            } catch (Exception e) {
                MessageMaster.sendFailMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("TphereCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
