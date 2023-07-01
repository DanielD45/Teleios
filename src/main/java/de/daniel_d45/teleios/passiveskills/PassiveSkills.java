/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.ItemBuilder;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;


public class PassiveSkills {

    private static final String[] activationstatePaths = {"PassiveSkills.All"};
    // An ArrayList with an object of every skill class in it
    // TODO: Move to config
    public static ArrayList<Skill> usedSkills = new ArrayList<>();

    /**
     * Must be called before operating the PassiveSkills segment to prevent problems.
     */
    public static void setup() {
        // TODO
        // Specifies the skills to use and initiates their variables
        LumberjackSkill.use();
        // MinerSkill.use();
    }

    public static String getSegmentName() {
        return "PassiveSkills";
    }

    public static String[] getActivationstatePaths() {
        return activationstatePaths;
    }

    public static void switchActivationstatePS() {
        try {

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "OFF");
                }

                MessageMaster.sendExitMessage("SegmentManagerAC", "switchActivationstateAC()", "success");
            }
            else {

                for (String current : activationstatePaths) {
                    ConfigEditor.set("Activationstates." + current, "ON");
                }

                MessageMaster.sendExitMessage("SegmentManagerPS", "switchActivationstatePS()", "success");
            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "switchActivationstatePS()", e);
        }
    }

    public static ItemStack getSegmentItem() {
        try {

            ItemStack item;

            if (ConfigEditor.isActive(getSegmentName() + ".All")) {
                // The segment is activated

                item = new ItemBuilder(Material.SPRUCE_LOG, 1).setName("§o§5PassiveSkills").setLore("§fThe PassiveSkills segment adds skills", "§fyou can level up by breaking blocks.", "§fYou can use this segment for", "§fa better survival experience.", "§eActivationstate: §aON", "§7Left click to deactivate the segment.", "§7Right click for more options.").addUnsafeEnchantment(Enchantment.VANISHING_CURSE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();

            }
            else {
                // The segment is deactivated

                item = new ItemBuilder(Material.SPRUCE_LOG, 1).setName("§o§5PassiveSkills").setLore("§fThe PassiveSkills segment adds skills", "§fyou can level up by breaking blocks.", "§fYou can use this segment for", "§fa better survival experience.", "§eActivationstate: §cOFF", "§7Left click to activate the segment.", "§7Right click for more options.").build();

            }

            MessageMaster.sendExitMessage("SegmentManagerPS", "getSegmentItem()", "success");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "getSegmentItem()", e);
            return new ItemBuilder(Material.BARRIER, 1).setName("§o§cERROR").build();
        }

    }

    /**
     * This method is used for enabling the wanted skills.
     *
     * @param skill [String] The name of the skill to use.
     */
    public static void addUsedSkill(Skill skill) {
        try {

            // Iterates through the already listed used skills
            for (Skill currentSkill : usedSkills) {

                // Checks whether the specified skill is already listed
                if (currentSkill.equals(skill)) {
                    MessageMaster.sendExitMessage("SegmentManagerPS", "addUsedSkill(" + skill + ")", "the specified skill is already listed.");
                    return;
                }

            }

            usedSkills.add(skill);
            // Calls the setup method in the subclass of the Skill class
            skill.setup();

            MessageMaster.sendExitMessage("SegmentManagerPS", "addUsedSkill(" + skill + ")", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "addUsedSkill(" + skill + ")", e);
        }
    }

    /**
     * This method is used for disabling the wanted skills.
     *
     * @param skill [Skill]
     */
    public static void removeUsedSkill(Skill skill) {
        try {

            usedSkills.remove(skill);

            MessageMaster.sendExitMessage("SegmentManagerPS", "removeUsedSkill(" + skill + ")", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "removeUsedSkill(" + skill + ")", e);
        }
    }

    /**
     * Updates the paths of the player with the specified name. Returns whether the method succeded or
     * not. Only works correctly when both paths "BlockValue" and "BonusMultiplier" exist.
     *
     * @param playerName [String] The player's name
     */
    public static void initiatePlayerRecords(String playerName) {
        try {

            // Iterates through the used skills
            for (Skill currentSkill : usedSkills) {

                // Changes the value to the current skillName
                String skillName = currentSkill.getSkillName();

                // CHECKS WHETHER THE DATA STRUCTURE FOR THE SPECIFIED PLAYER EXISTS
                if (!(ConfigEditor.containsPath("Players." + playerName + "." + skillName + ".BlockValue") && ConfigEditor.containsPath("Players." + playerName + "." + skillName + ".BonusMultiplier"))) {
                    // One or both paths don't exist

                    // Creates the paths
                    ConfigEditor.set("Players." + playerName + "." + skillName + ".BlockValue", 0.0);
                    ConfigEditor.set("Players." + playerName + "." + skillName + ".BonusMultiplier", 1.0);

                    MessageMaster.sendExitMessage("SegmentManagerPS", "initiatePlayerRecords(" + playerName + ")", "success");
                }
                else {
                    // Both paths exist
                    MessageMaster.sendExitMessage("SegmentManagerPS", "initiatePlayerRecords(" + playerName + ")", "both paths already exist.");
                }
                // After checking for data structure
            }
            // Atfer iterating through the used skills

        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "initiatePlayerRecords(" + playerName + ")", e);
        }
    }

    /**
     * Removes the player with the specified name from the Players list.
     *
     * @param playerName [String] The player's name
     */
    public static void removePlayer(String playerName) {
        try {

            ConfigEditor.clearPath("Players." + playerName);

            MessageMaster.sendExitMessage("SegmentManagerPS", "removePlayer(" + playerName + ")", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "removePlayer(" + playerName + ")", e);
        }
    }

    /**
     * Getter for the BlockValue of the specified player's specified skill. Returns -1 if the process
     * failed.
     *
     * @param playerName [String] The player's name.
     * @param skillName  [String] The skill's name.
     * @return [int] The value of the "Players.[playerName].[skillName].BlockValue" path in
     * the config file. Equals to -1 if the process failed.
     */
    public static double getBlockValue(String playerName, String skillName) {
        try {

            double blockValue = (double) ConfigEditor.get("Players." + playerName + "." + skillName + ".BlockValue");

            MessageMaster.sendExitMessage("SegmentManagerPS", "getBlockValue(" + playerName + ", " + skillName + ")", "success");

            return blockValue;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "getBlockValue(" + playerName + ", " + skillName + ")", e);
            return -1;
        }
    }

    /**
     * This method increases the BlockValue of the specified player's specified skill by the specified
     * value.
     *
     * @param playerName [String] The player's name.
     * @param skillName  [String] The skill's name.
     */
    public static void increaseBlockValue(String playerName, String skillName, double value) {
        try {

            // Increases the BlockValue by the specified value
            ConfigEditor.set("Players." + playerName + "." + skillName + ".BlockValue", getBlockValue(playerName, skillName) + value);

            MessageMaster.sendExitMessage("SegmentManagerPS", "increaseBlockValue(" + playerName + ", " + skillName + ", " + value + ")", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "increaseBlockValue(" + playerName + ", " + skillName + ", " + value + ")", e);
        }
    }

    /**
     * Getter for the BonusMultiplier of the specified player's specified skill. Returns -1 if the
     * process failed.
     *
     * @param playerName [String] The player's name.
     * @param skillName  [String] The skill's name.
     * @return [int] The value of the "Players.[playerName].[skillName].BonusMultiplier" path
     * in the config file or -1 if the process failed.
     */
    public static double getBonusMultiplier(String playerName, String skillName) {
        try {

            double bonusMultiplier = (double) ConfigEditor.get("Players." + playerName + "." + skillName + ".BonusMultiplier");

            MessageMaster.sendExitMessage("SegmentManagerPS", "getBonusMultiplier(" + playerName + ", " + skillName + ")", "success");

            return bonusMultiplier;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "getBonusMultiplier(" + playerName + ", " + skillName + ")", e);
            return -1;
        }
    }

    // TODO: Maybe improve Exception Handling

    /**
     * Computes the amount of bonus items the player gets. Returns -1 if the process failed.
     *
     * @return [int] The amount of bonus blocks the player gets.
     */
    public static int computeBonusItemMultiplier(String playerName, String skillName) {
        try {

            int itemMultiplier = 1;
            double bonusMultiplier;

            // Checks whether getBonusMultiplier() ran properly
            if (getBonusMultiplier(playerName, skillName) >= 1) {
                // METHOD GETBONUSMULTIPLIER() SUCCEDED

                // Should be at least 1.0.
                bonusMultiplier = getBonusMultiplier(playerName, skillName);

                /* The floored BonusMultiplier value represents the chance of getting certainly x times a bonus
                 * block, e.g. BonusMultiplier 2.5 => 2 bonus blocks and 50% chance to get a third one. */
                itemMultiplier += Math.floor(bonusMultiplier) - 1;

                // Only the decimal places needed from here
                double restBonusMultiplier = bonusMultiplier - Math.floor(bonusMultiplier);

                // Calculates whether the player gets one extra bonus item
                if (Math.random() <= restBonusMultiplier) {
                    itemMultiplier++;
                }

                MessageMaster.sendExitMessage("SegmentManagerPS", "computeBonusItemMultiplier(" + playerName + ", " + skillName + ")", "success");

            }
            else {
                // Method getBonusMultiplier() failed

                MessageMaster.sendWarningMessage("SegmentManagerPS", "computeBonusItemAmount(" + playerName + ", " + skillName + ")", "method getBonusMultiplier(" + playerName + ", " + skillName + ") failed.");
            }

            return itemMultiplier;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "computeBonusItemMultiplier(" + playerName + ", " + skillName + ")", e);
            return -1;
        }
    }

    /**
     * Returns the level of the specified player's specified skill.
     *
     * @param playerName [String] The player's name.
     * @param skillName  [String] The skill's name.
     * @return [int] The level of the specified player's specified skill or -1 when the
     * process failed.
     */
    public static int getLevel(String playerName, String skillName) {
        try {

            int level;

            // Checks whether the getBlockValue() method ran properly
            if (getBlockValue(playerName, skillName) >= 0.0) {
                // The method getBlockValue() ran properly

                // The level limit is every 64 BlockValue
                level = (int) Math.floor(getBlockValue(playerName, skillName) / 64) + 1;

                MessageMaster.sendExitMessage("SegmentManagerPS", "getLevel(" + playerName + ", " + skillName + ")", "success");
            }
            else {
                // The getBlockValue() method failed

                level = -1;

                MessageMaster.sendWarningMessage("SegmentManagerPS", "getLevel(" + playerName + ", " + skillName + ")", "method getBlockValue(" + playerName + ", " + skillName + ") failed.");
            }

            return level;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("SegmentManagerPS", "getLevel(" + playerName + ", " + skillName + ")", e);
            return -1;
        }
    }

}
