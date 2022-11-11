/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
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
        try {

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

            MessageMaster.sendSuccessMessage("RecipeManager", "getTeleporterRecipe()");
            return teleporterRecipe;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("RecipeManager", "getTeleporterRecipe()", e);
            return null;
        }
    }

    // TODO: Check functionality
    public static void enableTeleporterRecipe(boolean b) {
        try {

            if (b) {
                Bukkit.addRecipe(getTeleporterRecipe());
            }
            else {
                Bukkit.removeRecipe(getTeleporterRecipe().getKey());
            }

            MessageMaster.sendSuccessMessage("RecipeManager", "enableTeleporterRecipe(" + b + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("RecipeManager", "enableTeleporterRecipe(" + b + ")", e);
        }
    }

}
