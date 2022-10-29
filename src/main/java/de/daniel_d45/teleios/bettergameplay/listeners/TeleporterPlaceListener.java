/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay.listeners;

import de.daniel_d45.teleios.core.program.*;
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


public class TeleporterPlaceListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onTeleporterPlace(BlockPlaceEvent event) {
        try {

            Block block = event.getBlockPlaced();

            // Block material check
            if (!block.getType().equals(Material.END_PORTAL_FRAME)) {
                MessageMaster.sendSkipMessage("TeleporterPlaceListener", "Skipped method onTeleporterPlace(" + event + "), wrong block type.");
                return;
            }

            Player player = event.getPlayer();
            Location loc = block.getLocation();
            ItemStack item = event.getItemInHand();
            String itemName = InventoryManager.getCleanString(item.getItemMeta().getDisplayName());
            List<String> itemLore = item.getItemMeta().getLore();
            String standardName = InventoryManager.getCleanString(RecipeManager.getTeleporterRecipe().getResult().getItemMeta().getDisplayName());

            // Item lore check
            if (!BetterMethods.betterEquals(RecipeManager.getTeleporterRecipe().getResult().getItemMeta().getLore(), itemLore)) {
                MessageMaster.sendSkipMessage("TeleporterPlaceListener", "Skipped method onTeleporterPlace(" + event + "), wrong item lore.");
                return;
            }

            // Activationstate check
            if (!ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                event.setCancelled(true);
                player.sendMessage("§cThis function is not active.");
                MessageMaster.sendSkipMessage("WarppointCommand", "Skipped method onTeleporterPlace(" + event + "), the function is deactivated.");
                return;
            }

            // The teleporter has to be renamed
            if (itemName.equals(standardName)) {
                event.setCancelled(true);
                player.sendMessage("§cChange the teleporter's name with the §6/configureteleporter §ccommand before placing it!");
                MessageMaster.sendSkipMessage("TeleporterPlaceListener", "onTeleporterPlace(" + event + "), this teleporter has not been renamed.");
                return;
            }

            // Only allows teleporters with a unique name
            if (ConfigEditor.containsPath("Teleporters." + itemName) || ConfigEditor.containsPath("Warppoints." + itemName)) {
                event.setCancelled(true);
                player.sendMessage("§cA teleporter or warppoint with this name already exists!");
                MessageMaster.sendSkipMessage("TeleporterPlaceListener", "onTeleporterPlace(" + event + "), a teleporter or warppoint with that name already exists.");
            }
            else {
                ConfigEditor.set("Teleporters." + itemName, loc);
                player.sendMessage("§aTeleporter §6" + itemName + " §ahas been added.");
                MessageMaster.sendSuccessMessage("TeleporterPlaceListener", "onTeleporterPlace(" + event + ")");
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("TeleporterPlaceListener", "onTeleporterPlace(" + event + ")", e);
        }
    }

}
