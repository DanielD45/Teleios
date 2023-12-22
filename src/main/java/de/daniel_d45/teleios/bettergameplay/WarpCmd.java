/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.bettergameplay;

import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.GlobalMethods;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;


public class WarpCmd implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("BetterGameplay.All")) {
                sender.sendMessage("§cThis command is not active.");
                return true;
            }

            // Sender player check (for computing if teleporters are unreachable)
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                return true;
            }

            // Checks for warppoints and teleporters
            Set<String> wps = ConfigEditor.getSectionKeys("Warppoints");
            Set<String> tps = ConfigEditor.getSectionKeys("Teleporters");

            // Are there warppoints or teleporters check
            if (wps == null && tps == null) {
                sender.sendMessage("§eThere are no warppoints or teleporters yet!");
                return true;
            }

            // Specifies /warp (list)
            if (args.length == 0 || args[0].equalsIgnoreCase("list")) {

                StringBuilder listMessage = new StringBuilder();

                // Adds warppoints to the listMessage
                if (wps != null) {
                    String[] warppoints = wps.toArray(new String[0]);
                    listMessage.append("§a--------------------\n");
                    listMessage.append("§aExisting warppoints:\n");

                    // Iterates through the warppoints
                    for (int i = 0; i < warppoints.length; ++i) {

                        listMessage.append("§6").append(warppoints[i]);
                        // Only adds commas if it's not the last name
                        if (i < (warppoints.length - 1)) {
                            listMessage.append("§a, ");
                        }
                    }

                    listMessage.append("\n§a--------------------\n");
                }

                // TODO: Check whether the teleporter is reachable (dimension, cost)
                // Adds teleporters to the listMessage
                if (tps != null) {

                    String[] teleporters = tps.toArray(new String[0]);
                    listMessage.append("§a--------------------\n");
                    listMessage.append("§aExisting teleporters:\n");

                    // Iterates through the teleporters
                    for (int i = 0; i < teleporters.length; ++i) {

                        //TODO: Improve
                        Location currentTPLoc;
                        try {
                            currentTPLoc = Objects.requireNonNull((Location) ConfigEditor.get("Teleporters." + teleporters[i]));
                        } catch (NullPointerException e) {
                            return true;
                        }

                        Material FeetMat = new Location(currentTPLoc.getWorld(), currentTPLoc.getX(), currentTPLoc.getY() + 1, currentTPLoc.getZ()).getBlock().getType();
                        Material HeadMat = new Location(currentTPLoc.getWorld(), currentTPLoc.getX(), currentTPLoc.getY() + 2, currentTPLoc.getZ()).getBlock().getType();
                        Location playerLoc = player.getLocation();

                        // Teleporter unobstructed check
                        if (((FeetMat == Material.AIR || FeetMat == Material.CAVE_AIR) && (HeadMat == Material.AIR || HeadMat == Material.CAVE_AIR)) && currentTPLoc.getWorld() == playerLoc.getWorld()) {
                            // Lists the teleporter in gold
                            listMessage.append("§6").append(teleporters[i]);
                        }
                        else {
                            // Lists the teleporter in grey
                            listMessage.append("§7").append(teleporters[i]);
                        }

                        // Only adds commas if it's not the last name
                        if (i < teleporters.length - 1) {
                            listMessage.append("§a, ");
                        }

                        // Resets the values (active bug fix)
                        // loc1List.setY(loc1List.getY() - 1);
                        // loc2List.setY(loc2List.getY() - 2);
                    }

                    listMessage.append("\n§a--------------------");

                }

                sender.sendMessage(listMessage.toString());
                return true;

            }

            // Specifies /warp [WarppointName]
            if (wps != null) {
                String[] warppoints = wps.toArray(new String[0]);

                // Iterates through the warppoints
                for (String currentWP : warppoints) {

                    // Warppoint match check
                    if (args[0].equalsIgnoreCase(currentWP)) {

                        Location WPLoc;
                        // Checks if the warppoint's location is valid
                        try {
                            WPLoc = Objects.requireNonNull((Location) ConfigEditor.get("Warppoints." + currentWP));
                        } catch (NullPointerException e) {
                            player.sendMessage("§cThe warppoint's location is invalid");
                            return true;
                        }

                        // Teleports the player to the warppoint
                        player.teleport(WPLoc);
                        player.sendMessage("§aYou have been warped to §6" + currentWP + "§a.");
                        return true;
                    }
                }
                // No warppoint match, code continues
            }

            // Specifies /warp [TeleporterName]
            if (tps != null) {
                String[] teleporters = tps.toArray(new String[0]);

                // Iterates through the teleporters
                for (String currentTP : teleporters) {

                    // Teleporter match check
                    if (args[0].equalsIgnoreCase(currentTP)) {

                        Location playerLoc = player.getLocation();
                        Location TPLoc;
                        // Is teleporter location valid check
                        try {
                            TPLoc = Objects.requireNonNull((Location) ConfigEditor.get("Teleporters." + currentTP));
                        } catch (NullPointerException e) {
                            player.sendMessage("§cThat teleporter's location is invalid!");
                            return false;
                        }

                        // Warping only works intradimensional
                        if (!player.getWorld().equals(TPLoc.getWorld())) {
                            player.sendMessage("§cYou are not in the same dimension as this teleporter!");
                            return true;
                        }

                        int requiredPearls;
                        // requiredPearls invalid check
                        try {
                            requiredPearls = getRequiredPearls(TPLoc, playerLoc);
                            if (requiredPearls <= 0) {
                                throw new IllegalArgumentException("requiredPearls is negative!");
                            }
                        } catch (IllegalArgumentException e) {
                            return false;
                        }

                        int possessedPearls;
                        // Possessed pearls invalid check
                        try {
                            possessedPearls = Integer.parseInt(Objects.requireNonNull(ConfigEditor.get("Warppouch." + player.getName())).toString());
                            if (possessedPearls < 0) {
                                throw new NullPointerException("possessedPearls is invalid");
                            }
                        } catch (NullPointerException e) {
                            return true;
                        }

                        // Enough ender pearls check
                        if (possessedPearls < requiredPearls) {
                            player.sendMessage("§cYou don't have enough ender pearls in your warppouch! Use §6/warppouch deposit [Amount]§c to put ender pearls in your warp pouch.");
                            return true;
                        }

                        Location feetLoc = new Location(TPLoc.getWorld(), TPLoc.getX(), TPLoc.getY() + 1, TPLoc.getZ());
                        Location headLoc = new Location(TPLoc.getWorld(), TPLoc.getX(), TPLoc.getY() + 2, TPLoc.getZ());

                        // Teleporter obstruction check
                        if ((feetLoc.getBlock().getType() != Material.AIR && feetLoc.getBlock().getType() != Material.CAVE_AIR) || (headLoc.getBlock().getType() != Material.AIR && headLoc.getBlock().getType() != Material.CAVE_AIR)) {

                            // TODO: Necessary?
                            // Resets the values
                            //feetLoc.setY(feetLoc.getY() - 1);
                            //headLoc.setY(headLoc.getY() - 2);

                            player.sendMessage("§cThis teleporter is obstructed!");
                            return true;
                        }

                        // The location to teleport the player to
                        Location teleportLoc = new Location(TPLoc.getWorld(), TPLoc.getX() + 0.5, TPLoc.getY() + 1, TPLoc.getZ() + 0.5, TPLoc.getYaw(), TPLoc.getPitch());

                        // Removes the required amount of ender pearls
                        ConfigEditor.set("Warppouch." + player.getName(), possessedPearls - requiredPearls);
                        player.teleport(teleportLoc);

                        // TODO: Necessary?
                        // Resets the values.
                        //tpLoc.setX(tpLoc.getX() - 0.5);
                        //tpLoc.setY(tpLoc.getY() - 1);
                        //tpLoc.setZ(tpLoc.getZ() - 0.5);
                        //ConfigEditor.set("Teleporters." + currentTP, TPLoc);

                        player.sendMessage("§aYou have been warped to §6" + currentTP + " §afor §6" + requiredPearls + " ender pearl(s).");
                        return true;
                    }
                }
                // No warppoint or teleporter match
                sender.sendMessage("§cThere is no warppoint or teleporter with this name!");
                return true;
            }
            // Wrong arguments
            sender.sendMessage("§cWrong arguments!");
            return false;
        } catch (Exception e) {
            GlobalMethods.sendErrorFeedback(sender);
            return false;
        }
    }

    private int getRequiredPearls(Location tpLoc, Location playerLoc) {
        try {
            int blocksPerPearl;

            try {
                blocksPerPearl = Integer.parseInt(ConfigEditor.get("BlocksPerPearl").toString());
                if (blocksPerPearl <= 0) {
                    throw new NullPointerException("blocksPerPearl is invalid!");
                }
            } catch (NullPointerException e) {
                return -1;
            }

            double xDist = Math.abs(tpLoc.getX() - playerLoc.getX());
            double yDist = Math.abs(tpLoc.getY() - playerLoc.getY());
            double zDist = Math.abs(tpLoc.getZ() - playerLoc.getZ());

            return (int) Math.ceil((Math.sqrt(Math.pow(yDist, 2) + Math.pow(zDist, 2) + Math.pow(xDist, 2))) / blocksPerPearl);
        } catch (Exception e) {
            return -1;
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {

        ArrayList<String> list = new ArrayList<String>();
        Set<String> wps = ConfigEditor.getSectionKeys("Warppoints");
        Set<String> tps = ConfigEditor.getSectionKeys("Teleporters");

        // Warppoints exist check
        if (wps != null && wps.size() > 0) {
            list.addAll(wps);
        }

        // Teleporters exist check
        if (tps != null && tps.size() > 0) {
            list.addAll(tps);
        }

        return list;
    }

}
