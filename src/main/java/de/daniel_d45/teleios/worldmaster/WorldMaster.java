/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.worldmaster;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;


public class WorldMaster {

    private static final String[] activationstatePaths = {"WorldMaster.All"};

    public static String getSegmentName() {
        return "WorldMaster";
    }

    public static String[] getActivationstatePaths() {
        return activationstatePaths;
    }

    public static void switchActivationstateWM() {

        if (ConfigEditor.isActive(getSegmentName() + ".All")) {
            for (String current : activationstatePaths) {
                ConfigEditor.set("Activationstates." + current, "OFF");
            }
        } else {
            for (String current : activationstatePaths) {
                ConfigEditor.set("Activationstates." + current, "ON");
            }
        }
    }

    public static ItemStack getSegmentItem() {

        ItemStack item;

        if (ConfigEditor.isActive(getSegmentName() + ".All")) {
            // The segment is activated
            item = new ItemBuilder(Material.GRASS_BLOCK, 1).setName("§o§5WorldMaster").setLore(
                            "§eActivationstate: §aON", "§4Not implemented", "§fThe WorldMaster segment adds commands",
                            "§fto manage different worlds on the server.", "§7Left click to deactivate the segment.",
                            "§7Right click for more options.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1)
                    .addItemFlags(ItemFlag.HIDE_ENCHANTS).build();
        } else {
            // The segment is deactivated
            item = new ItemBuilder(Material.GRASS_BLOCK, 1).setName("§o§5WorldMaster").setLore(
                    "§eActivationstate: §cOFF", "§4Not implemented", "§fThe WorldMaster segment adds commands",
                    "§fto manage different worlds on the server.", "§7Left click to activate the segment.",
                    "§7Right click for more options.").build();
        }
        return item;
    }

}
