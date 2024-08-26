/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.InventoryManager;
import de.daniel_d45.teleios.core.ItemBuilder;
import de.daniel_d45.teleios.core.RecipeManager;
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


public class PlayerInteractWithTeleporterLst implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractTeleporter(PlayerInteractEvent event) {

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
            return;
        }

        // Block type check
        if (!block.getType().equals(Material.END_PORTAL_FRAME)) {
            return;
        }

        Set<String> teleporterNames;
        try {
            teleporterNames = Objects.requireNonNull(ConfigEditor.getSectionKeys("Teleporters"));
        } catch (NullPointerException e) {
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
                continue;
            }

            // Teleporter match check
            if (currentX == blockX && currentY == blockY && currentZ == blockZ) {

                // Is right click with ender eye check
                if (action.equals(Action.RIGHT_CLICK_BLOCK) && itemType.equals(Material.ENDER_EYE)) {
                    event.setCancelled(true);
                    return;
                }

                // Is not left-click check
                if (!action.equals(Action.LEFT_CLICK_BLOCK)) {
                    return;
                }

                event.setCancelled(true);
                // Opens the "Pick up teleporter?" inventory
                player.openInventory(getPickupTeleporterInventory("§5" + current));

                return;
            }
        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPickupTeleporterInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();

        // TODO: Make more specific? (&& event.getInventory() instanceof ArtificialInventory)
        if (!(event.getView().getTitle().equals("§0Pick up teleporter?"))) {
            return;
        }

        String teleporterName = InventoryManager.getCleanString(inventory.getItem(4).getItemMeta().getDisplayName());

        // YES ITEM
        if (item.equals(InventoryManager.getYesItem())) {

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
                    return;
                }
            }

            // NO ITEM
        }
        else if (item.equals(InventoryManager.getNoItem())) {
            player.closeInventory();
        }

    }

    private Inventory getPickupTeleporterInventory(String teleporterName) {
        try {

            Inventory inv = InventoryManager.createNoInteractionInv(1, "§0Pick up teleporter?");

            inv.setItem(0, InventoryManager.getYesItem());
            inv.setItem(4, getTeleporterItem(teleporterName));
            inv.setItem(8, InventoryManager.getNoItem());

            InventoryManager.fillEmptySlots(inv);
            return inv;
        } catch (Exception e) {
            return InventoryManager.getErrorInventory();
        }
    }

    private ItemStack getTeleporterItem(String teleporterName) {
        try {
            ItemStack item = new ItemBuilder(RecipeManager.getTeleporterRecipe().getResult()).setName(teleporterName).build();
            return item;
        } catch (Exception e) {
            return InventoryManager.getErrorItem();
        }
    }

}
