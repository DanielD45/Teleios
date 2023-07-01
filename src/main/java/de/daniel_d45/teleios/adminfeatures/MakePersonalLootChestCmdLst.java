/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.InventoryManager;
import de.daniel_d45.teleios.core.ItemBuilder;
import de.daniel_d45.teleios.core.MessageMaster;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class MakePersonalLootChestCmdLst implements CommandExecutor, Listener {

    ArrayList<UUID> playersInAssignMode = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("AdminFeatures.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendExitMessage("GmaCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the command is deactivated.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendExitMessage("GmaCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // TODO: implement Timer
            if (playersInAssignMode.contains(player.getUniqueId())) {
                playersInAssignMode.remove(player.getUniqueId());
                player.sendMessage("§aAssigning mode has been §6deactivated§a.");
            }
            else {
                playersInAssignMode.add(player.getUniqueId());
                player.sendMessage("§aThe next chest you click will turn into a personal loot chest. You can also cancel this assigning mode by using the command again.");
            }
            MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
            return true;

        } catch (Exception e) {
            MessageMaster.sendFailMessage("MakePersonalLootChestCmdLst", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerInteractPLC(PlayerInteractEvent event) {
        try {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();

            // TODO: Make other chest types possible? Then also change getPLCItem()...
            // Block material check
            if (block == null || !block.getType().equals(Material.CHEST)) {
                MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerAssignPLC(" + event + ")", "Wrong block type.");
                return;
            }

            Chest chest = (Chest) block.getState();
            String chestKey = chest.getX() + ", " + chest.getY() + ", " + chest.getZ();
            Set<String> plcKeys = ConfigEditor.getSectionKeys("PersonalLootChests");

            // Are there PLCs check
            if (plcKeys == null) {
                MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerInteractPLC(" + event + ")", "There are no PLCs.");
                return;
            }

            // Is block PLC check
            boolean match = false;
            for (String currentKey : plcKeys) {
                if (currentKey.equals(chestKey)) {
                    // Key match
                    match = true;
                    break;
                }
            }

            // Is player in assigning mode check
            if (playersInAssignMode.contains(player.getUniqueId())) {

                // Is block already PLC check
                if (match) {
                    // This block is already a PLC
                    event.setCancelled(true);
                    player.sendMessage("§cThis block is already a Personal Loot Chest!");
                    playersInAssignMode.remove(player.getUniqueId());
                    player.sendMessage("§aAssigning mode has been §6deactivated§a.");
                    MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerAssignPLC(" + event + ")", "This block is already a PLC.");
                    return;
                }
                // BLOCK IS NOT A PLC

                // Activation state check
                if (!ConfigEditor.isActive("AdminFeatures.All")) {
                    player.sendMessage("§cThis function is not active.");
                    playersInAssignMode.remove(player.getUniqueId());
                    player.sendMessage("§aAssigning mode has been §6deactivated§a.");
                    MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerAssignPLC(" + event + ")", "The command is deactivated.");
                    return;
                }

                event.setCancelled(true);
                String key = chest.getX() + ", " + chest.getY() + ", " + chest.getZ();
                // Saves new PLC to config
                // TODO: Save full location?
                ConfigEditor.set("PersonalLootChests." + key + ".CoreInventory.Size", chest.getBlockInventory().getSize());
                ConfigEditor.set("PersonalLootChests." + key + ".CoreInventory.Contents", chest.getBlockInventory().getContents());

                // TODO: remove
                System.out.println("COREINV SAVED!");

                player.sendMessage("§aThis chest is now a Personal Loot Chest.");
                // Ends the assigning mode
                playersInAssignMode.remove(player.getUniqueId());
                player.sendMessage("§aAssigning mode has been §6deactivated§a.");
                MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerAssignPLC(" + event + ")", "Success");
            }
            else {
                // Player is not in assigning mode
                Action action = event.getAction();

                if (!match) {
                    MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerInteractPLC(" + event + ")", "This block is not a PLC.");
                    return;
                }
                // THE BLOCK IS A PLC

                // TODO: Don't cancel event?
                event.setCancelled(true);

                if (!ConfigEditor.isActive("AdminFeatures.All")) {
                    player.sendMessage("§cThis function is not active.");
                    MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerAssignPLC(" + event + ")", "The command is deactivated.");
                    return;
                }

                if (action.equals(Action.RIGHT_CLICK_BLOCK)) {
                    boolean playerAlreadySaved = false;
                    try {
                        playerAlreadySaved = ConfigEditor.containsPath("PersonalLootChests." + chestKey + "." + player.getName());
                    } catch (Exception e) {
                        // Player is not yet saved
                    }

                    // Is there already a personal inventory check
                    if (!playerAlreadySaved) {
                        // TODO: Exception handling if queried inv is invalid
                        // Creates new personal inventory as a copy of the core inventory
                        Object newPersInv;
                        try {
                            newPersInv = ConfigEditor.get("PersonalLootChests." + chestKey + ".CoreInventory");
                        } catch (Exception e) {
                            return;
                        }
                        ConfigEditor.set("PersonalLootChests." + chestKey + "." + player.getName(), newPersInv);
                        // TODO!!!
                        System.out.println("NEW INV SAVED!");
                    }

                    // Opens the personal inventory
                    try {
                        int size = (int) ConfigEditor.get("PersonalLootChests." + chest.getX() + ", " + chest.getY() + ", " + chest.getZ() + "." + "CoreInventory.Size");
                        // TODO: works?
                        ItemStack[] contents = (ItemStack[]) ConfigEditor.get("PersonalLootChests." + chest.getX() + ", " + chest.getY() + ", " + chest.getZ() + "." + "CoreInventory.Contents");

                        Inventory inv = Bukkit.createInventory(null, size, player.getName() + " §0personal loot chest: " + chestKey);
                        inv.setContents(contents);
                        player.openInventory(inv);
                    } catch (Exception e) {
                        MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerInteractPLC(" + event + ")", "This player's saved inventory is invalid! Path: " + "PersonalLootChests." + chestKey + "." + player.getName() + " equals: " + ConfigEditor.get("PersonalLootChests." + chestKey + "." + player.getName()));
                        return;
                    }

                    MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerInteractPLC(" + event + ")", "success");
                    return;
                }

                if (action.equals(Action.LEFT_CLICK_BLOCK)) {
                    if (player.getGameMode().equals(GameMode.CREATIVE)) {
                        player.openInventory(getDestroyPLCInventory(block));
                        MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onPlayerInteractPLC(" + event + ")", "success");
                    }

                }

            }
        } catch (Exception e) {
            MessageMaster.sendFailMessage("MakePersonalLootChestCmdLst", "onPlayerInteractPLC(" + event + ")", e);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPLCInventoryClick(InventoryClickEvent event) {
        try {

            Player player = (Player) event.getWhoClicked();
            World world = event.getWhoClicked().getWorld();
            Inventory inventory = event.getClickedInventory();
            ItemStack item = event.getCurrentItem();

            if (inventory == null) {
                MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + ")", "There is no inventory clicked.");
                return;
            }

            // TODO: Make more specific? (&& event.getInventory() instanceof ArtificialInventory)
            if (event.getView().getTitle().equals("§0Destroy Personal Loot Chest?")) {
                String plcKey = InventoryManager.getCleanString(Objects.requireNonNull(Objects.requireNonNull(inventory.getItem(4)).getItemMeta()).getDisplayName());

                if (item.equals(InventoryManager.getYesItem())) {
                    // YES ITEM

                    // Iterates through the PLCs
                    for (String current : Objects.requireNonNull(ConfigEditor.getSectionKeys("PersonalLootChests"))) {

                        assert plcKey != null;
                        if (plcKey.equals(current)) {
                            // There is a PLC with this key

                            String[] plcStringArr = plcKey.split(", ");
                            // TODO: Test if coords are correct
                            double plcX = Double.parseDouble(plcStringArr[0]);
                            double plcY = Double.parseDouble(plcStringArr[1]);
                            double plcZ = Double.parseDouble(plcStringArr[2]);

                            Location location = new Location(world, plcX, plcY, plcZ);

                            // Deletes the PLC config entry
                            ConfigEditor.clearPath("PersonalLootChests." + current);
                            // Destroys the teleporter
                            location.getBlock().setType(Material.AIR);

                            player.closeInventory();

                            MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + "), player chose \"Yes\"", "success");
                            return;
                        }
                    }

                }
                else if (item.equals(InventoryManager.getNoItem())) {
                    // NO ITEM

                    player.closeInventory();
                    MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + "), player chose \"No\"", "success");
                    return;
                }

            }

            if (event.getView().getTitle().startsWith(player.getName() + " §0personal loot chest: ")) {
                // works?
                String key = event.getView().getTitle().split(": ")[1];
                // TODO: rem + BUG IS HERE! Saving inv overwrites all paths of "PersonalLootChests.[key]". TEST SINGLE ITEMSTACK RELOAD CONSISTENCY. PROBLEM WITH RELOADS. TEST WITH MULTIPLE PLCS.
                // Saves the inventory contents
                //ConfigEditor.set("PersonalLootChests." + key + "." + player.getName() + ".Contents", event.getInventory().getContents());
                //player.getName();
                //ConfigEditor.set("PersonalLootChests.Test", "TestValue");
                System.out.println("PERSINV UPDATED! Path: PersonalLootChests." + key + "." + player.getName() + ".Contents");
                MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + ")", "success");
                return;
            }

            MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + ")", "Wrong inventory clicked.");
        } catch (NullPointerException e) {
            MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + ")", "No item clicked.");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + ")", e);
        }
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPLCInventoryClose(InventoryCloseEvent event) {
        try {

            Player player = (Player) event.getPlayer();
            Inventory inventory = event.getInventory();

            if (event.getView().getTitle().startsWith(player.getName() + " §0personal loot chest: ")) {
                String key = event.getView().getTitle().split(": ")[1];
                // Saves the inventory contents
                ConfigEditor.set("PersonalLootChests." + key + "." + player.getName() + ".Contents", event.getInventory().getContents());
                // TODO!!!
                System.out.println("PERSINV SAVED (INVCLOSE)!");
                MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + ")", "success");
            }

        } catch (Exception e) {
            MessageMaster.sendFailMessage("MakePersonalLootChestCmdLst", "onDestroyPLCInventoryClick(" + event + ")", e);
        }
    }

    private Inventory getDestroyPLCInventory(Block plc) {
        try {

            Inventory inv = InventoryManager.createArtificialInventory(1, "§0Destroy Personal Loot Chest?");

            inv.setItem(0, InventoryManager.getYesItem());
            inv.setItem(4, getPLCItem(plc));
            inv.setItem(8, InventoryManager.getNoItem());

            InventoryManager.fillEmptySlots(inv);

            MessageMaster.sendExitMessage("MakePersonalLootChestCmdLst", "getDestroyPLCInventory()", "success");
            return inv;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("MakePersonalLootChestCmdLst", "getDestroyPLCInventory()", e);
            return InventoryManager.getErrorInventory();
        }
    }

    private ItemStack getPLCItem(Block plc) {
        try {

            String itemName = plc.getX() + ", " + plc.getY() + ", " + plc.getZ();

            ItemStack item = new ItemBuilder(Material.CHEST, 1).setName(itemName).build();

            MessageMaster.sendExitMessage("PlayerInteractWithTeleporterListener", "getPLCItem()", "success");
            return item;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("PlayerInteractWithTeleporterListener", "getPLCItem()", e);
            return InventoryManager.getErrorItem();
        }
    }

}
