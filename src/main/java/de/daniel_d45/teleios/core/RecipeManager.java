/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import com.google.errorprone.annotations.DoNotCall;
import de.daniel_d45.teleios.adminfeatures.AdminFeatures;
import de.daniel_d45.teleios.core.main.Teleios;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.recipe.CraftingBookCategory;

import javax.annotation.Nonnull;


public final class RecipeManager implements Listener {

    @Nonnull
    public static ShapedRecipe getTeleporterRecipe() {

        ItemStack teleporter = new ItemBuilder(Material.END_PORTAL_FRAME).setName("§5Teleporter").setLore("§fChange this teleporter's name", "§fby using the §6/configureteleporter§f command§r").build();

        ShapedRecipe teleporterRecipe = new ShapedRecipe(new NamespacedKey(Teleios.getPlugin(), "Teleporter"), teleporter);
        teleporterRecipe.shape("ESE", "SBS", "ESE");
        teleporterRecipe.setIngredient('E', Material.ENDER_PEARL);
        teleporterRecipe.setIngredient('S', Material.SANDSTONE);
        teleporterRecipe.setIngredient('B', Material.BLAZE_POWDER);
        teleporterRecipe.setCategory(CraftingBookCategory.MISC);

        return teleporterRecipe;
    }

    public static void enableTeleporterRecipe(boolean enable) {
        if (enable) {
            Bukkit.addRecipe(getTeleporterRecipe());
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.hasDiscoveredRecipe(getTeleporterRecipe().getKey())) {
                    player.discoverRecipe(getTeleporterRecipe().getKey());
                }
            }
        }
        else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.hasDiscoveredRecipe(getTeleporterRecipe().getKey())) {
                    player.undiscoverRecipe(getTeleporterRecipe().getKey());
                }
            }
            Bukkit.removeRecipe(getTeleporterRecipe().getKey());
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (ConfigEditor.isActive(AdminFeatures.getActivationstatePaths()[0])) {
            if (!player.hasDiscoveredRecipe(getTeleporterRecipe().getKey())) {
                player.discoverRecipe(getTeleporterRecipe().getKey());
            }
        }
        else {
            if (player.hasDiscoveredRecipe(getTeleporterRecipe().getKey())) {
                player.undiscoverRecipe(getTeleporterRecipe().getKey());
            }
        }
    }

    @DoNotCall
    public static void registerTestRecipes() {

        ItemStack superPaper = new ItemBuilder(Material.PAPER, 1).setName(ChatColor.GOLD + "Super Paper").addEnchant(Enchantment.SHARPNESS, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS).build();

        ShapelessRecipe recipe0 = new ShapelessRecipe(new NamespacedKey(Teleios.getPlugin(), "SuperPaperRecipe"), superPaper);
        recipe0.addIngredient(3, Material.BOOK);
        Bukkit.addRecipe(recipe0);


        ItemStack weirdSword = new ItemBuilder(Material.WOODEN_SWORD).setName(ChatColor.GRAY + "Weird Sword").setLore("Test").build();

        FurnaceRecipe recipe1 = new FurnaceRecipe(new NamespacedKey(Teleios.getPlugin(), "WeirdSword"), weirdSword, new RecipeChoice.ExactChoice(superPaper), 2, 20);
        Bukkit.addRecipe(recipe1);


        ItemStack laserpointer = new ItemBuilder(Material.END_ROD).setName(ChatColor.DARK_RED + "Laser Pointer").build();

        ShapedRecipe recipe2 = new ShapedRecipe(new NamespacedKey(Teleios.getPlugin(), "LaserPointerRecipe"), laserpointer);
        recipe2.shape(" S ", "SBS", " S ");
        recipe2.setIngredient('S', new RecipeChoice.ExactChoice(weirdSword));
        recipe2.setIngredient('B', Material.BOOK);
        Bukkit.addRecipe(recipe2);
    }

}
