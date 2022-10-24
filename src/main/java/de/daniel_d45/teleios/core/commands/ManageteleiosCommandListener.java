/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.commands;

import de.daniel_d45.teleios.adminfeatures.SegmentManagerAF;
import de.daniel_d45.teleios.bettergameplay.SegmentManagerBG;
import de.daniel_d45.teleios.core.util.ConfigEditor;
import de.daniel_d45.teleios.core.util.InventoryManager;
import de.daniel_d45.teleios.core.util.MessageMaster;
import de.daniel_d45.teleios.creatureevolution.SegmentManagerCE;
import de.daniel_d45.teleios.passiveskills.SegmentManagerPS;
import de.daniel_d45.teleios.worldmaster.SegmentManagerWM;
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


public class ManageteleiosCommandListener implements CommandExecutor, Listener {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendSkipMessage("WarppouchCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender is not a player.");
                return true;
            }

            // Sender permission check
            if (!sender.hasPermission("teleios.core.manageteleios")) {
                sender.sendMessage("§cError: Missing Permissions!");
                MessageMaster.sendSkipMessage("ManageteleiosCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player doesn't have the needed permissions.");
            }

            switch (args.length) {
                case 0:
                    // Specifies /manageteleios
                    try {

                        player.openInventory(InventoryManager.getManageTeleiosInventory());

                        MessageMaster.sendSuccessMessage("ManageteleiosCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                        return true;
                    } catch (Exception e) {
                        MessageMaster.sendFailMessage("ManageteleiosCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                default:
                    MessageMaster.sendSkipMessage("ManageteleiosCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
                    return false;
            }

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
                MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), no item clicked.");
                return;
            }

            // 1. MANAGETELEIOS INVENTORY
            if (event.getView().getTitle().equals("§0Manage Teleios functionality")) {

                // ADMINFEATURES ITEM
                if (item.equals(SegmentManagerAF.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        SegmentManagerAF.switchActivationstateAF();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManageAFInventory());
                        return;
                    }

                    // BETTERGAMEPLAY ITEM
                }
                else if (item.equals(SegmentManagerBG.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        SegmentManagerBG.switchActivationstateBG();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManageBGInventory());
                        return;
                    }

                    // CREATUREEVOLUTION ITEM
                }
                else if (item.equals(SegmentManagerCE.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        // TODO
                        // SegmentManagerCE.switchActivationstateCE();
                        // player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManageCEInventory());
                        return;
                    }

                    // PASSIVESKILLS ITEM
                }
                else if (item.equals(SegmentManagerPS.getSegmentItem())) {

                    if (clickType == ClickType.LEFT) {
                        SegmentManagerPS.switchActivationstatePS();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManagePSInventory());
                        return;
                    }

                    // WORLDMASTER ITEM
                }
                else if (item.equals(SegmentManagerWM.getSegmentItem())) {

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
                    MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), the clicked item has no function.");
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
                    MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), the clicked item has no function.");
                    return;
                }

                // 1.2 BETTERGAMEPLAY INVENTORY
            }
            else if (event.getView().getTitle().equals("§0Manage BetterGameplay")) {

                // ENDERCHESTCOMMAND ITEM
                if (item.equals(SegmentManagerBG.getEnderchestCommandItem())) {
                    ConfigEditor.switchActivationstate("BetterGameplay.EnderchestCommand");
                    player.openInventory(InventoryManager.getManageBGInventory());
                    return;

                    // TELEPORTERS ITEM
                }
                else if (item.equals(SegmentManagerBG.getTeleportersItem())) {
                    SegmentManagerBG.switchActivationstateTeleporters();
                    player.openInventory(InventoryManager.getManageBGInventory());
                    return;

                    // BACK ITEM
                }
                else if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), the clicked item has no function.");
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
                    MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), the clicked item has no function.");
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
                    MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), the clicked item has no function.");
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
                    MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), the clicked item has no function.");
                    return;
                }

            }
            else {
                MessageMaster.sendSkipMessage("onManageTeleiosInventoryClick", "Skipped method onManageTeleiosInventoryClick(" + event + "), wrong inventory.");
                return;
            }

            MessageMaster.sendSuccessMessage("onManageTeleiosInventoryClick", "onInventoryClick(" + event + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("onManageTeleiosInventoryClick", "onInventoryClick(" + event + ")", e);
        }
    }

}
