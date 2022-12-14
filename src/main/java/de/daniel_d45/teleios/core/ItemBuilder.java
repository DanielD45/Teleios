/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;


public class ItemBuilder {

    private ItemStack item;
    private ItemMeta itemMeta;

    /**
     * Material and amount constructor
     *
     * @param material [Material]
     * @param amount   [Integer]
     */
    public ItemBuilder(Material material, int amount) {
        item = new ItemStack(material, amount);
        itemMeta = item.getItemMeta();
    }

    /**
     * ItemStack constructor
     *
     * @param itemStack [ItemStack]
     */
    public ItemBuilder(ItemStack itemStack) {
        item = itemStack;
        itemMeta = itemStack.getItemMeta();
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }

    // TODO: Check usage
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(String name) {
        itemMeta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(String... lore) {
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    // TODO: Fix
    public ItemBuilder addEnchantment(Enchantment enchant, int level) {
        item.addEnchantment(enchant, level);
        return this;
    }

    // TODO: Fix
    public ItemBuilder addUnsafeEnchantment(Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
        return this;
    }

    public ItemBuilder addItemFlags(ItemFlag... itemFlags) {
        itemMeta.addItemFlags(itemFlags);
        return this;
    }

    public ItemBuilder setUnbreakable(boolean unbreakable) {
        itemMeta.setUnbreakable(unbreakable);
        return this;
    }

}
