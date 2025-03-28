/*
 2020-2024
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.annotation.Nonnull;


public class JoinmessageCmdLst implements CommandExecutor, Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        // TODO: Make individual ActivationState for command
        // Tests whether the joinmessage is enabled and the command is active
        if (ConfigEditor.hasValue("JoinMessage", true) && ConfigEditor.isActive("AdminFeatures.All")) {
            Player player = event.getPlayer();
            event.setJoinMessage("§6" + player.getName() + " §9joined the server.");
        }
    }

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

        // /joinmessage
        if (args.length == 0) {

            Object joinMessageValue = ConfigEditor.get("JoinMessage");

            // TODO: test whether instanceof is appropriate
            if (!(joinMessageValue instanceof Boolean)) {
                ConfigEditor.set("JoinMessage", false);
                return true;
            }

            boolean joinMessageEnabled = (boolean) ConfigEditor.get("JoinMessage");

            if (joinMessageEnabled) sender.sendMessage("§aThe custom join message is §6enabled§a.");
            else sender.sendMessage("§aThe custom join message is §6disabled§a.");
            return true;
        }

        // /joinmessage enable|true ...
        if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("true")) {
            ConfigEditor.set("JoinMessage", true);
            sender.sendMessage("§aThe custom join message is now §6enabled§a!");
            return true;
        }
        // /joinmessage disable|false ...
        else if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("false")) {
            ConfigEditor.set("JoinMessage", false);
            sender.sendMessage("§aThe custom join message is now §6disabled§a!");
            return true;
        }

        return GlobalFunctions.invalidArg(sender);
    }

}
