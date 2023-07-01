/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.adminfeatures.AdminFeatures;
import de.daniel_d45.teleios.bettergameplay.BetterGameplay;
import de.daniel_d45.teleios.passiveskills.PassiveSkills;
import de.daniel_d45.teleios.worldmaster.WorldMaster;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;


public class ManageteleiosCmdLst implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendExitMessage("WarppouchCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "the sender is not a player.");
                return true;
            }

            // Specifies /manageteleios
            player.openInventory(InventoryManager.getManageTeleiosInventory());

            MessageMaster.sendExitMessage("ManageteleiosCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", "success");
            return true;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }
    }

    /**
     * This listener method implements the functionality when clicking an item in the ManageTeleios inventory.
     *
     * @param event [InventoryClickEvent] The event triggering this method
     */
    @EventHandler
    public void onManageTeleiosInventoryClick(InventoryClickEvent event) {
        try {

            Player player = (Player) event.getWhoClicked();
            ItemStack item = event.getCurrentItem();
            ClickType clickType = event.getClick();

            // Item null check
            if (item == null) {
                MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "no item clicked.");
                return;
            }

            // 1. MANAGETELEIOS INVENTORY
            if (event.getView().getTitle().equals("§0Manage Teleios functionality")) {

                // ADMINFEATURES ITEM
                if (item.equals(AdminFeatures.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        AdminFeatures.switchActivationstateAF();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManageAFInventory());
                        return;
                    }

                    // BETTERGAMEPLAY ITEM
                }
                else if (item.equals(BetterGameplay.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        BetterGameplay.switchActivationstateBG();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManageBGInventory());
                        return;
                    }

                    // CREATUREEVOLUTION ITEM
                }
                else if (item.equals(PassiveSkills.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        PassiveSkills.switchActivationstatePS();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManagePSInventory());
                        return;
                    }

                    // WORLDMASTER ITEM
                }
                else if (item.equals(WorldMaster.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        // TODO
                        // SegmentManagerWM.switchActivationstateWM();
                        // player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManageWMInventory());
                        return;
                    }

                }
                else {
                    MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "the clicked item has no function.");
                    return;
                }

                // 1.1 ADMINFEATURES INVENTORY
            }
            else if (event.getView().getTitle().equals("§0Manage AdminFeatures")) {

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "the clicked item has no function.");
                    return;
                }

                // 1.2 BETTERGAMEPLAY INVENTORY
            }
            else if (event.getView().getTitle().equals("§0Manage BetterGameplay")) {

                // ENDERCHESTCOMMAND ITEM
                if (item.equals(BetterGameplay.getEnderchestCommandItem())) {
                    ConfigEditor.switchActivationstate("BetterGameplay.EnderchestCommand");
                    player.openInventory(InventoryManager.getManageBGInventory());
                    return;

                    // TELEPORTERS ITEM
                }
                else if (item.equals(BetterGameplay.getTeleportersItem())) {
                    BetterGameplay.switchActivationstateTeleporters();
                    player.openInventory(InventoryManager.getManageBGInventory());
                    return;

                    // BACK ITEM
                }
                else if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "the clicked item has no function.");
                    return;
                }

                // 1.3 CREATUREEVOLUTION INVENTORY
            }
            else if (event.getView().getTitle().equals("§0Manage CreatureEvolution")) {

                // TODO

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "the clicked item has no function.");
                    return;
                }

                // TODO Integrate ImmersivePlus, update hierarchy (1.x)
                // 1.4 PASSIVESKILLS INVENTORY
            }
            else if (event.getView().getTitle().equals("§0Manage PassiveSkills")) {

                // TODO

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "the clicked item has no function.");
                    return;
                }

                // 1.5 WORLDMASTER INVENTORY
            }
            else if (event.getView().getTitle().equals("§0Manage WorldMaster")) {

                // TODO

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "the clicked item has no function.");
                    return;
                }

            }
            else {
                MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onManageTeleiosInventoryClick(" + event + ")", "wrong inventory.");
                return;
            }

            MessageMaster.sendExitMessage("ManageteleiosCommandListener", "onInventoryClick(" + event + ")", "success");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("ManageteleiosCommandListener", "onInventoryClick(" + event + ")", e);
        }
    }

}
