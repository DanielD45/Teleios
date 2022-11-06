/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Arrays;


public class JoinmessageCommandListener implements CommandExecutor, Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {

            // TODO: Make individual ActivationState for command
            // Tests if the joinmessage is enabled and the command is active
            if (ConfigEditor.hasValue("JoinMessage", true) && ConfigEditor.isActive("AdminFeatures.All")) {

                // Sends join message
                Player player = event.getPlayer();
                event.setJoinMessage("§6" + player.getName() + " §9joined the server.");
            }
        } catch (NullPointerException e) {
            MessageMaster.sendWarningMessage("JoinmessageCommandListener", "onPlayerJoin(" + event + ")", "the JoinMessage path in the config file doesn't exist.");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("JoinmessageCommandListener", "onPlayerJoin(" + event + ")", e);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendWarningMessage("JoinmessageCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            // Sender permission check
            if (!sender.hasPermission("teleios.adminfeatures.joinmessage")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendWarningMessage("JoinmessageCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender doesn't have the needed permissions.");
                return true;
            }

            // Specifies /joinmessage
            if (args.length == 0) {
                if (ConfigEditor.hasValue("JoinMessage", true)) {
                    sender.sendMessage("§aThe custom join message is §6enabled§a.");
                }
                else {
                    sender.sendMessage("§aThe custom join message is §6disabled§a.");
                }

                MessageMaster.sendSuccessMessage("JoinmessageCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                return true;
            }

            // Specifies /joinmessage enable|true
            if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("true")) {
                ConfigEditor.set("JoinMessage", true);
                sender.sendMessage("§aThe custom join message is now §6enabled§a!");
                MessageMaster.sendSuccessMessage("JoinmessageCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                return true;
            }

            // Specifies /joinmessage disable|false
            else if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("false")) {
                ConfigEditor.set("JoinMessage", false);
                sender.sendMessage("§aThe custom join message is now §6disabled§a!");
                MessageMaster.sendSuccessMessage("JoinmessageCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                return true;
            }

            // Wrong arguments
            sender.sendMessage("§cWrong arguments!");
            MessageMaster.sendWarningMessage("JoinmessageCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
            return false;

        } catch (Exception e) {
            MessageMaster.sendFailMessage("JoinmessageCommandListener", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
