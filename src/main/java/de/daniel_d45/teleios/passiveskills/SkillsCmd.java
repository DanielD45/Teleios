/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.GlobalFunctions;
import de.daniel_d45.teleios.core.InventoryManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;


// TODO: WIP
public class SkillsCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        try {

            if (GlobalFunctions.cmdOffCheck("PassiveSkills.All", sender)) return true;

            Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
            if (player == null) return true;

            // Only runs when a skill is activated
            if (!PassiveSkills.usedSkills.isEmpty()) {
                player.openInventory(getSkillsInventory(player));
            }
            else {
                player.sendMessage("§eNo skill is beeing used!");
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Inventory getSkillsInventory(Player player) {
        try {

            ItemStack item;

            // Sets the start slot. Only works fine up to 9 skills
            int slot = (int) (13 - Math.floor(PassiveSkills.usedSkills.size() / 2.0));
            // TODO: Dynamically adjust the inventory's rows.
            Inventory inventory = InventoryManager.createNoInteractionInventory(3, "Your Skills", null);

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
            return inventory;
        } catch (Exception e) {
            return InventoryManager.getErrorInventory();
        }
    }

}
