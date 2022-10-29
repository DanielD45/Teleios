/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures.commands;

import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.MessageMaster;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;


public class ChatclearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            MessageMaster.sendSkipMessage("ChatclearCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the command is deactivated.");
            return true;
        }

        // Permission check
        if (!sender.hasPermission("teleios.adminfeatures.chatclear")) {
            sender.sendMessage("§cMissing Permissions!");
            MessageMaster.sendSkipMessage("ChatclearCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender doesn't have the needed permissions.");
            return true;
        }

        // Specifies /chatclear
        for (int i = 0; i <= 60; ++i) {
            Bukkit.broadcastMessage("");
        }

        Bukkit.broadcastMessage("§aThe chat has been cleared!");
        MessageMaster.sendSuccessMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
        return true;
    }

}
