/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import javax.annotation.Nonnull;


public class MuteCmdLst implements CommandExecutor, Listener {

    @EventHandler
    public void onMutedChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();

        if (ConfigEditor.containsPath("MutedPlayers." + player.getName())) {
            // Mutes the player
            event.setCancelled(true);
            player.sendMessage("§cYou are muted!");
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

        Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
        if (player == null) return true;

        // /mute [Player]
        try {

            Player target = Bukkit.getPlayer(args[0]);

            // Target online check
            if (target == null) {
                player.sendMessage("§cThis player is not online!");
                return true;
            }

            // Player already muted check
            if (ConfigEditor.containsPath("MutedPlayers." + player.getName())) {
                player.sendMessage("§6" + target.getName() + " §ais already muted!");
                return true;
            }

            // Adds the target to the muted players list
            ConfigEditor.set("MutedPlayers." + target.getName(), "1");
            player.sendMessage("§aMuted §6" + target.getName() + "§a!");
            target.sendMessage("§cYou have been muted!");

            return true;
        } catch (Exception e) {
            player.sendMessage("§cWrong arguments!");
            return false;
        }
    }

}
