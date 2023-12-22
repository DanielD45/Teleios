/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.passiveskills;

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
    }

    public static void use() {
        // Adds an object of this class to the list of used skills for access to this class's variables
        PassiveSkills.addUsedSkill(new LumberjackSkill());
    }

    /**
     * Setup method in the LumberjackSkill class. Initiates the variables. Must be called before
     * operating the LumberSkill class to prevent problems.
     */
    public void setup() {
        fillMaterials();
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
        return skillName;
    }

    /**
     * Getter for the listedMaterials variable. This method is used by the instance of this class that
     * is put in the usedSkills ArrayList of the SegmentManagerPS class. Returns null if the process
     * failed.
     */
    @Override
    public HashMap<Material, Double> getListedMaterials() {
        try {
            return listedMaterials;
        } catch (Exception e) {
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
        PassiveSkills.increaseBlockValue(player.getName(), skillName, listedMaterials.get(material));
    }

}
