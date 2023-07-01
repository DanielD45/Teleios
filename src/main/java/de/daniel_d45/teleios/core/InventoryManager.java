/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.adminfeatures.AdminFeatures;
import de.daniel_d45.teleios.bettergameplay.BetterGameplay;
import de.daniel_d45.teleios.passiveskills.PassiveSkills;
import de.daniel_d45.teleios.worldmaster.WorldMaster;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


public class InventoryManager {

    public static ItemStack getErrorItem() {
        return new ItemBuilder(Material.BARRIER, 1).setName("§oERROR").build();
    }

    public static Inventory getErrorInventory() {
        Inventory inv = Bukkit.createInventory(null, 9, "§cInventory creation failed!");
        InventoryManager.fillInventory(inv, getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem(), getErrorItem());
        return inv;
    }

    public static String getCleanString(String string) {
        try {

            // TODO: Handle spaces at string start?
            if (string.startsWith("§")) {
                return string.substring(2);
            }

            MessageMaster.sendExitMessage("InventoryManager", "getCleanString(" + string + ")", "success");
            return string;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "getCleanString(" + string + ")", e);
            return null;
        }
    }

    public static ItemStack getYesItem() {
        try {

            ItemStack item = new ItemBuilder(Material.GREEN_CONCRETE, 1).setName("§aYes").build();

            MessageMaster.sendExitMessage("InventoryManager", "getYesItem()", "success");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "getYesItem()", e);
            return getErrorItem();
        }
    }

    public static ItemStack getNoItem() {
        try {

            ItemStack item = new ItemBuilder(Material.RED_CONCRETE, 1).setName("§cNo").build();

            MessageMaster.sendExitMessage("InventoryManager", "getNoItem()", "success");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "getNoItem()", e);
            return getErrorItem();
        }
    }

    public static ItemStack getBackItem() {
        try {

            ItemStack item = new ItemBuilder(Material.HOPPER, 1).setName("§7<- Previous page").build();

            MessageMaster.sendExitMessage("InventoryManager", "getBackItem()", "success");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "getBackItem()", e);
            return getErrorItem();
        }
    }

    // TODO: Keep up-to-date
    public static Inventory getManageTeleiosInventory() {
        try {

            Inventory inv = InventoryManager.createArtificialInventory(3, "§0Manage Teleios functionality");

            // TODO: make list and dynamically arrange icons in inventory
            // AdminFeatures segment.
            inv.setItem(11, AdminFeatures.getSegmentItem());
            // BetterGameplay segment.
            inv.setItem(12, BetterGameplay.getSegmentItem());
            // PassiveSkills segment.
            inv.setItem(14, PassiveSkills.getSegmentItem());
            // WorldMaster segment.
            inv.setItem(15, WorldMaster.getSegmentItem());

            InventoryManager.fillEmptySlots(inv);

            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "getInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    public static Inventory getManageAFInventory() {
        try {

            // TODO: Add Items
            Inventory inv = createSegmentInventory("AdminFeatures");

            MessageMaster.sendExitMessage("ManageteleiosCommand", "getManageAFInventory()", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "getManageAFInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    public static Inventory getManageBGInventory() {
        try {

            // TODO: Add Items
            Inventory inv = createSegmentInventory("BetterGameplay", BetterGameplay.getEnderchestCommandItem(), BetterGameplay.getTeleportersItem());

            MessageMaster.sendExitMessage("ManageteleiosCommand", "getManageAFInventory()", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "getManageAFInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    public static Inventory getManageCEInventory() {
        try {

            // TODO: Add Items
            Inventory inv = createSegmentInventory("CreatureEvolution");

            MessageMaster.sendExitMessage("ManageteleiosCommand", "getManageCEInventory()", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "getManageCEInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    public static Inventory getManagePSInventory() {
        try {

            // TODO: Add Items
            Inventory inv = createSegmentInventory("PassiveSkills");

            MessageMaster.sendExitMessage("ManageteleiosCommand", "getManagePSInventory()", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "getManagePSInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    public static Inventory getManageWMInventory() {
        try {

            // TODO: Add Items
            Inventory inv = createSegmentInventory("WorldMaster");

            MessageMaster.sendExitMessage("ManageteleiosCommand", "getManageWMInventory()", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "getManageWMInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    /**
     * Fills a specified inventory's unoccupied slots with black stained glass panes.
     *
     * @param inventory [Inventory] The modified inventory
     */
    public static void fillEmptySlots(Inventory inventory) {
        try {

            for (int slot = 0; slot < inventory.getSize(); slot++) {
                // Adds a black stained glass pane in every empty slot
                if (inventory.getItem(slot) == null) {
                    // TODO: Possible to reference the same variable?
                    inventory.setItem(slot, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE, 1).setName(" ").build());
                }
            }

            MessageMaster.sendExitMessage("InventoryManager", "fillEmptySlots(" + inventory + ")", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "fillEmptySlots(" + inventory + ")", e);
        }
    }

    // TODO: Care for ArrayIndexOutOfBoundsException

    public static void fillInventory(Inventory inventory, ItemStack... itemStacks) {
        try {
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
                    inventory.setItem(currentSlot, itemStacks[i]);
                    ++i;
                }

                MessageMaster.sendExitMessage("InventoryManager", "fillInventory(" + inventory + ", " + Arrays.toString(itemStacks) + ")", "success");
            }
            else {
                MessageMaster.sendExitMessage("InventoryManager", "fillInventory(" + inventory + ", " + Arrays.toString(itemStacks) + ")", "too many items to add.");
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            MessageMaster.sendFailMessage("InventoryManager", "fillInventory(" + inventory + ")", e);
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "fillInventory(" + inventory + ")", e);
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
            MessageMaster.sendExitMessage("InventoryManager", "removeItemsPlayerSoft(" + inv + ", " + itemStack + ", " + amount + ")", "the specified amount is 0 or less.");
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
                MessageMaster.sendExitMessage("InventoryManager", "removeItemsPlayerSoft(" + inv + ", " + itemStack + ", " + amount + ")", "the specified inventory doesn't contain this ItemStack.");
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
                    MessageMaster.sendExitMessage("InventoryManager", "removeItemsPlayerSoft(" + inv + ", " + itemStack + ", " + amount + ")", "success");
                    return amount;
                }

                if (restAmount == 0) {
                    MessageMaster.sendExitMessage("InventoryManager", "removeItemsPlayerSoft(" + inv + ", " + itemStack + ", " + amount + ")", "success");
                    return amount;
                }

            }

            throw new Exception("Couldn't remove the specified items from the specified inventory!");
        } catch (Exception e) {
            // Resets the inventory
            for (int currentSlot : matchingItems.keySet()) {
                inv.setItem(currentSlot, matchingItems.get(currentSlot));
            }
            MessageMaster.sendFailMessage("InventoryManager", "removeItemsPlayerSoft(" + inv + ", " + itemStack + ", " + amount + ")", e);
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

            if (BetterMethods.betterEquals(itemStack1.getType(), itemStack2.getType()) && BetterMethods.betterEquals(itemStack1.getEnchantments(), itemStack2.getEnchantments()) && BetterMethods.betterEquals(itemStack1.getItemMeta().getAttributeModifiers(), itemStack2.getItemMeta().getAttributeModifiers()) && BetterMethods.betterEquals(itemStack1.getItemMeta().getDisplayName(), itemStack2.getItemMeta().getDisplayName()) && BetterMethods.betterEquals(itemStack1.getItemMeta().getEnchants(), itemStack2.getItemMeta().getEnchants()) && BetterMethods.betterEquals(itemStack1.getItemMeta().getItemFlags(), itemStack2.getItemMeta().getItemFlags()) && BetterMethods.betterEquals(itemStack1.getItemMeta().getLore(), itemStack2.getItemMeta().getLore())) {
                MessageMaster.sendExitMessage("InventoryManager", "isSameItemType(" + itemStack1 + ", " + itemStack2 + ")", "success");
                return true;
            }

            MessageMaster.sendExitMessage("InventoryManager", "isSameItemType(" + itemStack1 + ", " + itemStack2 + ")", "the items are not of the same type.");
            return false;
        } catch (NullPointerException e) {
            MessageMaster.sendExitMessage("InventoryManager", "isSameItemType(" + itemStack1 + ", " + itemStack2 + ")", "the items are not of the same type.");
            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "isSameItemType(" + itemStack1 + ", " + itemStack2 + ")", e);
            return false;
        }
    }

    public static Inventory createNormalInventory(int rows, String name) {
        try {

            Inventory inv = Bukkit.createInventory(null, 9 * rows, name);

            MessageMaster.sendExitMessage("InventoryManager", "createArtificialInventory(" + rows + ", " + name + ")", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "createArtificialInventory(" + rows + ", " + name + ")", e);
            return getErrorInventory();
        }
    }

    public static Inventory createArtificialInventory(int rows, String name) {
        try {

            Inventory inv = Bukkit.createInventory(new ArtificialInventory(), rows * 9, name);

            MessageMaster.sendExitMessage("InventoryManager", "createArtificialInventory(" + rows + ", " + name + ")", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("InventoryManager", "createArtificialInventory(" + rows + ", " + name + ")", e);
            return getErrorInventory();
        }
    }

    public static Inventory createSegmentInventory(String name, ItemStack... itemStacks) {
        try {

            Inventory inv = InventoryManager.createArtificialInventory(3, "§0Manage " + name);

            inv.setItem(22, InventoryManager.getBackItem());

            if (itemStacks != null) {
                InventoryManager.fillInventory(inv, itemStacks);
            }

            InventoryManager.fillEmptySlots(inv);

            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "getManageAFInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

}
