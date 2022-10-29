/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.program.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;


public class SegmentManagerAF {

    private static final String[] activationstatePaths = {"AdminFeatures.All"};

    public static String getSegmentName() {
        return "AdminFeatures";
    }

    public static String[] getActivationstatePaths() {
        return activationstatePaths;
    }

    // TODO: Hide Enchants
    public static ItemStack getSegmentItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {
                // The segment is activated
                item = new ItemBuilder(Material.NETHER_STAR, 1).setName("§o§5AdminFeatures").setLore("§fThe AdminFeatures segment adds", "§fconvenient admin-only commands.", "§eActivationstate: §aON", "§7Left click to deactivate the segment.", "§7Right click for more options.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1)/* .addItemFlags(ItemFlag.HIDE_ENCHANTS) */.build();
            }
            else {
                // The segment is deactivated
                item = new ItemBuilder(Material.NETHER_STAR, 1).setName("§o§5AdminFeatures").setLore("§fThe AdminFeatures segment adds", "§fconvenient admin-only commands.", "§eActivationstate: §cOFF", "§7Left click to activate the segment.", "§7Right click for more options.").build();

            }

            MessageMaster.sendSuccessMessage("SegmentManagerAC", "getSegmentItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerAC", "getSegmentItem()", e);
            return InventoryManager.getErrorItem();
        }
    }

    // TODO: Maybe change the switch activationstate system
    public static void switchActivationstateAF() {
        try {

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {

                for (String current : activationstatePaths) {

                    ConfigEditor.set("Activationstates." + current, "OFF");
                }

                RecipeManager.enableTeleporterRecipe(false);

                MessageMaster.sendSuccessMessage("SegmentManagerAC", "switchActivationstateAC()");
            }
            else {

                for (String current : activationstatePaths) {

                    ConfigEditor.set("Activationstates." + current, "ON");
                }

                RecipeManager.enableTeleporterRecipe(true);

                MessageMaster.sendSuccessMessage("SegmentManagerAC", "switchActivationstateAC()");
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerAC", "switchActivationstateAC()", e);
        }
    }

}
