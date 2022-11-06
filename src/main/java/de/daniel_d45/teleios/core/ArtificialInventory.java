/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;


/**
 * This class is used to identify the inventories you can't exchange items with.
 *
 * @author Daniel_D45
 */
public class ArtificialInventory implements InventoryHolder {

    // Not used?
    @Override
    public Inventory getInventory() {
        return null;
    }

}
