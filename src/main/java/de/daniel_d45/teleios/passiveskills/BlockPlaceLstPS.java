/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.ConfigEditor;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockPlaceLstPS implements Listener {

    // TODO: Care for falling blocks

    /**
     * Method fires when a block is placed and logs the block as placed.
     *
     * @param event [BlockPlaceEvent]
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlacePS(BlockPlaceEvent event) {

        // TODO: Only log a limited amount of blocks at a time and override?
        // Activationstate check
        if (!ConfigEditor.isActive("PassiveSkills.All")) {
            return;
        }

        Block block = event.getBlockPlaced();
        String entryName = block.getWorld().getName() + "," + block.getX() + "," + block.getY() + "," + block.getZ();
        // Logs the block as placed
        ConfigEditor.set("PlacedBlocks." + entryName, block.getLocation());

    }

}
