/*
 2020-2024
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */
package de.daniel_d45.teleios.adminfeatures;
// TODO: assign mode -> create by targeting PLC + /makeplc or specify coords
/*
import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.InventoryManager;
import de.daniel_d45.teleios.core.ItemBuilder;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Set;


public class LootChestLst implements Listener {


    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractPLC(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block block = event.getClickedBlock();

        // TODO: Make other chest types possible? Then also change getPLCItem()...
        // Block material check
        if (block == null || !block.getType().equals(Material.CHEST)) return;

        Chest chest = (Chest) block.getState();
        String chestKey = chest.getX() + ", " + chest.getY() + ", " + chest.getZ();
        Set<String> plcKeys = ConfigEditor.getSectionKeys("LootChests");

        // Are there PLCs check
        if (plcKeys == null) return;

        // Is block PLC check
        boolean match = false;
        for (String currentKey : plcKeys) {
            if (currentKey.equals(chestKey)) {
                // Key match
                match = true;
                break;
            }
        }

        // Is player in assigning mode check
        if (playersInAssignMode.contains(player.getUniqueId())) {

            // Is block already PLC check
            if (match) {
                // This block is already a PLC
                event.setCancelled(true);
                player.sendMessage("§cThis block is already a Loot Chest!");
                playersInAssignMode.remove(player.getUniqueId());
                player.sendMessage("§aAssigning mode has been §6deactivated§a.");
                return;
            }
            // BLOCK IS NOT A PLC

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                player.sendMessage("§cThis function is not active.");
                playersInAssignMode.remove(player.getUniqueId());
                player.sendMessage("§aAssigning mode has been §6deactivated§a.");
                return;
            }

            event.setCancelled(true);
            String key = chest.getX() + ", " + chest.getY() + ", " + chest.getZ();
            // Saves new PLC to config
            // TODO: Save full location?
            ConfigEditor.set("LootChests." + key + ".CoreInventory.Size", chest.getBlockInventory().getSize());
            ConfigEditor.set("LootChests." + key + ".CoreInventory.Contents", chest.getBlockInventory().getContents());

            // TODO: remove
            System.out.println("COREINV SAVED!");

            player.sendMessage("§aThis chest is now a Loot Chest.");
            // Ends the assigning mode
            playersInAssignMode.remove(player.getUniqueId());
            player.sendMessage("§aAssigning mode has been §6deactivated§a.");
        }
        else {
            // Player is not in assigning mode
            Action action = event.getAction();

            if (!match) return;
            // THE BLOCK IS A PLC

            // TODO: Don't cancel event?
            event.setCancelled(true);

            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                player.sendMessage("§cThis function is not active.");
                return;
            }

            if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                boolean playerAlreadySaved = false;
                try {
                    playerAlreadySaved = ConfigEditor.containsPath("LootChests." + chestKey + "." + player.getName());
                } catch (Exception e) {
                    // Player is not yet saved
                    // TODO: create player path
                }

                // Is there already a personal inventory check
                if (!playerAlreadySaved) {
                    // TODO: Exception handling if queried inv is invalid
                    // Creates new personal inventory as a copy of the core inventory
                    Object newPersInv;
                    newPersInv = ConfigEditor.get("LootChests." + chestKey + ".CoreInventory");
                    ConfigEditor.set("LootChests." + chestKey + "." + player.getName(), newPersInv);
                    // TODO!!!
                    System.out.println("NEW INV SAVED!");
                }

                // Opens the personal inventory
                int size = (int) ConfigEditor.get("LootChests." + chest.getX() + ", " + chest.getY() + ", " + chest.getZ() + "." + "CoreInventory.Size");
                // TODO: works?
                ItemStack[] contents = (ItemStack[]) ConfigEditor.get("LootChests." + chest.getX() + ", " + chest.getY() + ", " + chest.getZ() + "." + "CoreInventory.Contents");

                Inventory inv = Bukkit.createInventory(null, size, player.getName() + " §0loot chest: " + chestKey);
                inv.setContents(contents);
                player.openInventory(inv);
                return;
            }

            if (action.equals(Action.LEFT_CLICK_BLOCK) && player.getGameMode().equals(GameMode.CREATIVE)) {
                player.openInventory(getDestroyPLCInventory(block));
            }

        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPLCInventoryClick(InventoryClickEvent event) {

        Player player = (Player) event.getWhoClicked();
        World world = event.getWhoClicked().getWorld();
        Inventory inventory = event.getClickedInventory();
        ItemStack item = event.getCurrentItem();

        if (inventory == null) {
            return;
        }

        // TODO: Make more specific? (&& event.getInventory() instanceof ArtificialInventory)
        if (event.getView().getTitle().equals("§0Destroy Loot Chest?")) {
            String plcKey = InventoryManager.getCleanString(Objects.requireNonNull(Objects.requireNonNull(inventory.getItem(4)).getItemMeta()).getDisplayName());

            if (item.equals(InventoryManager.getYesItem())) {
                // YES ITEM

                // Iterates through the PLCs
                for (String current : Objects.requireNonNull(ConfigEditor.getSectionKeys("LootChests"))) {

                    assert plcKey != null;
                    if (plcKey.equals(current)) {
                        // There is a PLC with this key

                        String[] plcStringArr = plcKey.split(", ");
                        // TODO: Test if coords are correct
                        double plcX = Double.parseDouble(plcStringArr[0]);
                        double plcY = Double.parseDouble(plcStringArr[1]);
                        double plcZ = Double.parseDouble(plcStringArr[2]);

                        Location location = new Location(world, plcX, plcY, plcZ);

                        // Deletes the PLC config entry
                        ConfigEditor.clearPath("LootChests." + current);
                        // Destroys the teleporter
                        location.getBlock().setType(Material.AIR);
                        player.closeInventory();
                        return;
                    }
                }

            }
            else if (item.equals(InventoryManager.getNoItem())) {
                // NO ITEM
                player.closeInventory();
                return;
            }

        }

        if (event.getView().getTitle().startsWith(player.getName() + " §0loot chest: ")) {
            // works?
            String key = event.getView().getTitle().split(": ")[1];
            // TODO: rem + BUG IS HERE! Saving inv overwrites all paths of "LootChests.[key]".
            //  TEST SINGLE ITEMSTACK RELOAD CONSISTENCY. PROBLEM WITH RELOADS. TEST WITH MULTIPLE PLCS.
            // Saves the inventory contents
            //ConfigEditor.set("LootChests." + key + "." + player.getName() + ".Contents", event.getInventory().getContents());
            //player.getName();
            //ConfigEditor.set("LootChests.Test", "TestValue");
            System.out.println("PERSINV UPDATED! Path: LootChests." + key + "." + player.getName() + ".Contents");
            return;
        }

    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPLCInventoryClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (event.getView().getTitle().startsWith(player.getName() + " §0loot chest: ")) {
            String key = event.getView().getTitle().split(": ")[1];
            // Saves the inventory contents
            ConfigEditor.set("LootChests." + key + "." + player.getName() + ".Contents", event.getInventory().getContents());
            // TODO!!!
            System.out.println("PERSINV SAVED (INVCLOSE)!");
        }
    }

    private Inventory getDestroyPLCInventory(Block plc) {
        try {

            Inventory inv = InventoryManager.createNoInteractionInv(1, "§0Destroy Loot Chest?");

            inv.setItem(0, InventoryManager.getYesItem());
            inv.setItem(4, getPLCItem(plc));
            inv.setItem(8, InventoryManager.getNoItem());

            InventoryManager.fillEmptySlots(inv);
            return inv;
        } catch (Exception e) {
            return InventoryManager.getErrorInventory();
        }
    }

    private ItemStack getPLCItem(Block plc) {
        try {
            String itemName = plc.getX() + ", " + plc.getY() + ", " + plc.getZ();
            ItemStack item = new ItemBuilder(Material.CHEST, 1).setName(itemName).build();
            return item;
        } catch (Exception e) {
            return InventoryManager.getErrorItem();
        }
    }

}
*/