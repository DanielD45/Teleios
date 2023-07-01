/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.passiveskills;

import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;


/**
 * This class represents a type of Skill.
 *
 * @author Daniel_D45
 */
public class LumberjackSkill extends Skill {

    public static String skillName = "LumberjackSkill";
    public static String messageName = "Lumberjack skill";
    public static String blocksType = "log";
    public static Material infoMaterial = Material.OAK_LOG;
    public static HashMap<Material, Double> listedMaterials = new HashMap<>();

    /**
     * Constructor for class LumberjackSkill.
     */
    public LumberjackSkill() {
    }

    /**
     * Adds all the materials covered by this skill to the listedMaterials ArrayList. The
     * LumberjackSkill includes all types of log and stem.
     */
    private static void fillMaterials() {
        try {

            // TODO: Keep up-to-date
            listedMaterials.clear();
            listedMaterials.put(Material.OAK_LOG, 1.0);
            listedMaterials.put(Material.BIRCH_LOG, 1.0);
            listedMaterials.put(Material.SPRUCE_LOG, 1.0);
            listedMaterials.put(Material.DARK_OAK_LOG, 1.0);
            listedMaterials.put(Material.ACACIA_LOG, 1.0);
            listedMaterials.put(Material.JUNGLE_LOG, 1.0);
            listedMaterials.put(Material.CRIMSON_STEM, 1.0);
            listedMaterials.put(Material.WARPED_STEM, 1.0);

            MessageMaster.sendExitMessage("LumberjackSkill", "fillMaterials()", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("LumberjackSkill", "fillMaterials()", e);
        }
    }

    public static void use() {
        try {

            // Adds an object of this class to the list of used skills for access to this class's variables
            PassiveSkills.addUsedSkill(new LumberjackSkill());

            MessageMaster.sendExitMessage("LumberjackSkill", "use()", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("LumberjackSkill", "use()", e);
        }
    }

    /**
     * Setup method in the LumberjackSkill class. Initiates the variables. Must be called before
     * operating the LumberSkill class to prevent problems.
     */
    public void setup() {
        try {

            fillMaterials();

            MessageMaster.sendExitMessage("LumberjackSkill", "setup()", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("LumberjackSkill", "setup()", e);
        }
    }

    @Override
    public String getMessageName() {
        return messageName;
    }

    @Override
    public String getBlocksType() {
        return blocksType;
    }

    @Override
    public Material getInfoMaterial() {
        return infoMaterial;
    }

    /**
     * Getter for the skillName variable. This method is used by the instance of this class that is put
     * in the usedSkills ArrayList of the SegmentManagerPS class.
     */
    @Override
    public String getSkillName() {
        try {

            String name = skillName;

            MessageMaster.sendExitMessage("LumberjackSkill", "getSkillName()", "success");
            return name;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("LumberjackSkill", "getSkillName()", e);
            return null;
        }
    }

    /**
     * Getter for the listedMaterials variable. This method is used by the instance of this class that
     * is put in the usedSkills ArrayList of the SegmentManagerPS class. Returns null if the process
     * failed.
     */
    @Override
    public HashMap<Material, Double> getListedMaterials() {
        try {

            HashMap<Material, Double> ret = listedMaterials;

            MessageMaster.sendExitMessage("LumberjackSkill", "getListedMaterials()", "success");

            return ret;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("LumberjackSkill", "getListedMaterials()", e);
            return null;
        }
    }

    /**
     * Computes the BlockValue depending on the broken block and calls the increaseBlockValue(...)
     * method of the SegmentManagerPS class to update the specified player's BlockValue.
     *
     * @param player   [Player] The player who broke the block.
     * @param material [Material] The material matching the block's material. Used to be able to
     *                 increase the BlockValue specifically for every material.
     */
    @Override
    public void increaseBlockValue(Player player, Material material) {
        try {

            PassiveSkills.increaseBlockValue(player.getName(), skillName, listedMaterials.get(material));

            MessageMaster.sendExitMessage("LumberjackSkill", "increaseBlockValue(" + player + ", " + material + ")", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("LumberjackSkill", "increaseBlockValue(" + player + ", " + material + ")", e);
        }
    }

}
