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
                item = new ItemBuilder(Material.IRON_PICKAXE, 1).setName("§o§5BetterGameplay").setLore("§eActivationstate: §aON", "§fadds features for a", "§fbetter survival experience", "§7Left click to deactivate", "§7Right click for more options").addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS).addEnchant(Enchantment.VANISHING_CURSE, 1).build();
            }
            else {
                item = new ItemBuilder(Material.IRON_PICKAXE, 1).setName("§o§5BetterGameplay").setLore("§eActivationstate: §cOFF", "§fadds features for a", "§fbetter survival experience", "§7Left click to activate", "§7Right click for more options").addItemFlags(ItemFlag.HIDE_ATTRIBUTES).build();
            }
            return item;
        } catch (Exception e) {
            return InventoryManager.getErrorItem();
        }
    }

    public static ItemStack getEnderchestCmdItem() {
        try {
            ItemStack item;

            if (ConfigEditor.isActive("BetterGameplay.EnderchestCommand")) {
                // The command is ON
                item = new ItemBuilder(Material.ENDER_CHEST, 1).setName("§o§9Enderchest command").setLore("§eActivationstate: §aON", "§fThe enderchest command lets every player", "§fopen their enderchest from anywhere.", "§7Left click to deactivate the command.").addEnchant(Enchantment.VANISHING_CURSE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
            }
            else {
                // The command is OFF
                item = new ItemBuilder(Material.ENDER_CHEST, 1).setName("§o§9Enderchest command").setLore("§eActivationstate: §cOFF", "§fThe enderchest command lets every player", "§fopen their enderchest from anywhere.", "§7Left click to activate the command.").build();
            }

            return item;
        } catch (Exception e) {
            return InventoryManager.getErrorItem();
        }
    }

    public static ItemStack getTeleportersItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive("BetterGameplay.Teleporters")) {
                // The function is activated.
                item = new ItemBuilder(Material.END_PORTAL_FRAME, 1).setName("§o§9Teleporters").setLore("§eActivationstate: §aON", "§fYou can craft teleporters", "§fto which you can teleport", "§ffrom everywhere.", "§fTeleporting costs ender pearls", "§fstored in your warppouch.", "§7Left click to deactivate the function.").addEnchant(Enchantment.VANISHING_CURSE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
            }
            else {
                // The function is deactivated.
                item = new ItemBuilder(Material.END_PORTAL_FRAME, 1).setName("§o§9Teleporters").setLore("§eActivationstate: §cOFF", "§fYou can craft teleporters", "§fto which you can teleport", "§ffrom everywhere.", "§fTeleporting costs ender pearls", "§fstored in your warppouch.", "§7Left click to activate the function.").build();
            }

            return item;
        } catch (Exception e) {
            return InventoryManager.getErrorItem();
        }
    }

    public static void switchActivationstateBG() {
        if (ConfigEditor.isActive(getSegmentName() + ".All")) {
            for (String current : getActivationstatePaths()) {
                ConfigEditor.set("Activationstates." + current, "OFF");
            }
            RecipeManager.enableTeleporterRecipe(false);
        }
        else {
            for (String current : getActivationstatePaths()) {
                ConfigEditor.set("Activationstates." + current, "ON");
            }
            RecipeManager.enableTeleporterRecipe(true);
        }
    }

    public static void switchActivationstateTeleporters() {
        if (ConfigEditor.isActive(getSegmentName() + ".Teleporters")) {

            ConfigEditor.set("Activationstates." + getSegmentName() + ".Teleporters", "OFF");
            RecipeManager.enableTeleporterRecipe(false);
        }
        else {
            ConfigEditor.set("Activationstates." + getSegmentName() + ".Teleporters", "ON");
            RecipeManager.enableTeleporterRecipe(true);
        }
    }

    public static void initiateWarppouch(String playerName) {
        if (!ConfigEditor.containsPath("Warppouch." + playerName)) {
            ConfigEditor.set("Warppouch." + playerName, 0);
        }
    }

}
