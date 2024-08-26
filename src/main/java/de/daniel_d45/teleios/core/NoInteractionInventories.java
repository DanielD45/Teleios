/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.checkerframework.checker.nullness.qual.NonNull;


/**
 * Is used to identify the inventories you can't exchange items with.
 */
public class NoInteractionInventories implements InventoryHolder {

    // unused
    @NonNull
    @Override
    public Inventory getInventory() {
        return InventoryManager.getErrorInventory();
    }

}
