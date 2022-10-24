/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.bettergameplay.commands;

import de.daniel_d45.teleios.core.util.ConfigEditor;
import de.daniel_d45.teleios.core.util.MessageMaster;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;


public class WarpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        try {

            // Activation state check
            if (!ConfigEditor.isActive("BetterGameplay.All")) {
                sender.sendMessage("§cThis command is not active.");
                MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the command is deactivated.");
                return true;
            }

            // Sender player check
            if (!(sender instanceof Player player)) {
                sender.sendMessage("§cYou are no player!");
                MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender is not a player.");
                return true;
            }

            // Player permission check
            if (!player.hasPermission("teleios.bettergameplay.warp")) {
                player.sendMessage("§cMissing Permissions!");
                MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the sender doesn't have the needed permissions.");
                return true;
            }

            // Implements the two ways to successfully run the command
            if (args.length == 0 || (args.length == 1 && args[0].equalsIgnoreCase("list"))) {
                // Specifies /warp list
                if (args[0].equalsIgnoreCase("list")) {
                    try {

                        String[] warppoints = ConfigEditor.getSectionKeys("Warppoints").toArray(new String[0]);
                        String[] teleporters = ConfigEditor.getSectionKeys("Teleporters").toArray(new String[0]);

                        // Warppoints and teleporters check
                        if (warppoints.length == 0 && teleporters.length == 0) {
                            // There are no warppoints or teleporters
                            player.sendMessage("§eThere are no warppoints or teleporters yet!");
                            MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), there are no warppoints or teleporters.");
                            return true;
                        }

                        if (warppoints.length > 0) {
                            // Lists all warppoints

                            player.sendMessage("§a--------------------");
                            player.sendMessage("§aExisting warppoints:");

                            StringBuilder warppointsMsg = new StringBuilder();

                            // Iterates through the warppoints
                            for (int i = 0; i < warppoints.length; i++) {

                                warppointsMsg.append("§6").append(warppoints[i]);
                                // Only adds commas if it's not the last name
                                if (i < (warppoints.length - 1)) {
                                    warppointsMsg.append("§a, ");
                                }
                            }

                            player.sendMessage(warppointsMsg.toString());
                            player.sendMessage("§a--------------------");
                        }

                        // TODO: Check whether the teleporter is obstructed
                        if (teleporters.length > 0) {
                            // Lists all teleporters

                            player.sendMessage("§a--------------------");
                            player.sendMessage("§aExisting teleporters:");

                            StringBuilder teleportersMsg = new StringBuilder();

                            // Iterates through the teleporters
                            for (int i = 0; i < teleporters.length; ++i) {

                                Location unchangedLoc = (Location) ConfigEditor.get("Teleporters." + teleporters[i]);

                                Location loc1List = new Location(unchangedLoc.getWorld(), unchangedLoc.getX(), unchangedLoc.getY(), unchangedLoc.getZ());
                                loc1List.setY(loc1List.getY() + 1);

                                Location loc2List = new Location(unchangedLoc.getWorld(), unchangedLoc.getX(), unchangedLoc.getY(), unchangedLoc.getZ());
                                loc2List.setY(loc2List.getY() + 2);

                                if (loc1List.getBlock().getType() == Material.AIR || loc1List.getBlock().getType() == Material.CAVE_AIR && loc2List.getBlock().getType() == Material.AIR || loc2List.getBlock().getType() == Material.CAVE_AIR) {
                                    teleportersMsg.append("§6").append(teleporters[i]);
                                }
                                else {
                                    teleportersMsg.append("§7").append(teleporters[i]);
                                }

                                // Only adds commas if it's not the last name
                                if (i < teleporters.length - 1) {
                                    teleportersMsg.append("§a, ");
                                }

                                // Resets the values (active bug fix)
                                // loc1List.setY(loc1List.getY() - 1);
                                // loc2List.setY(loc2List.getY() - 2);
                            }

                            player.sendMessage(teleportersMsg.toString());
                            player.sendMessage("§a--------------------");

                            MessageMaster.sendSuccessMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                            return true;
                        }

                    } catch (NullPointerException e) {
                        // TODO: Check usage
                        player.sendMessage("§eThere are no warppoints or teleporters yet!");
                        MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), there are no warppoints or teleporters.");
                        return true;
                    } catch (Exception e) {
                        player.sendMessage("§cCould not list all warppoints!");
                        MessageMaster.sendFailMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), listing all warppoints", e);
                        return false;
                    }

                }
                else {
                    // Specifies /warp [Warppoint/Teleporter]
                    try {

                        String[] warppoints = ConfigEditor.getSectionKeys("Warppoints").toArray(new String[0]);
                        String[] teleporters = ConfigEditor.getSectionKeys("Teleporters").toArray(new String[0]);

                        boolean match = false;

                        if (warppoints.length == 0 && teleporters.length == 0) {
                            // There are no warppoints nor teleporters
                            player.sendMessage("§eThere are no warppoints or teleporters yet! Add a warppoint by using §6/warppoint add [name] §eor place a teleporter!");
                            MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), There are no warppoints nor teleporters.");
                            return true;
                        }

                        if (warppoints.length > 0) {
                            // There are warppoints

                            // Iterates through the warppoints
                            for (String warppoint : warppoints) {

                                if (args[0].equals(warppoint)) {
                                    // WARPPOINT MATCH
                                    try {

                                        match = true;

                                        player.teleport((Location) ConfigEditor.get("Warppoints." + warppoint));
                                        player.sendMessage("§aYou have been warped to §6" + warppoint + "§a.");

                                        MessageMaster.sendSuccessMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                                        return true;
                                    } catch (Exception e) {
                                        player.sendMessage("§cCould not teleport you to that warppoint!");
                                        MessageMaster.sendFailMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), teleporting the player to the warppoint", e);
                                        return false;
                                    }
                                }
                            }
                            // After iterating through the warppoints
                        }

                        // Check for teleporters
                        if (teleporters.length > 0) {
                            // There are teleporters

                            // Iterates through the teleporters
                            for (String teleporter : teleporters) {

                                if (args[0].equals(teleporter)) {
                                    // TELEPORTER MATCH
                                    try {

                                        Location unchangedLocation = (Location) ConfigEditor.get("Teleporters." + teleporter);
                                        Location tpLoc = new Location(unchangedLocation.getWorld(), unchangedLocation.getX(), unchangedLocation.getY(), unchangedLocation.getZ());
                                        Location playerLoc = player.getLocation();

                                        // Only teleports in the same dimension work
                                        if (!player.getWorld().equals(tpLoc.getWorld())) {
                                            player.sendMessage("§cYou are not in the same dimension as the teleporter!");
                                            MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), player is in the wrong dimension.");
                                            return true;
                                        }

                                        match = true;

                                        int requiredPearls = getRequiredPearls(tpLoc, playerLoc);
                                        int posessedPearls = 0;

                                        // gets the posessed pearls from the warppouch
                                        if (ConfigEditor.get("Warppouch." + player.getName()) != null) {
                                            posessedPearls = Integer.parseInt(ConfigEditor.get("Warppouch." + player.getName()).toString());
                                        }
                                        else {
                                            player.sendMessage("§eYou don't have a warppouch!");
                                            MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player doesn't have a warppouch.");
                                            return true;
                                        }

                                        // Enough pearls check
                                        if (posessedPearls < requiredPearls) {
                                            player.sendMessage("§cYou don't have enough ender pearls in your warppouch! Use §6/warppouch put [Amount]§c.");
                                            MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), the player doesn't have enough ender pearls.");
                                            return true;
                                        }

                                        Location feetLoc = new Location(unchangedLocation.getWorld(), unchangedLocation.getX(), unchangedLocation.getY(), unchangedLocation.getZ());
                                        feetLoc.setY(feetLoc.getY() + 1);
                                        Location headLoc = new Location(unchangedLocation.getWorld(), unchangedLocation.getX(), unchangedLocation.getY(), unchangedLocation.getZ());
                                        headLoc.setY(headLoc.getY() + 2);

                                        // Two blocks free check
                                        if ((feetLoc.getBlock().getType() != Material.AIR && feetLoc.getBlock().getType() != Material.CAVE_AIR) && (headLoc.getBlock().getType() != Material.AIR && feetLoc.getBlock().getType() != Material.CAVE_AIR)) {
                                            player.sendMessage("§cThis teleporter is obstructed!");

                                            // TODO: Necessary?
                                            // Resets the values
                                            feetLoc.setY(feetLoc.getY() - 1);
                                            headLoc.setY(headLoc.getY() - 2);

                                            ConfigEditor.set("Teleporters." + teleporter, unchangedLocation);

                                            MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), that teleporter is obstructed.");
                                        }
                                        else {

                                            tpLoc.setX(tpLoc.getX() + 0.5);
                                            tpLoc.setY(tpLoc.getY() + 1);
                                            tpLoc.setZ(tpLoc.getZ() + 0.5);

                                            // Removes the required ender pearls
                                            ConfigEditor.set("Warppouch." + player.getName(), (int) ConfigEditor.get("Warppouch." + player.getName()) - requiredPearls);
                                            player.teleport(tpLoc);
                                            player.sendMessage("§aYou have been warped to §6" + teleporter + " §afor §6" + requiredPearls + " ender pearl(s).");

                                            // TODO: Necessary?
                                            // Resets the values.
                                            tpLoc.setX(tpLoc.getX() - 0.5);
                                            tpLoc.setY(tpLoc.getY() - 1);
                                            tpLoc.setZ(tpLoc.getZ() - 0.5);

                                            ConfigEditor.set("Teleporters." + teleporter, unchangedLocation);

                                            MessageMaster.sendSuccessMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")");
                                        }

                                        return true;
                                    } catch (Exception e) {
                                        player.sendMessage("§cCould not teleport you to that teleporter!");
                                        MessageMaster.sendFailMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), teleporting the player to the teleporter", e);
                                        return false;
                                    }
                                }
                            }
                        }

                        // TODO: Needed?
                        player.sendMessage("§cThere is no warppoint or teleporter with the name §6" + args[0] + "§c! Use §6/warp list §cto list all warppoints and teleporters!");
                        MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), there is no warppoint or teleporter with that name.");
                        return true;

                    } catch (Exception e) {
                        player.sendMessage("§cCould not extecute the command correctly!");
                        MessageMaster.sendFailMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
                        return false;
                    }
                }
            }

            // Wrong amount of arguments
            player.sendMessage("§cWrong amount of arguments!");
            MessageMaster.sendSkipMessage("WarpCommand", "Skipped method onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + "), wrong amount of arguments.");
            return false;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("WarpCommand", "onCommand(" + sender + ", " + command + ", " + label + ", " + Arrays.toString(args) + ")", e);
            return false;
        }

    }

    private int getRequiredPearls(Location tpLoc, Location playerLoc) {
        try {

            int blocksPerPearl;

            if (ConfigEditor.get("BlocksPerPearl") != null && Integer.parseInt(ConfigEditor.get("BlocksPerPearl").toString()) > 0) {

                blocksPerPearl = Integer.parseInt(ConfigEditor.get("BlocksPerPearl").toString());
            }
            else {
                MessageMaster.sendSkipMessage("WarpCommand", "Skipped method getRequiredPearls(" + tpLoc + ", " + playerLoc + "), the BlocksPerPearl argument is invalid.");
                return -1;
            }

            double xdist = Math.abs(tpLoc.getX() - playerLoc.getX());
            double ydist = Math.abs(tpLoc.getY() - playerLoc.getY());
            double zdist = Math.abs(tpLoc.getZ() - playerLoc.getZ());

            int requiredPearls = (int) Math.ceil((Math.sqrt(Math.pow(ydist, 2) + Math.pow(zdist, 2) + Math.pow(xdist, 2))) / blocksPerPearl);

            MessageMaster.sendSuccessMessage("WarpCommand", "getRequiredPearls(" + tpLoc + ", " + playerLoc + ")");
            return requiredPearls;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("WarpCommand", "getRequiredPearls(" + tpLoc + ", " + playerLoc + ")", e);
            return -1;
        }

    }

}
