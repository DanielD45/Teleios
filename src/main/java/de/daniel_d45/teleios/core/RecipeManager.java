/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.core.main.Teleios;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;


public class RecipeManager {

    public static ShapedRecipe getTeleporterRecipe() {

        ItemStack teleporter = new ItemBuilder(Material.END_PORTAL_FRAME, 1).setLore("§fChange this teleporter's name", "§fby using the §6/configureteleporter §fcommand§r").build();

        ItemMeta teleporterMeta = teleporter.getItemMeta();

        teleporterMeta.setDisplayName("§5Teleporter");

        teleporter.setItemMeta(teleporterMeta);

        NamespacedKey key = new NamespacedKey(Teleios.getPlugin(), "teleporter");

        ShapedRecipe teleporterRecipe = new ShapedRecipe(key, teleporter);
        teleporterRecipe.shape("ESE", "SBS", "ESE");
        teleporterRecipe.setIngredient('E', Material.ENDER_PEARL);
        teleporterRecipe.setIngredient('S', Material.SANDSTONE);
        teleporterRecipe.setIngredient('B', Material.BLAZE_POWDER);

        return teleporterRecipe;
    }

    // TODO: Check functionality
    public static void enableTeleporterRecipe(boolean b) {
        if (b) {
            Bukkit.addRecipe(getTeleporterRecipe());
        }
        else {
            Bukkit.removeRecipe(getTeleporterRecipe().getKey());
        }
    }

}
