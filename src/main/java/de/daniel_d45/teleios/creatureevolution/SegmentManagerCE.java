/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.creatureevolution;

import de.daniel_d45.teleios.core.util.ConfigEditor;
import de.daniel_d45.teleios.core.util.ItemBuilder;
import de.daniel_d45.teleios.core.util.MessageMaster;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;


public class SegmentManagerCE {

    private static final String[] activationstatePaths = {"CreatureEvolution.All"};

    public static String getSegmentName() {
        return "CreatureEvolution";
    }

    public static String[] getActivationstatePaths() {
        return activationstatePaths;
    }

    public static void switchActivationstateCE() {
        try {

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "OFF");
                }

                MessageMaster.sendSuccessMessage("SegmentManagerCE", "switchActivationstateCE()");
            }
            else {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "ON");
                }

                MessageMaster.sendSuccessMessage("SegmentManagerCE", "switchActivationstateCE()");
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerCE", "switchActivationstateCE()", e);
        }
    }

    public static ItemStack getSegmentItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {
                // The segment is activated

                item = new ItemBuilder(Material.ZOMBIE_HEAD, 1).setName("§o§5CreatureEvolution").setLore("§4Not implemented", "§eActivationstate: §aON", "§7Left click to deactivate the segment.", "§7Right click to manage the functionality more specifically.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();

            }
            else {
                // The segment is deactivated

                item = new ItemBuilder(Material.ZOMBIE_HEAD, 1).setName("§o§5CreatureEvolution").setLore("§4Not implemented", "§eActivationstate: §cOFF", "§7Left click to deactivate the segment.", "§7Right click to manage the functionality more specifically.").build();

            }

            MessageMaster.sendSuccessMessage("SegmentManagerCE", "getSegmentItem()");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerCE", "getSegmentItem()", e);
            return new ItemBuilder(Material.BARRIER, 1).setName("§o§cERROR").build();
        }

    }

}
