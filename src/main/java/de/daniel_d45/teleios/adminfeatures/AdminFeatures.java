/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.ItemBuilder;
import de.daniel_d45.teleios.core.RecipeManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;


public class AdminFeatures {

    private static final String[] activationstatePaths = {"AdminFeatures.All"};

    public static String getSegmentName() {
        return "AdminFeatures";
    }

    public static String[] getActivationstatePaths() {
        return activationstatePaths;
    }

    // TODO: Hide Enchants
    public static ItemStack getSegmentItem() {
        ItemStack item;
        ItemFlag[] flags = {ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_DESTROYS,
                ItemFlag.HIDE_DYE, ItemFlag.HIDE_PLACED_ON, ItemFlag.HIDE_POTION_EFFECTS, ItemFlag.HIDE_UNBREAKABLE,
                /*ItemFlag.HIDE_ARMOR_TRIM*/};

        if (ConfigEditor.isActive(getSegmentName() + ".All")) {
            // The segment is ON
            item = new ItemBuilder(Material.NETHER_STAR, 1).setName("§o§5AdminFeatures").setLore(
                            "§eActivationstate: §aON", "§fadds admin-only commands", "§7Left click to deactivate",
                            "§7Right click for more options").addEnchant(Enchantment.VANISHING_CURSE, 1)
                    .addItemFlags(flags).build();
        } else {
            // The segment is OFF
            item = new ItemBuilder(Material.NETHER_STAR, 1).setName("§o§5AdminFeatures").setLore(
                    "§eActivationstate: §cOFF", "§fadds admin-only commands", "§7Left click to activate",
                    "§7Right click for more options").addItemFlags(flags).build();
        }
        return item;
    }

    public static void switchActivationstateAF() {
        if (ConfigEditor.isActive(getSegmentName() + ".All")) {

            for (String current : activationstatePaths) {
                ConfigEditor.set("Activationstates." + current, "OFF");
            }
            RecipeManager.enableTeleporterRecipe(false);
        } else {

            for (String current : activationstatePaths) {
                ConfigEditor.set("Activationstates." + current, "ON");
            }
            RecipeManager.enableTeleporterRecipe(true);
        }
    }

}
