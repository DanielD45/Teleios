/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.worldmaster;

import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.ItemBuilder;
import de.daniel_d45.teleios.core.program.MessageMaster;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;


public class SegmentManagerWM {

    private static final String[] activationstatePaths = {"WorldMaster.All"};

    public static String getSegmentName() {
        return "WorldMaster";
    }

    public static String[] getActivationstatePaths() {
        return activationstatePaths;
    }

    public static void switchActivationstateWM() {
        try {

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "OFF");
                }

                MessageMaster.sendSuccessMessage("SegmentManagerWM", "switchActivationstateWM()");
            }
            else {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "ON");
                }

                MessageMaster.sendSuccessMessage("SegmentManagerWM", "switchActivationstateWM()");
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerWM", "switchActivationstateWM()", e);
        }
    }

    public static ItemStack getSegmentItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {
                // The segment is activated

                item = new ItemBuilder(Material.GRASS_BLOCK, 1).setName("§o§5WorldMaster").setLore("§4Not implemented", "§fThe WorldMaster segment adds commands", "§fto manage different worlds on the server.", "§eActivationstate: §aON", "§7Left click to deactivate the segment.", "§7Right click for more options.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();

            }
            else {
                // The segment is deactivated

                item = new ItemBuilder(Material.GRASS_BLOCK, 1).setName("§o§5WorldMaster").setLore("§4Not implemented", "§fThe WorldMaster segment adds commands", "§fto manage different worlds on the server.", "§eActivationstate: §cOFF", "§7Left click to activate the segment.", "§7Right click for more options.").build();

            }

            MessageMaster.sendSuccessMessage("SegmentManagerPS", "getSegmentItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "getSegmentItem()", e);
            return new ItemBuilder(Material.BARRIER, 1).setName("§o§cERROR").build();
        }

    }

}
