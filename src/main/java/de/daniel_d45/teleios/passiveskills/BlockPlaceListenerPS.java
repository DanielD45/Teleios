/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;


public class BlockPlaceListenerPS implements Listener {

    // TODO: Care for falling blocks

    /**
     * Method fires when a block is placed and logs the block as placed.
     *
     * @param event [BlockPlaceEvent]
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlacePS(BlockPlaceEvent event) {
        try {

            // TODO: Only log a limited amount of blocks at a time and override?
            // Activationstate check
            if (!ConfigEditor.isActive("PassiveSkills.All")) {
                MessageMaster.sendWarningMessage("BlockPlaceListenerPS", "onBlockPlacePS(" + event + ")", "the PassiveSkills segment is not active.");
                return;
            }

            Block block = event.getBlockPlaced();
            String entryName = block.getWorld().getName() + "," + block.getX() + "," + block.getY() + "," + block.getZ();
            // Logs the block as placed
            ConfigEditor.set("PlacedBlocks." + entryName, block.getLocation());

            MessageMaster.sendSuccessMessage("BlockPlaceListenerPS", "onBlockPlacePS(" + event + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("BlockPlaceListenerPS", "onBlockPlacePS(" + event + ")", e);
        }
    }

}
