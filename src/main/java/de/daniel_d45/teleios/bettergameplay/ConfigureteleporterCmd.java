/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.ItemBuilder;
import de.daniel_d45.teleios.core.RecipeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class ConfigureteleporterCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                sender.sendMessage("§cThis function is not active!");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                return true;
            }

            // Checks if the item lore is right
            if (!player.getItemInHand().getItemMeta().getLore().equals(RecipeManager.getTeleporterRecipe().getResult().getItemMeta().getLore())) {
                sender.sendMessage("§cYou have to hold a teleporter in your hand!");
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
                            return true;
                        }

                        // Checks for invalid names that can cause problems
                        if (teleporterName.equalsIgnoreCase("list")) {
                            player.sendMessage("§cThat name is invalid!");
                            return true;
                        }

                        ItemStack teleporterNew = new ItemBuilder(teleporter).setName("§5" + teleporterName).build();
                        player.setItemInHand(teleporterNew);
                        return true;
                    } catch (Exception e) {
                        return false;
                    }
                default:
                    // Wrong amount of arguments
                    sender.sendMessage("§cWrong amount of arguments!");
                    return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

}
