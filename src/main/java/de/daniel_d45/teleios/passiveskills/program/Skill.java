/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.passiveskills.program;

import de.daniel_d45.teleios.core.util.MessageMaster;
import de.daniel_d45.teleios.passiveskills.SegmentManagerPS;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;


public abstract class Skill {

    // TODO: Maybe implement a use() method

    // Variables should be unused. Use getters instead
    @SuppressWarnings("unused")
    private static final String skillName = "[skill]";
    @SuppressWarnings("unused")
    private static final String messageName = "[skill]";
    @SuppressWarnings("unused")
    private static final String blocksType = "[blocksType]";
    @SuppressWarnings("unused")
    private static final Material infoMaterial = Material.BEDROCK;
    @SuppressWarnings("unused")
    private static final HashMap<Material, Double> listedMaterials = new HashMap<Material, Double>();

    /**
     * Setup method in the Skill class.
     */
    public abstract void setup();

    public abstract String getSkillName();

    public abstract String getMessageName();

    public abstract String getBlocksType();

    public abstract Material getInfoMaterial();

    public abstract HashMap<Material, Double> getListedMaterials();

    public abstract void increaseBlockValue(Player player, Material material);

    /**
     * Creates the new items that are being dropped in case of a material match by copying the normal
     * block's drops and multiplying the amount of every item by the player's bonus item multiplier.
     * Returns the ItemStacks in an ArrayList or returns null when the process fails.
     *
     * @param block  [Block] The broken block.
     * @param tool   [ItemStack] The ItemStack used to break the block.
     * @param player [Entity] The player who broke the block.
     * @return [ArrayList<ItemStack>] The new drops or null when the process failed.
     */
    public ArrayList<ItemStack> modifyBlockDrops(Block block, ItemStack tool, Entity player) {
        try {

            // Casts the blockDrops to type ArrayList for better accessing the data
            ArrayList<ItemStack> blockDrops = (ArrayList<ItemStack>) block.getDrops(tool, player);
            ArrayList<ItemStack> newDrops = new ArrayList<ItemStack>();

            // Copies the objects from blockDrops to newDrops while changing the amount
            for (ItemStack currentItem : blockDrops) {
                int amount = currentItem.getAmount() * SegmentManagerPS.computeBonusItemMultiplier(player.getName(), getSkillName());
                // The current ItemStack with the new amount
                ItemStack itemStack = new ItemStack(currentItem.getType(), amount);

                // Adds the current entry to the new list
                newDrops.add(itemStack);
            }

            MessageMaster.sendSuccessMessage("Skill", "modifyBlockDrops(" + block + ", " + tool + ", " + player + ")");
            return newDrops;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("Skill", "modifyBlockDrops(" + block + ", " + tool + ", " + player + ")", e);
            return null;
        }

    }

    /**
     * @param playerName
     * @return
     */
    public String getListSkillsMessage(String playerName) {
        try {

            String message;
            // Represents the bonusMultiplier of the player and the skill
            double bonusMultiplier = SegmentManagerPS.getBonusMultiplier(playerName, getSkillName());

            // Checks whether the BonusMultiplier is an integer
            if (bonusMultiplier == Math.floor(bonusMultiplier)) {
                // Bonus Multiplier is an integer

                // Casts the BonusMultiplier to int
                bonusMultiplier = (int) bonusMultiplier;

                message = "§eYour §3" + getMessageName() + " §eis on level §3" + SegmentManagerPS.getLevel(playerName, getSkillName()) + "§e! Your bonus items multiplier is at §3" + bonusMultiplier + " §e meaning you'll get §3" + bonusMultiplier + "x §3the block drops of any kind of " + getBlocksType() + "!";
            }
            else {
                // Bonus Multiplier is not an integer

                message = "§eYour §3" + getMessageName() + " §eis on level §3" + SegmentManagerPS.getLevel(playerName, getSkillName()) + "§e! Your bonus items multiplier is at §3" + bonusMultiplier + " §e meaning you'll get §3" + (int) Math.floor(bonusMultiplier) + "x §ethe block drops of any kind of " + getBlocksType() + " and a chance of §3" + (bonusMultiplier - Math.floor(bonusMultiplier)) * 100 + "% §eto get §3" + (int) Math.floor(bonusMultiplier) + 1 + "x §ethe block drops!";
            }

            MessageMaster.sendSuccessMessage("Skill", "getMessage()");

            return message;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("Skill", "getMessage()", e);
            return null;
        }
    }

