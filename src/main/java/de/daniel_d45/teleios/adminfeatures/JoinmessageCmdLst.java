/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.ConfigEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;


public class JoinmessageCmdLst implements CommandExecutor, Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {

            // TODO: Make individual ActivationState for command
            // Tests if the joinmessage is enabled and the command is active
            if (ConfigEditor.hasValue("JoinMessage", true) && ConfigEditor.isActive("AdminFeatures.All")) {

                // Sends join message
                Player player = event.getPlayer();
                event.setJoinMessage("§6" + player.getName() + " §9joined the server.");
            }
        } catch (NullPointerException e) {
            // The JoinMessage path in the config file doesn't exist
            // TODO: Create JoinMessage path?
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }

        // Specifies /joinmessage
        if (args.length == 0) {
            // TODO: fix config path if invalid
            boolean joinMessageEnabled = (boolean) Objects.requireNonNull(ConfigEditor.get("JoinMessage"));

            if (joinMessageEnabled) {
                sender.sendMessage("§aThe custom join message is §6enabled§a.");
            }
            else {
                sender.sendMessage("§aThe custom join message is §6disabled§a.");
            }

            return true;
        }

        // Sender permission check
        if (!sender.hasPermission("teleios.adminfeatures.joinmessage")) {
            sender.sendMessage("§cMissing Permissions!");
            return true;
        }

        // Specifies /joinmessage enable|true
        if (args[0].equalsIgnoreCase("enable") || args[0].equalsIgnoreCase("true")) {
            ConfigEditor.set("JoinMessage", true);
            sender.sendMessage("§aThe custom join message is now §6enabled§a!");
            return true;
        }

        // Specifies /joinmessage disable|false
        else if (args[0].equalsIgnoreCase("disable") || args[0].equalsIgnoreCase("false")) {
            ConfigEditor.set("JoinMessage", false);
            sender.sendMessage("§aThe custom join message is now §6disabled§a!");
            return true;
        }

        // Wrong arguments
        sender.sendMessage("§cWrong arguments!");
        return false;
    }

}
