/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
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


public class ManageteleiosCmdLst implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                return true;
            }

            // Specifies /manageteleios
            player.openInventory(InventoryManager.getManageTeleiosInventory());
            return true;
        } catch (Exception e) {
            GlobalMethods.sendErrorFeedback(sender);
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

        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        ClickType clickType = event.getClick();
        String invName = event.getView().getTitle();

        ItemStack item_AF = AdminFeatures.getSegmentItem();
        ItemStack item_BG = BetterGameplay.getSegmentItem();
        ItemStack item_PS = PassiveSkills.getSegmentItem();
        ItemStack item_WM = WorldMaster.getSegmentItem();

        // Item null check
        if (item == null) {
            return;
        }

        switch (invName) {
            // 1. MANAGETELEIOS INVENTORY
            case "§0Manage Teleios functionality":

                // ADMINFEATURES ITEM
                if (item.equals(item_AF)) {

                    if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                        AdminFeatures.switchActivationstateAF();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;
                    }
                    else if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
                        //player.openInventory(InventoryManager.getManageAFInventory());
                        return;
                    }

                }

                // BETTERGAMEPLAY ITEM
                else if (item.equals(item_BG)) {

                    if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                        BetterGameplay.switchActivationstateBG();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;
                    }
                    else if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
                        player.openInventory(InventoryManager.getManageBGInventory());
                        return;
                    }

                }

                // PASSIVESKILLS ITEM
                else if (item.equals(item_PS)) {
                    return;
/*
                    if (clickType == ClickType.LEFT) {
                        PassiveSkills.switchActivationstatePS();
                        player.openInventory(InventoryManager.getManageTeleiosInventory());
                        return;

                    }
                    else if (clickType == ClickType.RIGHT) {
                        player.openInventory(InventoryManager.getManagePSInventory());
                        return;
                    }
*/
                }

                // WORLDMASTER ITEM
                else if (item.equals(item_WM)) {
                    return;
                /*

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
                    return;
                */
                }

                break;

            // 1.1 ADMINFEATURES INVENTORY
            case "§0Manage AdminFeatures":

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;
                }
                else {
                    return;
                }


                // 1.2 BETTERGAMEPLAY INVENTORY
            case "§0Manage BetterGameplay":

                // ENDERCHESTCOMMAND ITEM
                if (item.equals(BetterGameplay.getEnderchestCmdItem())) {
                    ConfigEditor.switchActivationstate("BetterGameplay.EnderchestCommand");
                    player.openInventory(InventoryManager.getManageBGInventory());
                    return;

                }
                // TELEPORTERS ITEM
                else if (item.equals(BetterGameplay.getTeleportersItem())) {
                    BetterGameplay.switchActivationstateTeleporters();
                    player.openInventory(InventoryManager.getManageBGInventory());
                    return;

                }
                // BACK ITEM
                else if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    return;
                }

                // 1.3 PASSIVESKILLS INVENTORY
            case "§0Manage PassiveSkills":
/*
                // TODO

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    return;
                }

*/
                // 1.4 WORLDMASTER INVENTORY
            case "§0Manage WorldMaster":
/*
                // TODO

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                    return;

                }
                else {
                    return;
                }

*/
            default:
                return;
        }
    }

}
