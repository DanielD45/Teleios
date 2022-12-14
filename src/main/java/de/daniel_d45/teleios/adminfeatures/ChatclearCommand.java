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

import java.util.Arrays;


public class ChatclearCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            MessageMaster.sendWarningMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
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
