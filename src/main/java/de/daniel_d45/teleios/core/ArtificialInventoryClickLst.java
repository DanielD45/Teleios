/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
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

        Inventory inventory = event.getClickedInventory();

        // No inventory check
        if (inventory == null) {
            return;
        }

        if (inventory.getHolder() instanceof NoInteractionInventories) {
            event.setCancelled(true);
        }

    }

}
