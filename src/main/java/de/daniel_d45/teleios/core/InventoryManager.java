/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.adminfeatures.AdminFeatures;
import de.daniel_d45.teleios.bettergameplay.BetterGameplay;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;


public class InventoryManager {

    public static ItemStack getErrorItem() {
        return new ItemBuilder(Material.POISONOUS_POTATO, 1).setName("§oERROR").build();
    }

    public static Inventory getErrorInventory() {
        Inventory inv = InventoryManager.createNoInteractionInventory(1, "§cInventory creation failed!", null);
        InventoryManager.fillInventory(inv, getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem());
        return inv;
    }

    public static String getCleanString(String string) {
        // TODO: Handle spaces at string start?
        if (string.startsWith("§")) {
            return string.substring(2);
        }
        return string;
    }

    public static ItemStack getYesItem() {
        return new ItemBuilder(Material.GREEN_CONCRETE, 1).setName("§aYes").build();
    }

    public static ItemStack getNoItem() {
        return new ItemBuilder(Material.RED_CONCRETE, 1).setName("§cNo").build();
    }

    public static ItemStack getBackItem() {
        return new ItemBuilder(Material.HOPPER, 1).setName("§7<- Previous page").build();
    }

    public static Inventory getManageTeleiosInventory() {
        Inventory inv = InventoryManager.createNoInteractionInventory(3, "§0Manage Teleios functionality", null);

        // TODO: make list and dynamically arrange icons in inventory
        // AdminFeatures segment.
        inv.setItem(11, AdminFeatures.getSegmentItem());
        // BetterGameplay segment.
        inv.setItem(12, BetterGameplay.getSegmentItem());
        // PassiveSkills segment.
        //inv.setItem(14, PassiveSkills.getSegmentItem());
        // WorldMaster segment.
        //inv.setItem(15, WorldMaster.getSegmentItem());

        InventoryManager.fillEmptySlots(inv);
        return inv;
    }

    public static Inventory getManageAFInventory() {
        // TODO: Add Items
        return createSegmentInventory("AdminFeatures");
    }

    public static Inventory getManageBGInventory() {
        // TODO: Add Items
        return createSegmentInventory("BetterGameplay", BetterGameplay.getEnderchestCmdItem(), BetterGameplay.getTeleportersItem());
    }

    public static Inventory getManagePSInventory() {
        // TODO: Add Items
        return createSegmentInventory("PassiveSkills");
    }

    public static Inventory getManageWMInventory() {
        // TODO: Add Items
        return createSegmentInventory("WorldMaster");
    }

    /**
     * Fills a specified inventory's unoccupied slots with black stained glass panes.
     *
     * @param inventory [Inventory] The modified inventory
     */
    public static void fillEmptySlots(Inventory inventory) {
        for (int slot = 0; slot < inventory.getSize(); ++slot) {
            // Adds a black stained glass pane in every empty slot
            if (inventory.getItem(slot) == null) {
                // TODO: Possible to reference the same variable?
                inventory.setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").build());
            }
        }
    }

    public static void fillInventory(Inventory inventory, ItemStack... itemStacks) {
        ArrayList<Integer> emptySlots = new ArrayList<>();

        // Saves the empty slots in the ArrayList
        for (int slot = 0; slot < inventory.getSize(); ++slot) {
            if (inventory.getItem(slot) == null) {
                emptySlots.add(slot);
            }
        }

        if (emptySlots.size() >= itemStacks.length) {
            int i = 0;

            for (int currentSlot : emptySlots) {
                if (i == itemStacks.length) {
                    break;
                }
                inventory.setItem(currentSlot, itemStacks[i]);
                ++i;
            }
        }
    }

    // TODO: Exception handling

