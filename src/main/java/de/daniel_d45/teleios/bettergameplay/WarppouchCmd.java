/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalMethods;
import de.daniel_d45.teleios.core.InventoryManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;


public class WarppouchCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activationstate check
            if (!ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                sender.sendMessage("§cThis command is not active.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                return true;
            }

            int storedEPs;
            try {
                storedEPs = (int) Objects.requireNonNull(ConfigEditor.get("Warppouch." + player.getName()));
                if (storedEPs < 0) {
                    throw new IllegalArgumentException("The warp pouch stores a negative amount of ender pearls!");
                }
            } catch (NullPointerException | ClassCastException | IllegalArgumentException e) {
                player.sendMessage("§cYour warp pouch is invalid!");
                return true;
            }

            // Specifies /warppouch (view|show)
            if (args.length == 0 || args[0].equalsIgnoreCase("view") || args[0].equalsIgnoreCase("show")) {
                if (storedEPs == 1) {
                    player.sendMessage("§aThere is §6" + storedEPs + " §aender pearl in your warp pouch.");
                }
                else {
                    player.sendMessage("§aThere are §6" + storedEPs + " §aender pearls in your warp pouch.");
                }
                return true;
            }

            // Specifies /warppouch (deposit|put [Amount])
            if (args[0].equalsIgnoreCase("deposit") || args[0].equalsIgnoreCase("put")) {
                int specifiedAmount = Integer.MAX_VALUE;

                // Specifies /warppouch deposit|put [Amount]
                // Tries to get a specified amount of ender pearls
                if (args.length >= 2) {
                    try {
                        specifiedAmount = Integer.parseInt(args[1]);
                        // Is specifiedAmount valid check
                        if (specifiedAmount <= 0) {
                            player.sendMessage("§cInvalid amount of ender pearls!");
                            return true;
                        }
                    } catch (NumberFormatException e) {
                        specifiedAmount = Integer.MAX_VALUE;
                    }
                }

                // TODO: move?
                // Warp pouch size is limited to 1'000'000 ender pearls
                if ((storedEPs + specifiedAmount) > 1000000) {
                    specifiedAmount = 1000000 - storedEPs;
                    // Is specifiedAmount valid check
                    if (specifiedAmount <= 0) {
                        player.sendMessage("§cInvalid amount of ender pearls!");
                        return true;
                    }
                }

                // Tries to remove the specified amount of ender pearls from the player's inventory and returns the actual amount of items removed
                int actualAmount = InventoryManager.removeItemsPlayerSoft(player.getInventory(), new ItemStack(Material.ENDER_PEARL), specifiedAmount);

                // Ender pearl amount check
                if (actualAmount == 0) {
                    player.sendMessage("§cYou don't have enough ender pearls in your inventory!");
                    return true;
                }

                // Adds the specified amount of ender pearls to the player's warp pouch
                ConfigEditor.set("Warppouch." + player.getName(), (int) ConfigEditor.get("Warppouch." + player.getName()) + actualAmount);

                if (specifiedAmount == 1) {
                    player.sendMessage("§aDeposited §6" + actualAmount + " §aender pearl.");
                }
                else {
                    player.sendMessage("§aDeposited §6" + actualAmount + " §aender pearls.");
                }

                player.sendMessage("§aYou now have §6" + ConfigEditor.get("Warppouch." + player.getName()) + " §aender pearls in your warp pouch.");
                return true;
            }

            player.sendMessage("§cWrong arguments!");
            return false;
        } catch (Exception e) {
            GlobalMethods.sendErrorFeedback(sender);
            return false;
        }
    }

}
