/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.ConfigEditor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;


// TODO: WIP
public class BlockBreakLstPS implements Listener {

    /**
     * Method fires when a player breaks a block, checks if the broken block is listed in a skill's
     * listedBlocks ArrayList and increases the connected BonusMultiplier of the connected skill. Value
     * of getDrops() when you break a redstone ore block: [ItemStack{REDSTONE x 4}].
     *
     * @param event [BlockBreakEvent] The event firing the method.
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreakPS(BlockBreakEvent event) {

        // Activationstate check
        if (!ConfigEditor.isActive("PassiveSkills.All")) {
            return;
        }

        int preLevel;
        int postLevel;
        Player player = event.getPlayer();
        Block block = event.getBlock();
        boolean playerPlaced = false;

        // TODO: Remove Try-catch
        // CHECKS WHETHER THE BLOCK HAS BEEN PLACED BY A PLAYER

        String[] placedBlocks = ConfigEditor.getSectionKeys("PlacedBlocks").toArray(new String[0]);

        if (placedBlocks != null) {

            // Iterates through the placed blocks
            for (String currentKey : placedBlocks) {

                // TODO: Valid check? (Block == Location check)
                if (ConfigEditor.get("PlacedBlocks." + currentKey) == block.getLocation()) {

                    playerPlaced = true;
                    break;
                }
            }

            if (playerPlaced) {

                // LOGS THE BLOCK AS REMOVED
                // TODO: Check functionality
                String entryName = block.getWorld().getName() + "," + block.getX() + "," + block.getY() + "," + block.getZ();
                ConfigEditor.clearPath("PlacedBlocks." + entryName);
            } else {

                // Player survival mode check
                if (player.getGameMode() != GameMode.SURVIVAL) {
                    return;
                }

                // CHECKS FOR A MATERIAL MATCH

                // Iterates through the usedSkills ArrayList
                for (Skill currentSkill : PassiveSkills.usedSkills) {

                    // Iterates through every used skill's listed materials
                    for (Material currentMaterial : currentSkill.getListedMaterials().keySet()) {

                        // Checks for a material match with the current material
                        if (block.getType() == currentMaterial) {

                            // Represents the level before increasing the BlockValue
                            preLevel = PassiveSkills.getLevel(player.getName(), currentSkill.getSkillName());

                            // CANCELS THE BLOCK DROPS AND REPLACES THEM WITH THE NEW DROPS

                            // TODO: Modify the outcome instead?
                            // Cancels the normal block drops
                            event.setDropItems(false);

                            // Drops the modified block drops one by one
                            for (ItemStack currentItem : currentSkill.modifyBlockDrops(block, player.getInventory().getItemInMainHand(), player)) {

                                block.getWorld().dropItemNaturally(block.getLocation(), currentItem);
                            }

                            // INCREASES THE BLOCKVALUE

                            // Increases the BlockValue by the material's value specified by the skill type
                            PassiveSkills.increaseBlockValue(player.getName(), currentSkill.getSkillName(), currentSkill.getListedMaterials().get(currentMaterial));

                            // Represents the level after increasing the BlockValue
                            postLevel = PassiveSkills.getLevel(player.getName(), currentSkill.getSkillName());

                            // CHECKS FOR A LEVEL-UP

                            if (preLevel < postLevel) {
                                currentSkill.levelUp(player);
                            }

                            return;
                        }
                        // Else: the block's material doesn't match the current material
                    }
                    // Else: no Material match in this used skill's listedMaterials list
                }
                // No material match in any used skill's listedMaterials list

            }
        }

    }

}