    /**
     * @param inv       [Inventory] The inventory to remove the items from.
     * @param itemStack [ItemStack] The ItemStack to remove.
     * @param amount    [int] The amount of items to remove.
     * @return [int] The amount of removed items.
     */
    public static int removeItemsPlayerSoft(Inventory inv, ItemStack itemStack, int amount) {
        if (amount <= 0) {
            return 0;
        }

        // Will hold all matching ItemStacks
        HashMap<Integer, ItemStack> matchingItems = new HashMap<>();

        int possibleAmount = 0;

        // Iterates firstly through the inventory
        for (int i = 9; i <= 35; ++i) {
            if (isSameItemType(inv.getItem(i), itemStack)) {
                // Adds the ItemStack to the map
                matchingItems.put(i, inv.getItem(i));
                possibleAmount += inv.getItem(i).getAmount();
            }
        }

        // Iterates secondly through the hotbar
        for (int j = 0; j <= 8; ++j) {
            if (isSameItemType(inv.getItem(j), itemStack)) {
                // Adds the item to the map
                matchingItems.put(j, inv.getItem(j));
                possibleAmount += inv.getItem(j).getAmount();
            }
        }

        try {

            if (possibleAmount <= 0) {
                return 0;
            }

            // Adjusts the amount to the possible removable amount
            if (amount > possibleAmount) {
                amount = possibleAmount;
            }

            int restAmount = amount;

            // Iterates through the matching ItemStacks
            for (int currentSlot : matchingItems.keySet()) {

                ItemStack currentItem = inv.getItem(currentSlot);

                if ((currentItem.getAmount() - restAmount) <= 0) {

                    restAmount -= currentItem.getAmount();
                    // Removes the ItemStack
                    inv.setItem(currentSlot, null);
                }
                else {
                    currentItem.setAmount(currentItem.getAmount() - restAmount);
                    return amount;
                }

                if (restAmount == 0) {
                    return amount;
                }

            }

            throw new Exception("Couldn't remove the specified items from the specified inventory!");
        } catch (Exception e) {
            // Resets the inventory
            for (int currentSlot : matchingItems.keySet()) {
                inv.setItem(currentSlot, matchingItems.get(currentSlot));
            }
            return 0;
        }
    }

    // TODO: Handle NullPointerExceptions better

    /**
     * This method compares the specified ItemStacks by material, enchantments, name, flags and lore.
     *
     * @param itemStack1 [ItemStack] The first ItemStack
     * @param itemStack2 [ItemStack] The second ItemStack
     * @return [boolean] Whether the specified ItemStacks are equal.
     */
    public static boolean isSameItemType(ItemStack itemStack1, ItemStack itemStack2) {
        try {

            return GlobalMethods.betterEquals(itemStack1.getType(), itemStack2.getType()) && GlobalMethods.betterEquals(itemStack1.getEnchantments(), itemStack2.getEnchantments()) && GlobalMethods.betterEquals(itemStack1.getItemMeta().getAttributeModifiers(), itemStack2.getItemMeta().getAttributeModifiers()) && GlobalMethods.betterEquals(itemStack1.getItemMeta().getDisplayName(), itemStack2.getItemMeta().getDisplayName()) && GlobalMethods.betterEquals(itemStack1.getItemMeta().getEnchants(), itemStack2.getItemMeta().getEnchants()) && GlobalMethods.betterEquals(itemStack1.getItemMeta().getItemFlags(), itemStack2.getItemMeta().getItemFlags()) && GlobalMethods.betterEquals(itemStack1.getItemMeta().getLore(), itemStack2.getItemMeta().getLore());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static Inventory createNormalInventory(int rows, String name) {
        return Bukkit.createInventory(null, 9 * rows, name);
    }


    /**
     * @param rows                 Corrected to fit interval [1;6]
     * @param name                 -
     * @param predecessorInventory -
     * @return -
     */
    public static Inventory createNoInteractionInventory(int rows, String name, Inventory predecessorInventory) {
        rows = GlobalMethods.validateInt(rows, 1, 6);
        return Bukkit.createInventory(new NoInteractionInventories(predecessorInventory), rows * 9, name);
    }

    public static Inventory createSegmentInventory(String segmentName, ItemStack... itemStacks) {
        try {

            // TODO: dynamically adjust number of rows to fit amount of itemStacks
            int rows = 3;
            Inventory inv = InventoryManager.createNoInteractionInventory(rows, "§0Manage " + segmentName, null/*getManageTeleiosInventory()*/);
            inv.setItem((rows * 9) - 5, InventoryManager.getBackItem());

            if (itemStacks != null) {
                InventoryManager.fillInventory(inv, itemStacks);
            }

            InventoryManager.fillEmptySlots(inv);
            return inv;
        } catch (Exception e) {
            return InventoryManager.getErrorInventory();
        }
    }

}
