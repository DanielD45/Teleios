/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.MessageMaster;
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
        try {

            // Activationstate check
            if (!ConfigEditor.isActive("PassiveSkills.All")) {
                MessageMaster.sendExitMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + ")", "the PassiveSkills segment is not active.");
                return;
            }

            int preLevel;
            int postLevel;
            Player player = event.getPlayer();
            Block block = event.getBlock();
            boolean playerPlaced = false;

            // TODO: Remove Try-catch
            // CHECKS WHETHER THE BLOCK HAS BEEN PLACED BY A PLAYER
            try {

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

                }

            } catch (Exception e) {
                MessageMaster.sendFailMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + "): Placed by player check", e);
            }

            if (playerPlaced) {

                // LOGS THE BLOCK AS REMOVED
                // TODO: Check functionality
                String entryName = block.getWorld().getName() + "," + block.getX() + "," + block.getY() + "," + block.getZ();
                ConfigEditor.clearPath("PlacedBlocks." + entryName);

                MessageMaster.sendExitMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + "), the block has been removed from the PlacedBlocks path.", "success");
            }
            else {

                // Player survival mode check
                if (player.getGameMode() != GameMode.SURVIVAL) {
                    MessageMaster.sendExitMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + ")", "the player is not in survival mode.");
                    return;
                }

                // TODO: Remove Try-catch
                // CHECKS FOR A MATERIAL MATCH
                try {

                    // Iterates through the usedSkills ArrayList
                    for (Skill currentSkill : PassiveSkills.usedSkills) {

                        // Iterates through every used skill's listed materials
                        for (Material currentMaterial : currentSkill.getListedMaterials().keySet()) {

                            // Checks for a material match with the current material
                            if (block.getType() == currentMaterial) {

                                // Represents the level before increasing the BlockValue
                                preLevel = PassiveSkills.getLevel(player.getName(), currentSkill.getSkillName());

                                // TODO: Remove Try-catch
                                // CANCELS THE BLOCK DROPS AND REPLACES THEM WITH THE NEW DROPS
                                try {

                                    // TODO: Modify the outcome instead?
                                    // Cancels the normal block drops
                                    event.setDropItems(false);

                                    // Drops the modified block drops one by one
                                    for (ItemStack currentItem : currentSkill.modifyBlockDrops(block, player.getItemInHand(), player)) {

                                        block.getWorld().dropItemNaturally(block.getLocation(), currentItem);
                                    }

                                } catch (Exception e) {
                                    MessageMaster.sendFailMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + "): Modifying the block drops.", e);
                                    return;
                                }

                                // TODO: Remove Try-catch
                                // INCREASES THE BLOCKVALUE
                                try {

                                    // Increases the BlockValue by the material's value specified by the skill type
                                    PassiveSkills.increaseBlockValue(player.getName(), currentSkill.getSkillName(), currentSkill.getListedMaterials().get(currentMaterial));
                                } catch (Exception e) {
                                    MessageMaster.sendFailMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + "): Increasing the BlockValue.", e);
                                    return;
                                }

                                // Represents the level after increasing the BlockValue
                                postLevel = PassiveSkills.getLevel(player.getName(), currentSkill.getSkillName());

                                // TODO: Remove Try-catch
                                // CHECKS FOR A LEVEL-UP
                                try {

                                    if (preLevel < postLevel) {
                                        currentSkill.levelUp(player);
                                    }
                                } catch (Exception e) {
                                    MessageMaster.sendFailMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + "): Level-up check.", e);
                                    return;
                                }

                                MessageMaster.sendExitMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + ")", "success");
                                return;
                            }
                            // Else: the block's material doesn't match the current material
                        }
                        // Else: no Material match in this used skill's listedMaterials list
                    }
                    // No material match in any used skill's listedMaterials list

                } catch (Exception e) {
                    MessageMaster.sendFailMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + "): Material match check.", e);
                    return;
                }

                MessageMaster.sendExitMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + ")", "no material match.");
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("BlockBreakListenerPS", "onBlockBreakPS(" + event + ")", e);
        }
    }

}
