/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Set;


public class PlayerInteractWithTeleporterListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractWithTeleporter(PlayerInteractEvent event) {
        try {

            Player player = event.getPlayer();
            Material itemType = event.getMaterial();
            Action action = event.getAction();
            Block block;
            double blockX;
            double blockY;
            double blockZ;
            try {
                block = Objects.requireNonNull(event.getClickedBlock());
                blockX = block.getX();
                blockY = block.getY();
                blockZ = block.getZ();
            } catch (Exception e) {
                MessageMaster.sendWarningMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")", "the block or its location is invalid.");
                return;
            }

            // Block type check
            if (!block.getType().equals(Material.END_PORTAL_FRAME)) {
                MessageMaster.sendInfoMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")", "The block is not an end portal frame.");
                return;
            }

            Set<String> teleporterNames;
            try {
                teleporterNames = Objects.requireNonNull(ConfigEditor.getSectionKeys("Teleporters"));
            } catch (NullPointerException e) {
                MessageMaster.sendInfoMessage(this.getClass().getName(), "onPlayerInteractWithTeleporter(" + event + ")", "The \"Teleporters\" path in the config file is empty.");
                MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")");
                return;
            }

            Location currentLoc;
            double currentX;
            double currentY;
            double currentZ;
            // Iterates through the teleporters
            for (String current : teleporterNames) {

                try {
                    currentLoc = Objects.requireNonNull((Location) ConfigEditor.get("Teleporters." + current));
                    currentX = currentLoc.getX();
                    currentY = currentLoc.getY();
                    currentZ = currentLoc.getZ();
                } catch (Exception e) {
                    MessageMaster.sendWarningMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")", "teleporter \"" + ConfigEditor.get("Teleporters." + current) + "\" has an invalid Location.");
                    continue;
                }

                // Teleporter match check
                if (currentX == blockX && currentY == blockY && currentZ == blockZ) {

                    // Is right click with ender eye check
                    if (action.equals(Action.RIGHT_CLICK_BLOCK) && itemType.equals(Material.ENDER_EYE)) {
                        event.setCancelled(true);
                        MessageMaster.sendWarningMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")", "cannot put an ender eye in a teleporter.");
                        return;
                    }

                    // Is not left-click check
                    if (!action.equals(Action.LEFT_CLICK_BLOCK)) {
                        MessageMaster.sendWarningMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")", "wrong action type.");
                        return;
                    }

                    event.setCancelled(true);
                    // Opens the "Pick up teleporter?" inventory
                    player.openInventory(getPickupTeleporterInventory("§5" + current));

                    MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")");
                    return;
                }
            }

            MessageMaster.sendWarningMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")", "wrong block.");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("PlayerInteractWithTeleporterListener", "onPlayerInteractWithTeleporter(" + event + ")", e);
        }
    }

    @EventHandler
    public void onPickupTeleporterInventoryClick(InventoryClickEvent event) {
        try {

            Player player = (Player) event.getWhoClicked();
            Inventory inventory = event.getClickedInventory();
            ItemStack item = event.getCurrentItem();

            if (event.getView().getTitle().equals("§0Pick up teleporter?")) {

                String teleporterName = InventoryManager.getCleanString(inventory.getItem(4).getItemMeta().getDisplayName());

                // YES ITEM
                if (item.equals(getYesItem())) {

                    // Iterates through the teleporters
                    for (String current : ConfigEditor.getSectionKeys("Teleporters")) {

                        if (teleporterName.equals(current)) {
                            // There is a teleporter with this name

                            Location location = (Location) ConfigEditor.get("Teleporters." + current);
                            World world = location.getWorld();

                            // Deletes the teleporter entry
                            ConfigEditor.clearPath("Teleporters." + current);

                            // Drops a teleporter item
                            world.dropItemNaturally(location, inventory.getItem(4));

                            // Destroys the teleporter
                            location.getBlock().setType(Material.AIR);

                            player.closeInventory();

                            MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "onPickupTeleporterInventoryClick(" + event + "), player chose \"Yes\"");
                            return;
                        }
                    }

                    // NO ITEM
                }
                else if (item.equals(getNoItem())) {

                    player.closeInventory();
                    MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "onPickupTeleporterInventoryClick(" + event + "), player chose \"No\"");
                }
            }

            MessageMaster.sendWarningMessage("PlayerInteractWithTeleporterListener", "onPickupTeleporterInventoryClick(" + event + ")", "wrong item clicked.");
        } catch (NullPointerException e) {
            MessageMaster.sendWarningMessage("PlayerInteractWithTeleporterListener", "onPickupTeleporterInventoryClick(" + event + ")", "no item has been clicked.");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("PlayerInteractWithTeleporterListener", "onPickupTeleporterInventoryClick(" + event + ")", e);
        }
    }

    private Inventory getPickupTeleporterInventory(String teleporterName) {
        try {

            Inventory inv = InventoryManager.createArtificialInventory(1, "§0Pick up teleporter?");

            inv.setItem(0, getYesItem());
            inv.setItem(4, getTeleporterItem(teleporterName));
            inv.setItem(8, getNoItem());

            InventoryManager.fillEmptySlots(inv);

            MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "getDestroyTeleporterInventory()");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("PlayerInteractWithTeleporterListener", "getDestroyTeleporterInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    private ItemStack getTeleporterItem(String teleporterName) {
        try {

            ItemStack item = new ItemBuilder(RecipeManager.getTeleporterRecipe().getResult()).setName(teleporterName).build();

            MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "getTeleporterItem(" + teleporterName + ")");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("PlayerInteractWithTeleporterListener", "getTeleporterItem(" + teleporterName + ")", e);
            return InventoryManager.getErrorItem();
        }
    }

    private ItemStack getYesItem() {
        try {

            ItemStack item = new ItemBuilder(Material.GREEN_CONCRETE, 1).setName("§aYes").build();

            MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "getYesItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("PlayerInteractWithTeleporterListener", "getYesItem()", e);
            return InventoryManager.getErrorItem();
        }
    }

    private ItemStack getNoItem() {
        try {

            ItemStack item = new ItemBuilder(Material.RED_CONCRETE, 1).setName("§cNo").build();

            MessageMaster.sendSuccessMessage("PlayerInteractWithTeleporterListener", "getNoItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("PlayerInteractWithTeleporterListener", "getNoItem()", e);
            return InventoryManager.getErrorItem();
        }
    }

}