    /**
     * This method sends the specified player a level-up message and adds experience.
     *
     * @param player [Player] The player receiving the level-up.
     */
    public void levelUp(Player player) {
        try {

            int bonusXp = 100;
            // Gives the xp specified by bonusXp to the player
            player.giveExp(bonusXp);
            // Represents the bonusMultiplier of the player and the skill
            double bonusMultiplier = SegmentManagerPS.getBonusMultiplier(player.getName(), getSkillName());
            // Represents the messages to send the player with a level-up
            String[] messages = new String[2];
            messages[0] = "§eYour §3" + getMessageName() + " §eis now on level §3" + SegmentManagerPS.getLevel(player.getName(), getSkillName() + "§e!");

            // Checks whether the BonusMultiplier is an integer
            if (bonusMultiplier == Math.floor(bonusMultiplier)) {
                // Bonus Multiplier is an integer

                // Casts the BonusMultiplier to int
                bonusMultiplier = (int) bonusMultiplier;

                messages[1] = "Your bonus items multiplier is now at §3" + bonusMultiplier + " §e meaning you'll now get §3" + bonusMultiplier + "x §3the block drops of any kind of " + getBlocksType() + "!";
            }
            else {
                // Bonus Multiplier is not an integer

                messages[1] = "Your bonus items multiplier is now at §3" + bonusMultiplier + " §e meaning you'll now get §3" + (int) Math.floor(bonusMultiplier) + "x §ethe block drops of any kind of " + getBlocksType() + " and a chance of §3" + (bonusMultiplier - Math.floor(bonusMultiplier)) * 100 + "% §eto get §3" + (int) Math.floor(bonusMultiplier) + 1 + "x §ethe block drops!";
            }

            // Sends the player the messages
            player.sendMessage(messages);

            MessageMaster.sendSuccessMessage("Skill", "levelUp(" + player + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("Skill", "levelUp(" + player + ")", e);
        }
    }

    /**
     * Returns an ItemStack with the skill stats of the specified player.
     *
     * @param player [Player] The player to get the skill stats from.
     * @return [ItemStack] The finished ItemStack.
     */
    public ItemStack getSkillInfo(Player player) {
        try {

            ItemStack item;
            ItemMeta itemMeta;
            double bonusMultiplier = SegmentManagerPS.getBonusMultiplier(player.getName(), getSkillName());
            ArrayList<String> strList = new ArrayList<>();

            // Instantiates the ItemStack with the skill type's material
            item = new ItemStack(getInfoMaterial(), 1);

            // Gets the item meta to modify it
            itemMeta = item.getItemMeta();

            // MODIFIES THE ITEMMETA

            itemMeta.setDisplayName("§o§5" + getMessageName());
            // Sets the item lore.
            strList.add("§eLevel: §3" + SegmentManagerPS.getLevel(player.getName(), getSkillName()));
            strList.add("§eBonusMultiplier: §3" + bonusMultiplier);
            strList.add("");

            // Checks whether the BonusMultiplier is an integer
            if (bonusMultiplier == Math.floor(bonusMultiplier)) {

                strList.add("§eYou get §3" + bonusMultiplier + "x §ethe block drops of any kind of " + getBlocksType() + "!");
            }
            else {

                strList.add("§eYou get §3" + (int) Math.floor(bonusMultiplier) + "x §ethe block drops of any kind of " + getBlocksType() + " and a chance of §3" + (bonusMultiplier - Math.floor(bonusMultiplier)) * 100 + "% §eto get §3" + (int) Math.floor(bonusMultiplier) + 1 + "x §ethe block drops!");
            }
            itemMeta.setLore(strList);

            item.setItemMeta(itemMeta);

            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("LumberjackSkill", "getSkillInfo()", e);
            return null;
        }
    }

}
