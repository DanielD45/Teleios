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
        Inventory inv = InventoryManager.createNoInteractionInv(1, "§cInventory creation failed!"/*, null*/);
        InventoryManager.fillInv(inv, getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem());
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
        return new ItemBuilder(Material.HOPPER, 1).setName("§7↓ go back").build();
    }

    public static Inventory getCurrMTLInv() {
        Inventory inv = InventoryManager.createNoInteractionInv(3, "§0Manage Teleios functionality");
        fillInvCentered(inv, AdminFeatures.getSegmentItem(), BetterGameplay.getSegmentItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem());
        InventoryManager.fillEmptySlots(inv);
        return inv;
    }

    public static Inventory getCurrAFInv() {
        // TODO: Add Items
        return createSegmentInv("AdminFeatures");
    }

    public static Inventory getCurrBGInv() {
        // TODO: Add Items
        return createSegmentInv("BetterGameplay", BetterGameplay.getEnderchestCmdItem(), BetterGameplay.getTeleportersItem());
    }

    public static Inventory getManagePSInventory() {
        return createSegmentInv("PassiveSkills");
    }

    /**
     * Fills the given inventory's unoccupied slots with black stained glass panes.
     */
    public static void fillEmptySlots(Inventory inventory) {
        for (int slot = 0; slot < inventory.getSize(); ++slot) {
            ItemStack filler = new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").build();
            // Adds a black stained glass pane in every empty slot
            if (inventory.getItem(slot) == null) {
                inventory.setItem(slot, filler);
            }
        }
    }

    /**
     * Fills the given inventory with the given ItemStacks if possible.
     */
    public static void fillInv(Inventory inventory, ItemStack... itemStacks) {
        ArrayList<Integer> emptySlots = new ArrayList<>();

        // Saves the empty slots to the ArrayList
        for (int slot = 0; slot < inventory.getSize(); ++slot) {
            if (inventory.getItem(slot) == null) emptySlots.add(slot);
        }

        if (emptySlots.size() < itemStacks.length) return;

        int i = 0;
        for (ItemStack item : itemStacks) {
            inventory.setItem(emptySlots.get(i), item);
            ++i;
        }
    }

    // TODO: manage multiple rows

    /**
     * Fills the given empty inventory with the given ItemStacks with centered alignment, if possible.
     */
    public static void fillInvCentered(Inventory inventory, ItemStack... itemStacks) {
        if (!inventory.isEmpty()) return;
        if (inventory.getSize() < itemStacks.length) return;

        // FIXME: manage multiple rows
        /*
          0000+0000 000+0+000 000+++000 V

          000000000 000000000 0000+0000 0000+0000 0000+0000 0000+0000 000+0+000 000+++000 00++0++00 00+++++00
          0000+0000 000+0+000 000+0+000 000+++000 00++0++00 00+++++00 00+++++00 00+++++00 00+++++00 00+++++00
         */

        int rows = (int) Math.ceil(itemStacks.length / 9.0);


        int slot = (int) (13 - Math.floor(itemStacks.length / 2.0));

        for (ItemStack currentItem : itemStacks) {
            inventory.setItem(slot, currentItem);
            if (itemStacks.length % 2 == 0 && slot == 12) {
                ++slot;
            }
            ++slot;
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
     * compares the specified ItemStacks by material, enchantments, name, flags and lore.
     *
     * @param itemStack1 [ItemStack]
     * @param itemStack2 [ItemStack]
     * @return [boolean] Whether the specified ItemStacks are equal.
     */
    public static boolean isSameItemType(ItemStack itemStack1, ItemStack itemStack2) {
        try {
            return GlobalFunctions.betterEquals(itemStack1.getType(), itemStack2.getType()) && GlobalFunctions.betterEquals(itemStack1.getEnchantments(), itemStack2.getEnchantments()) && GlobalFunctions.betterEquals(itemStack1.getItemMeta().getAttributeModifiers(), itemStack2.getItemMeta().getAttributeModifiers()) && GlobalFunctions.betterEquals(itemStack1.getItemMeta().getDisplayName(), itemStack2.getItemMeta().getDisplayName()) && GlobalFunctions.betterEquals(itemStack1.getItemMeta().getEnchants(), itemStack2.getItemMeta().getEnchants()) && GlobalFunctions.betterEquals(itemStack1.getItemMeta().getItemFlags(), itemStack2.getItemMeta().getItemFlags()) && GlobalFunctions.betterEquals(itemStack1.getItemMeta().getLore(), itemStack2.getItemMeta().getLore());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public static Inventory createNormalInv(int rows, String name) {
        return Bukkit.createInventory(null, 9 * rows, name);
    }

    public static Inventory createNoInteractionInv(int rows, String name) {
        rows = GlobalFunctions.trimInt(rows, 1, 6);
        return Bukkit.createInventory(new NoInteractionInventories(), rows * 9, name);
    }

    public static Inventory createSegmentInv(String segmentName, ItemStack... itemStacks) {

        // TODO: accomodate for >? items (multiple pages?)
        int rows;
        if (itemStacks == null) {
            rows = 1;
        }
        else {
            rows = (int) (Math.ceil(itemStacks.length / 9.0) + 1);
        }

        Inventory inv = InventoryManager.createNoInteractionInv(rows, "§0Manage " + segmentName);
        if (itemStacks != null) InventoryManager.fillInv(inv, itemStacks);
        inv.setItem((rows * 9) - 5, InventoryManager.getBackItem());
        InventoryManager.fillEmptySlots(inv);
        return inv;
    }

}
