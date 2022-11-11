/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;


public class BetterGameplay {

    private static final String[] activationstatePaths = {"BetterGameplay.All", "BetterGameplay.EnderchestCommand", "BetterGameplay.Teleporters"};

    public static String getSegmentName() {
        return "BetterGameplay";
    }

    public static String[] getActivationstatePaths() {
        return activationstatePaths;
    }

    // TODO: Hide Enchants.
    public static ItemStack getSegmentItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {
                // The segment is activated.
                item = new ItemBuilder(Material.IRON_PICKAXE, 1).setName("§o§5BetterGameplay").setLore("§fThe BetterGameplay segment adds", "§ffeatures for a better", "§fsurvival experience.", "§eActivationstate: §aON", "§7Left click to deactivate the segment.", "§7Right click for more options.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1)/* .addItemFlags(ItemFlag.HIDE_ENCHANTS) */.build();

            }
            else {
                // The segment is deactivated.
                item = new ItemBuilder(Material.IRON_PICKAXE, 1).setName("§o§5BetterGameplay").setLore("§fThe BetterGameplay segment adds", "§ffeatures for a better", "§fsurvival experience.", "§eActivationstate: §cOFF", "§7Left click to activate the segment.", "§7Right click for more options.").build();

            }

            MessageMaster.sendSuccessMessage("SegmentManagerBG", "getSegmentItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerBG", "getSegmentItem()", e);
            return InventoryManager.getErrorItem();
        }
    }

    public static ItemStack getEnderchestCommandItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive("BetterGameplay.EnderchestCommand")) {
                // The command is activated.
                item = new ItemBuilder(Material.ENDER_CHEST, 1).setName("§o§9Enderchest command").setLore("§fThe enderchest command lets every player", "§fopen their enderchest from anywhere.", "§eActivationstate: §aON", "§7Left click to deactivate the command.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
            }
            else {
                // The command is deactivated.
                item = new ItemBuilder(Material.ENDER_CHEST, 1).setName("§o§9Enderchest command").setLore("§fThe enderchest command lets every player", "§fopen their enderchest from anywhere.", "§eActivationstate: §cOFF", "§7Left click to activate the command.").build();
            }

            MessageMaster.sendSuccessMessage("SegmentManagerBG", "getEnderchestCommandItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerBG", "getEnderchestCommandItem()", e);
            return InventoryManager.getErrorItem();
        }
    }

    public static ItemStack getTeleportersItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                // The function is activated.
                item = new ItemBuilder(Material.END_PORTAL_FRAME, 1).setName("§o§9Teleporters").setLore("§fYou can craft teleporters", "§fto which you can teleport", "§ffrom everywhere.", "§fTeleporting costs ender pearls", "§fstored in your warppouch.", "§eActivationstate: §aON", "§7Left click to deactivate the function.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1)/* .addItemFlags(ItemFlag.HIDE_ENCHANTS) */.build();
            }
            else {
                // The function is deactivated.
                item = new ItemBuilder(Material.END_PORTAL_FRAME, 1).setName("§o§9Teleporters").setLore("§fYou can craft teleporters", "§fto which you can teleport", "§ffrom everywhere.", "§fTeleporting costs ender pearls", "§fstored in your warppouch.", "§eActivationstate: §cOFF", "§7Left click to deactivate the function.").build();
            }

            MessageMaster.sendSuccessMessage("SegmentManagerBG", "getTeleportersItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerBG", "getTeleportersItem()", e);
            return InventoryManager.getErrorItem();
        }
    }

    // TODO: Maybe change the switch activationstate system.
    public static void switchActivationstateBG() {
        try {

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "OFF");
                }

                RecipeManager.enableTeleporterRecipe(false);
                MessageMaster.sendSuccessMessage("SegmentManagerBG", "switchActivationstateBG()");
            }
            else {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "ON");
                }

                RecipeManager.enableTeleporterRecipe(true);
                MessageMaster.sendSuccessMessage("SegmentManagerBG", "switchActivationstateBG()");
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerBG", "switchActivationstateBG()", e);
        }
    }

    public static void switchActivationstateTeleporters() {
        try {

            if (ConfigEditor.isActive(getSegmentName() + ".Teleporters")) {

                ConfigEditor.set("Activationstates." + getSegmentName() + ".Teleporters", "OFF");
                RecipeManager.enableTeleporterRecipe(false);
                MessageMaster.sendSuccessMessage("SegmentManagerBG", "switchActivationstateTeleporters()");
            }
            else {

                ConfigEditor.set("Activationstates." + getSegmentName() + ".Teleporters", "ON");
                RecipeManager.enableTeleporterRecipe(true);
                MessageMaster.sendSuccessMessage("SegmentManagerBG", "switchActivationstateTeleporters()");
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerBG", "switchActivationstateTeleporters()", e);
        }
    }

    public static void initiateWarppouch(String playerName) {
        try {

            if (!ConfigEditor.containsPath("Warppouch." + playerName)) {

                ConfigEditor.set("Warppouch." + playerName, 0);
            }
            else {
                // Both paths exist
                MessageMaster.sendWarningMessage("SegmentManagerBG", "initiateWarppouch(" + playerName + ")", "the path already exists.");
            }

            MessageMaster.sendSuccessMessage("SegmentManagerBG", "initiateWarppouch(" + playerName + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerBG", "initiateWarppouch(" + playerName + ")", e);
        }
    }

}
