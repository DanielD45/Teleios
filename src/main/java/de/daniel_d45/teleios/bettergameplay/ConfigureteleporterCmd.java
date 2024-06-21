/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.GlobalFunctions;
import de.daniel_d45.teleios.core.ItemBuilder;
import de.daniel_d45.teleios.core.RecipeManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;


public class ConfigureteleporterCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (GlobalFunctions.cmdOffCheck("BetterGameplay.Teleporters", sender)) return true;

        if (GlobalFunctions.introduceSenderAsPlayer(sender)) return true;
        Player player = (Player) sender;

        // /ctp
        if (args.length == 0) return false;

        List<String> lorePl = Objects.requireNonNull(player.getInventory().getItemInMainHand().getItemMeta()).getLore();
        List<String> loreTel = Objects.requireNonNull(RecipeManager.getTeleporterRecipe().getResult().getItemMeta()).getLore();

        // Player is holding teleporter check
        if (!Objects.equals(lorePl, loreTel)) {
            sender.sendMessage("§cYou have to hold a teleporter in your main hand!");
            return true;
        }

        ItemStack teleporter = player.getInventory().getItemInMainHand();

        // /configureteleporter [Name]
        String teleporterName = args[0];
        int minLen = 1;
        int maxLen = 30;
        if (GlobalFunctions.stringNotUsable(teleporterName, minLen, maxLen)) {
            // The input is too long
            player.sendMessage("§cThe name must be §6" + minLen + "§c - §6" + maxLen + "§c characters long!");
            return true;
        }

        // Checks for invalid names that can cause problems
        if (teleporterName.equalsIgnoreCase("list")) {
            player.sendMessage("§cThis name is invalid!");
            return true;
        }

        ItemStack teleporterNew = new ItemBuilder(teleporter).setName("§5" + teleporterName).build();
        player.getInventory().setItemInMainHand(teleporterNew);
        return true;
    }

}
