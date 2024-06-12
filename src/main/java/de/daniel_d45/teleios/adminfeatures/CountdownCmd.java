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


// TODO: WIP
public class CountdownCmd implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // Activation state check
        if (!ConfigEditor.isActive("AdminFeatures.All")) {
            sender.sendMessage("§cThis command is not active.");
            return true;
        }

        // Permission check
        if (!sender.hasPermission("teleios.adminfeatures.countdown")) {
            sender.sendMessage("§cMissing Permissions!");
            return true;
        }

        switch (args.length) {
            case 2:
                // Specifies /countdown [Duration] [EndMessage]
                try {
                    //TODO

                    return true;
                } catch (Exception e) {
                    sender.sendMessage("§cWrong arguments!");
                    return false;
                }

            default:
                // Wrong amount of arguments
                sender.sendMessage("§cWrong amount of arguments!");
                return false;
        }

    }

}
