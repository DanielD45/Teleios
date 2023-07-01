/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;


/**
 * This class implements the /setdebuglevel command.
 *
 * @author Daniel_D45
 */
public class SetDebugLevelCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            if (args.length == 0) {
                // Specifes /setdebuglevel
                int debugLevel;
                try {
                    debugLevel = ConfigEditor.getDebugLevel();
                } catch (Exception e) {
                    sender.sendMessage("§cThe debug level is invalid!");
                    MessageMaster.sendFailMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                    return true;
                }

                sender.sendMessage("§aThe debug level is currently §6" + debugLevel + "§a.");
                MessageMaster.sendExitMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                return true;
            }

            // Sender permission check
            if (!sender.hasPermission("teleios.core.setdebuglevel")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendExitMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender doesn't have the needed permissions.");
                return true;
            }

            // Specifies /setdebuglevel [Level]
            int newDebugLevel;
            try {
                // Switches "uppercase number" chars to their corresponding chars
                // TODO: test replacing
                args[0] = args[0].replace('=', '0');
                args[0] = args[0].replace('!', '1');
                args[0] = args[0].replace('"', '2');
                args[0] = args[0].replace('§', '3');
                args[0] = args[0].replace('$', '4');
                args[0] = args[0].replace('%', '5');
                args[0] = args[0].replace('&', '6');
                args[0] = args[0].replace('/', '7');
                args[0] = args[0].replace('(', '8');
                args[0] = args[0].replace(')', '9');

                newDebugLevel = Integer.parseInt(args[0]);
            } catch (Exception e) {
                sender.sendMessage("§cThis debug level is invalid");
                MessageMaster.sendExitMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the specified debug level is invalid.");
                return true;
            }

            if (newDebugLevel < 0) {
                newDebugLevel = 0;
            }
            else if (newDebugLevel > 10) {
                newDebugLevel = 10;
            }

            // The specified integer matches a valid DebugLevel
            ConfigEditor.set("DebugLevel", newDebugLevel);

            sender.sendMessage("§aThe debug level is now §6" + newDebugLevel + "§a.");
            MessageMaster.sendExitMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
            return true;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SetDebugLevelCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
