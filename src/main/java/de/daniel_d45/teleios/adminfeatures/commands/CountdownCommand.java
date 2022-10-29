/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures.commands;

import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.MessageMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class CountdownCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendSkipMessage("CountdownCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + args + "), the command is deactivated.");
                return true;
            }

            // Permission check
            if (!sender.hasPermission("teleios.adminfeatures.countdown")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("CountdownCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + args + "), the sender doesn't have the needed permissions.");
                return true;
            }

            switch (args.length) {
                case 2:
                    // Specifies /countdown [Duration] [EndMessage]
                    try {
                        //TODO

                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("§cWrong arguments!");
                        MessageMaster.sendSkipMessage("CountdownCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + args + "), wrong arguments.");
                        return false;
                    }

                default:
                    // Wrong amount of arguments
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendSkipMessage("CountdownCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + args + "), wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("CountdownCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + args + ")", e);
            return false;
        }

    }

}
