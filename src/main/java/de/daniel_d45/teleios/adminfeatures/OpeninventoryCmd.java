/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
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


public class OpeninventoryCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendExitMessage("OpeninventoryCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendExitMessage("OpenInventoryCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // Specifies /openinventory [Player]
            try {

                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    player.sendMessage("§cThis player is not online!");
                    MessageMaster.sendExitMessage("OpenInventoryCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the specified player is not online.");
                    return true;
                }

                // TODO: Change inventory name to the target's name
                player.openInventory(target.getInventory());

                MessageMaster.sendExitMessage("OpenInventoryCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
                return true;
            } catch (Exception e) {
                player.sendMessage("§cWrong arguments!");
                MessageMaster.sendExitMessage("OpenInventoryCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong arguments.");
                return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("OpenInventoryCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

}
