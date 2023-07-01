/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;


public class ArtificialInventoryClickLst implements Listener {

    /**
     * This listener cancels the item movement in artificial inventories.
     *
     * @param event [InventoryClickEvent] The event that triggered the listener.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onArtificialInventoryClick(InventoryClickEvent event) {
        try {

            Inventory inventory = event.getClickedInventory();

            // TODO: Necessary?
            // No inventory check
            if (inventory == null) {
                MessageMaster.sendExitMessage("ArtificialInventoryClickListener", "onArtificialInventoryClick(" + event + ")", "no inventory clicked.");
                return;
            }

            if (inventory.getHolder() instanceof ArtificialInventory) {
                // Cancels interaction with artificial inventories
                event.setCancelled(true);
                MessageMaster.sendExitMessage("ArtificialInventoryClickListener", "onArtificialInventoryClick(" + event + ")", "success");
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("ArtificialInventoryClickListener", "onArtificialInventoryClick(" + event + ")", e);
        }
    }

}
