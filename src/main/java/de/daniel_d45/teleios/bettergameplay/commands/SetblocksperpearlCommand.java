/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay.commands;

import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.MessageMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;


public class SetblocksperpearlCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("BetterGameplay.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendSkipMessage("SetblocksperpearlCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the command is deactivated.");
                return true;
            }

            // Sender permission check
            if (!sender.hasPermission("teleios.bettergameplay.setblocksperpearl")) {
                sender.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("SetblocksperpearlCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender doesn't have the needed permissions.");
                return true;
            }

            switch (args.length) {
                case 0:
                    // Specifies /setblocksperpearl
                    try {

                        // TODO Add Exception handling when BlocksPerPeal has a cursed value
                        // Tells the sender the value of the BlocksPerPearl argument
                        sender.sendMessage("§aYou can currently teleport §6" + ConfigEditor.get("BlocksPerPearl") + "§a blocks per ender pearl.");
                        return true;
                    } catch (Exception e) {
                        sender.sendMessage("§cCould not execute the command correctly!");
                        MessageMaster.sendFailMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                case 1:
                    // Specifies /setblocksperpearl [Amount]
                    try {

                        int bpp = Integer.parseInt(args[0]);

                        // Valid argument check
                        if (bpp < 1) {
                            sender.sendMessage("§cWrong arguments!");
                            MessageMaster.sendSkipMessage("SetblocksperpearlCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong arguments.");
                            return false;
                        }

                        ConfigEditor.set("BlocksPerPearl", bpp);
                        sender.sendMessage("§aThe BlocksPerPearl have been set to §6" + bpp + "§a.");
                        MessageMaster.sendSuccessMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§cWrong argument!");
                        MessageMaster.sendSkipMessage("SetblocksperpearlCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the first argument is not an integer.");
                        return false;
                    } catch (Exception e) {
                        sender.sendMessage("§cCould not execute the command correctly!");
                        MessageMaster.sendFailMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                default:
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendSkipMessage("SetblocksperpearlCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}