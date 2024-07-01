/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.adminfeatures.AdminFeatures;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nonnull;


public class ManageteleiosCmdLst implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // /mtl ...
        Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
        if (player == null) return true;

        player.openInventory(InventoryManager.getManageTeleiosInventory());
        return true;
    }

    /**
     * Implements the functionality when clicking on an item in the ManageTeleios inventory.
     */
    @EventHandler//(priority = EventPriority.LOW)
    public void onManageTeleiosInventoryClick(InventoryClickEvent event) {

        ItemStack item = event.getCurrentItem();
        if (item == null) return;

        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();
        String invName = event.getView().getTitle();

        if (invName.equals("§0Manage Teleios functionality") && item.equals(AdminFeatures.getSegmentItem())) {
            if (ConfigEditor.get("Activationstates.AdminFeatures.All") == "ON") {
                ConfigEditor.set("Activationstates.AdminFeatures.All", "OFF");
            }
            else {
                ConfigEditor.set("Activationstates.AdminFeatures.All", "ON");
            }
        }

/*
        switch (invName) {
            // 1. MANAGETELEIOS INVENTORY
            case "§0Manage Teleios functionality":

                // ADMINFEATURES ITEM
                if (item.equals(AdminFeatures.getSegmentItem())) {

                    if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                        System.out.println("§aEVENT POSITIVE");
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
                else if (item.equals(BetterGameplay.getSegmentItem())) {

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
                break;

            // 1.1 ADMINFEATURES INVENTORY
            case "§0Manage AdminFeatures":

                // BACK ITEM
                if (item.equals(InventoryManager.getBackItem())) {
                    player.openInventory(InventoryManager.getManageTeleiosInventory());
                }
                return;

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

            default:
        }
*/
    }

}
