/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.MessageMaster;
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
                MessageMaster.sendWarningMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            switch (args.length) {
                case 0:
                    // Specifies /setblocksperpearl
                    int blocksPerPearl;

                    try {
                        blocksPerPearl = (int) ConfigEditor.get("BlocksPerPearl");
                    } catch (Exception e) {
                        sender.sendMessage("§cThe BlocksPerPearl argument in the config file is invalid!");
                        MessageMaster.sendWarningMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the BlocksPerPearl argument is invalid.");
                        return true;
                    }

                    // Tells the sender the value of the BlocksPerPearl argument
                    sender.sendMessage("§aYou can currently warp §6" + blocksPerPearl + "§a blocks per ender pearl.");
                    MessageMaster.sendWarningMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the BlocksPerPearl argument is invalid.");
                    return true;
                case 1:
                    // Specifies /setblocksperpearl [Amount]
                    try {

                        // Sender permission check
                        if (!sender.hasPermission("teleios.bettergameplay.setblocksperpearl")) {
                            sender.sendMessage("§cMissing Permissions!");
                            MessageMaster.sendWarningMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender doesn't have the needed permissions.");
                            return true;
                        }

                        int bpp = Integer.parseInt(args[0]);

                        // Invalid argument check
                        if (bpp <= 0) {
                            sender.sendMessage("§cWrong arguments!");
                            MessageMaster.sendWarningMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                            return false;
                        }

                        // Sets the BlocksPerPearl argument to the specified value
                        ConfigEditor.set("BlocksPerPearl", bpp);
                        sender.sendMessage("§aThe BlocksPerPearl have been set to §6" + bpp + "§a.");
                        MessageMaster.sendSuccessMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§cWrong argument!");
                        MessageMaster.sendWarningMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the first argument is not an integer.");
                        return false;
                    } catch (Exception e) {
                        sender.sendMessage("§cCould not execute the command correctly!");
                        MessageMaster.sendFailMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                default:
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendWarningMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("SetblocksperpearlCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
