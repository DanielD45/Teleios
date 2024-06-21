/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalFunctions;
import de.daniel_d45.teleios.core.InventoryManager;
import de.daniel_d45.teleios.core.RecipeManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;


public class TeleporterPlaceLst implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onTeleporterPlace(BlockPlaceEvent event) {

        Block block = event.getBlockPlaced();

        // Block material check
        if (!block.getType().equals(Material.END_PORTAL_FRAME)) {
            return;
        }

        Player player = event.getPlayer();
        Location loc = block.getLocation();
        loc.setYaw(player.getLocation().getYaw());
        ItemStack item = event.getItemInHand();
        String itemName = InventoryManager.getCleanString(item.getItemMeta().getDisplayName());
        List<String> itemLore = item.getItemMeta().getLore();
        String standardName = InventoryManager.getCleanString(RecipeManager.getTeleporterRecipe().getResult().getItemMeta().getDisplayName());

        // Item lore check
        if (!GlobalFunctions.betterEquals(RecipeManager.getTeleporterRecipe().getResult().getItemMeta().getLore(), itemLore)) {
            return;
        }

        // Activationstate check
        if (!ConfigEditor.isActive("BetterGameplay.Teleporters")) {
            event.setCancelled(true);
            player.sendMessage("§cThis function is not active.");
            return;
        }

        // Teleporter hasn't been renamed check
        if (itemName.equals(standardName)) {
            event.setCancelled(true);
            player.sendMessage("§cChange the teleporter's name with the §6/configureteleporter §ccommand before placing it!");
            return;
        }

        // Filters invalid teleporter names
        if (itemName.equals("list")) {
            event.setCancelled(true);
            player.sendMessage("§cThis teleporter name is invalid!");
            return;
        }

        Set<String> warppointNames = ConfigEditor.getSectionKeys("Warppoints");
        Set<String> teleporterNames = ConfigEditor.getSectionKeys("Teleporters");

        if (warppointNames != null) {
            // Checks for warppoints with the same name
            for (String currentWPName : warppointNames) {
                if (currentWPName.equalsIgnoreCase(itemName)) {
                    // Name match
                    event.setCancelled(true);
                    player.sendMessage("§cA warppoint with this name already exists!");
                    return;
                }
            }
            // No warppoint name match
        }
        // No warppoints or no warppoint name match

        if (teleporterNames != null) {
            // Checks for teleporters with the same name
            for (String currentTPName : teleporterNames) {
                if (currentTPName.equalsIgnoreCase(itemName)) {
                    // Name match
                    event.setCancelled(true);
                    player.sendMessage("§cA teleporter with this name already exists!");
                    return;
                }
            }
            // No name matches
        }
        // No warppoints and/or no teleporters and/or no name matches
        ConfigEditor.set("Teleporters." + itemName, loc);
        player.sendMessage("§aTeleporter §6" + itemName + " §ahas been added.");
    }

}
