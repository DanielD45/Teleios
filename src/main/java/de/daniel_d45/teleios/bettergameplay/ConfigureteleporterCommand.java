/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.ItemBuilder;
import de.daniel_d45.teleios.core.MessageMaster;
import de.daniel_d45.teleios.core.RecipeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class ConfigureteleporterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                sender.sendMessage("§cThis function is not active!");
                MessageMaster.sendWarningMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is not active.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendWarningMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // Checks if the item lore is right
            if (!player.getItemInHand().getItemMeta().getLore().equals(RecipeManager.getTeleporterRecipe().getResult().getItemMeta().getLore())) {
                sender.sendMessage("§cYou have to hold a teleporter in your hand!");
                MessageMaster.sendWarningMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the player doesn't hold a teleporter.");
                return true;
            }

            ItemStack teleporter = player.getItemInHand();

            switch (args.length) {
                case 1:
                    // Specifies /configureteleporter [Name]
                    try {

                        String teleporterName = args[0];

                        // The teleporter's name can't be longer than x characters
                        if (teleporterName.length() > 30) {
                            player.sendMessage("§cThis name is too long!");
                            MessageMaster.sendWarningMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "specified name is too long.");
                            return true;
                        }

                        // Checks for invalid names that can cause problems
                        if (teleporterName.equalsIgnoreCase("list")) {
                            player.sendMessage("§cThat name is invalid!");
                            MessageMaster.sendWarningMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "teleporter name is invalid.");
                            return true;
                        }

                        ItemStack teleporterNew = new ItemBuilder(teleporter).setName("§5" + teleporterName).build();
                        player.setItemInHand(teleporterNew);
                        MessageMaster.sendSuccessMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (Exception e) {
                        MessageMaster.sendFailMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                default:
                    // Wrong amount of arguments
                    sender.sendMessage("§cWrong amount of arguments!");
                    MessageMaster.sendWarningMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "wrong amount of arguments.");
                    return false;
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ConfigureteleporterCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }

    }

}
