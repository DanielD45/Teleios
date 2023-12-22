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

        if (ConfigEditor.isActive(getSegmentName() + ".All")) {
            // The segment is activated
            item = new ItemBuilder(Material.NETHER_STAR, 1).setName("§o§5AdminFeatures").setLore("§eActivationstate: §aON", "§fThe AdminFeatures segment adds", "§fconvenient admin-only commands.", "§7Left click to deactivate the segment.", "§7Right click for more options.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1)/* .addItemFlags(ItemFlag.HIDE_ENCHANTS) */.build();
        }
        else {
            // The segment is deactivated
            item = new ItemBuilder(Material.NETHER_STAR, 1).setName("§o§5AdminFeatures").setLore("§eActivationstate: §cOFF", "§fThe AdminFeatures segment adds", "§fconvenient admin-only commands.", "§7Left click to activate the segment.", "§7Right click for more options.").build();
        }
        return item;
    }

    // TODO: Maybe change the switch activationstate system
    public static void switchActivationstateAF() {
        if (ConfigEditor.isActive(getSegmentName() + ".All")) {

            for (String current : activationstatePaths) {
                ConfigEditor.set("Activationstates." + current, "OFF");
            }
            RecipeManager.enableTeleporterRecipe(false);
        }
        else {

            for (String current : activationstatePaths) {
                ConfigEditor.set("Activationstates." + current, "ON");
            }
            RecipeManager.enableTeleporterRecipe(true);
        }
    }

}
