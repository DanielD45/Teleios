/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.ArtificialInventory;
import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.InventoryManager;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


// TODO: WIP
public class SkillsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activationstate check
            if (!ConfigEditor.isActive("PassiveSkills.All")) {
                sender.sendMessage("§cThis command is not active!");
                MessageMaster.sendExitMessage("SkillsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "this command is not active");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendExitMessage("SkillsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // Only runs when a skill is activated
            if (PassiveSkills.usedSkills.size() > 0) {

                player.openInventory(getSkillsInventory(player));
                MessageMaster.sendExitMessage("SkillsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
            }
            else {
                player.sendMessage("§eNo skill is beeing used!");
                MessageMaster.sendExitMessage("SkillsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "no skills are beeing used.");
            }

            return true;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SkillsCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

    private Inventory getSkillsInventory(Player player) {
        try {

            ItemStack item;

            // Sets the start slot. Only works fine up to 9 skills
            int slot = (int) (13 - Math.floor(PassiveSkills.usedSkills.size() / 2.0));
            // TODO: Dynamically adjust the inventory's rows.
            Inventory inventory = Bukkit.createInventory(new ArtificialInventory(), 3 * 9, "Your Skills");

            // Iterates through the used skills
            for (Skill currentSkill : PassiveSkills.usedSkills) {

                item = currentSkill.getSkillInfo(player);

                // Dynamically adds the skill info items to the inventory
                inventory.setItem(slot, item);

                // Increases the value of the slot variable
                if (PassiveSkills.usedSkills.size() % 2 == 0 && slot == 12) {
                    // Skips the middle (13.) slot when the amount of used skills is even
                    slot += 2;
                }
                else {
                    ++slot;
                }

            }

            InventoryManager.fillEmptySlots(inventory);

            MessageMaster.sendExitMessage("SkillsCommand", "getInventory(" + player + ")", "success");
            return inventory;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SkillsCommand", "getInventory(" + player + ")", e);
            return InventoryManager.getErrorInventory();
        }

    }

}
