/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.adminfeatures.AdminFeatures;
import de.daniel_d45.teleios.bettergameplay.BetterGameplay;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

import javax.annotation.Nonnull;
import java.util.Objects;


public class ManageteleiosCmdLst implements CommandExecutor, Listener {

    final String MTL_INV_NAME = "§0Manage Teleios functionality";
    final String AF_INV_NAME = "§0Manage AdminFeatures";
    final String BG_INV_NAME = "§0Manage BetterGameplay";

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // /mtl ...
        Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
        if (player == null) return true;

        player.openInventory(InventoryManager.getCurrMTLInv());
        return true;
    }

    /**
     * Implements the functionality when clicking on an item in the ManageTeleios inventory.
     */
    @EventHandler//(priority = EventPriority.LOW)
    public void onManageTeleiosInventoryClick(InventoryClickEvent event) {

        String itemName;
        try {
            itemName = Objects.requireNonNull(Objects.requireNonNull(event.getCurrentItem()).getItemMeta()).getDisplayName();
        } catch (NullPointerException e) {
            return;
        }

        Player player = (Player) event.getWhoClicked();
        ClickType clickType = event.getClick();
        String invName = event.getView().getTitle();

        switch (invName) {
            case MTL_INV_NAME:

                // ADMINFEATURES ITEM
                if (itemName.equals(Objects.requireNonNull(AdminFeatures.getSegmentItem().getItemMeta()).getDisplayName())) {
                    if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                        AdminFeatures.switchActivationstateAF();
                        refreshInv(MTL_INV_NAME);
                        return;
                    }
                    else if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
                        player.openInventory(InventoryManager.getCurrAFInv());
                        return;
                    }
                }

                // BETTERGAMEPLAY ITEM
                else if (itemName.equals(Objects.requireNonNull(BetterGameplay.getSegmentItem().getItemMeta()).getDisplayName())) {
                    if (clickType == ClickType.LEFT || clickType == ClickType.SHIFT_LEFT) {
                        BetterGameplay.switchActivationstateBG();
                        refreshInv(MTL_INV_NAME);
                        return;
                    }
                    else if (clickType == ClickType.RIGHT || clickType == ClickType.SHIFT_RIGHT) {
                        player.openInventory(InventoryManager.getCurrBGInv());
                        return;
                    }
                }
                break;
            case AF_INV_NAME:

                // BACK ITEM
                if (itemName.equals(Objects.requireNonNull(InventoryManager.getBackItem().getItemMeta()).getDisplayName())) {
                    player.openInventory(InventoryManager.getCurrMTLInv());
                }
                return;
            case BG_INV_NAME:

                // ENDERCHESTCOMMAND ITEM
                if (itemName.equals(Objects.requireNonNull(BetterGameplay.getEnderchestCmdItem().getItemMeta()).getDisplayName())) {
                    ConfigEditor.switchActivationstate("BetterGameplay.EnderchestCommand");
                    refreshInv(BG_INV_NAME);
                    return;
                }
                // TELEPORTERS ITEM
                else if (itemName.equals(Objects.requireNonNull(BetterGameplay.getTeleportersItem().getItemMeta()).getDisplayName())) {
                    BetterGameplay.switchActivationstateTeleporters();
                    refreshInv(BG_INV_NAME);
                    return;
                }
                // BACK ITEM
                else if (itemName.equals(Objects.requireNonNull(InventoryManager.getBackItem().getItemMeta()).getDisplayName())) {
                    player.openInventory(InventoryManager.getCurrMTLInv());
                    return;
                }
                else {
                    return;
                }
        }

    }

    private void refreshInv(String title) {
        // redundant code but players get latest version of the inventory
        switch (title) {
            case MTL_INV_NAME:
                for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                    if (currentPlayer.getOpenInventory().getTitle().equals(title)) {
                        currentPlayer.openInventory(InventoryManager.getCurrMTLInv());
                    }
                }
                break;
            case AF_INV_NAME:
                for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                    if (currentPlayer.getOpenInventory().getTitle().equals(title)) {
                        currentPlayer.openInventory(InventoryManager.getCurrAFInv());
                    }
                }
                break;
            case BG_INV_NAME:
                for (Player currentPlayer : Bukkit.getOnlinePlayers()) {
                    if (currentPlayer.getOpenInventory().getTitle().equals(title)) {
                        currentPlayer.openInventory(InventoryManager.getCurrBGInv());
                    }
                }
                break;
        }
    }

}
