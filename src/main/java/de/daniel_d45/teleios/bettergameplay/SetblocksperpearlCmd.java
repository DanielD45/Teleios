/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import javax.annotation.Nonnull;


public class SetblocksperpearlCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {
        try {

            if (GlobalFunctions.cmdOffCheck("AdminFeatures.All", sender)) return true;

            switch (args.length) {
                case 0:
                    // /setblocksperpearl
                    int blocksPerPearl;

                    try {
                        blocksPerPearl = (int) ConfigEditor.get("BlocksPerPearl");
                    } catch (Exception e) {
                        sender.sendMessage("§cThe BlocksPerPearl argument in the config file is invalid!");
                        return true;
                    }

                    // Tells the sender the value of the BlocksPerPearl argument
                    sender.sendMessage("§aYou can currently warp §6" + blocksPerPearl + "§a blocks per ender pearl.");
                    return true;
                case 1:
                    // /setblocksperpearl [Amount]
                    try {

                        // Sender permission check
                        if (!sender.hasPermission("teleios.bettergameplay.setblocksperpearl")) {
                            sender.sendMessage("§cMissing Permissions!");
                            return true;
                        }

                        int bpp = Integer.parseInt(args[0]);

                        // Invalid argument check
                        if (bpp <= 0) {
                            sender.sendMessage("§cWrong arguments!");
                            return false;
                        }

                        // Sets the BlocksPerPearl argument to the specified value
                        ConfigEditor.set("BlocksPerPearl", bpp);
                        sender.sendMessage("§aThe BlocksPerPearl have been set to §6" + bpp + "§a.");
                        return true;
                    } catch (NumberFormatException e) {
                        sender.sendMessage("§cWrong argument!");
                        return false;
                    } catch (Exception e) {
                        sender.sendMessage("§cCould not execute the command correctly!");
                        return false;
                    }
                default:
                    sender.sendMessage("§cWrong amount of arguments!");
                    return false;
            }

        } catch (Exception e) {
            return false;
        }
    }

}
