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
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Arrays;


public class MuteCommandListener implements CommandExecutor, Listener {

    @EventHandler
    public void onMutedChat(AsyncPlayerChatEvent event) {
        try {

            Player player = event.getPlayer();

            if (ConfigEditor.containsPath("MutedPlayers." + player.getName())) {

                // Mutes the player
                event.setCancelled(true);
                player.sendMessage("§cYou are muted!");
                MessageMaster.sendSuccessMessage("MuteCommandListener", "onMutedChat(" + event + ")");
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("MuteCommandListener", "onCommand(" + event + ")", e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendWarningMessage("MuteCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            // Sender permission check
            if (!sender.hasPermission("teleios.adminfeatures.mute")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendWarningMessage("MuteCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender doesn't have the needed permissions.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendWarningMessage("MuteCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // Specifies /mute [Player]
            try {

                Player target = Bukkit.getPlayer(args[0]);

                // Target online check
                if (target == null) {
                    player.sendMessage("§cThis player is not online!");
                    MessageMaster.sendWarningMessage("MuteCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the specified player is not online.");
                    return true;
                }

                // Player already muted check
                if (ConfigEditor.containsPath("MutedPlayers." + player.getName())) {
                    player.sendMessage("§6" + target.getName() + " §ais already muted!");
                    MessageMaster.sendWarningMessage("MuteCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the specified player is already muted.");
                    return true;
                }

                // Adds the target to the muted players list
                ConfigEditor.set("MutedPlayers." + target.getName(), "1");
                player.sendMessage("§aMuted §6" + target.getName() + "§a!");
                target.sendMessage("§cYou have been muted!");

                MessageMaster.sendSuccessMessage("MuteCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                return true;
            } catch (Exception e) {
                player.sendMessage("§cWrong arguments!");
                MessageMaster.sendWarningMessage("MuteCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("MuteCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
