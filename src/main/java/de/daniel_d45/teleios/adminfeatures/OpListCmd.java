/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Objects;


public class OpListCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
            return true;
        }

        // Specifies /oplist
        if (args.length == 0) {

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the target is no player.");
                return true;
            }

            // Sender already op check
            if (player.isOp()) {
                player.sendMessage("§cYou are already an operator!");
                MessageMaster.sendExitMessage("HealCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the target is already an operator.");
                return true;
            }

            try {
                String[] ListedOps = Objects.requireNonNull(ConfigEditor.getSectionKeys("OPList")).toArray(new String[0]);
                boolean match = false;
                for (String current : ListedOps) {
                    if (current.equals(player.getName())) {
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    throw new Exception();
                }

                player.setOp(true);
                player.sendMessage("§aYou are now an operator!");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                return true;
            } catch (Exception e) {
                player.sendMessage("§cCouldn't make you an operator!");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "couldn't make this player an operator!");
                return true;
            }
        }

        // Specifies /oplist add [Name]
        if (args.length >= 2 && args[0].equals("add")) {

            // Sender permission check
            if (!sender.hasPermission("teleios.adminfeatures.oplistAdd")) {
                sender.sendMessage("§cMissing permissions!");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender dosen't have the needed permissions.");
                return true;
            }

            ConfigEditor.set("OPList." + args[1], 1);
            //sender.sendMessage(Objects.requireNonNull(ConfigEditor.getSectionKeys("OPList")).toArray(new String[0]));

            try {
                boolean match = false;
                String[] ops = Objects.requireNonNull(ConfigEditor.getSectionKeys("OPList")).toArray(new String[0]);
                for (String current : ops) {
                    if (current.equals(args[1])) {
                        match = true;
                        break;
                    }
                }
                if (!match) {
                    throw new Exception();
                }

            } catch (Exception e) {
                // Exception ocurred when setting or querying the value
                sender.sendMessage("§cCouldn't add this player to the op list!");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "couldn't add this player to the op list.");
                return true;
            }

            sender.sendMessage("§aAdded player §6" + args[1] + "§a to the OP list.");
            MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
            return true;
        }

        // Specifies /oplist remove|delete [Name]
        if (args.length >= 2 && (args[0].equals("remove") || args[0].equals("delete"))) {

            // Sender permission check
            if (!sender.hasPermission("teleios.adminfeatures.oplistAdd")) {
                sender.sendMessage("§cMissing permissions!");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender dosen't have the needed permissions.");
                return true;
            }

            // Target on OP list check
            if (ConfigEditor.get("OPList." + args[1]) == null) {
                sender.sendMessage("§cThis player is not on the OP list!");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "this player is not on the OP list.");
                return true;
            }

            try {
                ConfigEditor.set("OPList." + args[1], null);
                sender.sendMessage("§aRemoved player §6" + args[1] + "§a from the OP list.");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                return true;
            } catch (Exception e) {
                sender.sendMessage("§cCouldn't remove this player from the op list!");
                MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "couldn't remove this player from the op list!");
                return true;
            }

        }

        // Wrong arguments
        sender.sendMessage("§cWrong arguments!");
        MessageMaster.sendExitMessage("ChatclearCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments");
        return false;
    }

}
