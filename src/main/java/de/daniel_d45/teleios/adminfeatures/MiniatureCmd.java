/*
 2020-2025
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.adminfeatures;

import de.daniel_d45.teleios.core.GlobalFunctions;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;


public class MiniatureCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@Nonnull CommandSender sender, @Nonnull Command command, @Nonnull String label, @Nonnull String[] args) {

        // Is command active check SC-1
        if (GlobalFunctions.inactiveCmdCheck("AdminFeatures.All", sender)) return true;

        // sender -> player SC-1
        Player player = GlobalFunctions.introduceSenderAsPlayer(sender);
        if (player == null) return true;

        // /miniature <scale> <x_max> <y_max> <z_max>
        if (args.length >= 4) {

            // Force-Gets float SC-1
            float scale = GlobalFunctions.introduceFloat(args[0], 0, 100, sender);
            if (scale == Float.MIN_VALUE) return false;
            if (scale == 0) return GlobalFunctions.invalidNumber(sender, args[0]);

            // Force-Gets int SC-1
            int x_max = GlobalFunctions.introduceInt(args[1], 0, 10, sender);
            if (x_max == Integer.MIN_VALUE) return false;

            // Force-Gets int SC-1
            int y_max = GlobalFunctions.introduceInt(args[2], 0, 10, sender);
            if (y_max == Integer.MIN_VALUE) return false;

            // Force-Gets int SC-1
            int z_max = GlobalFunctions.introduceInt(args[3], 0, 10, sender);
            if (z_max == Integer.MIN_VALUE) return false;

            StringBuilder cmd = new StringBuilder("summon block_display ~ ~1 ~ {Passengers:[");

            int x_min = -x_max;
            int y_min = -y_max;
            int z_min = -z_max;
            World world = player.getWorld();
            int playerX = player.getLocation().getBlockX();
            int playerY = player.getLocation().getBlockY();
            int playerZ = player.getLocation().getBlockZ();

            // TODO: slice miniature in pieces, spawn incrementally
            for (int currRelX = x_min; currRelX <= x_max; ++currRelX) {
                for (int currRelY = y_min; currRelY <= y_max; ++currRelY) {
                    for (int currRelZ = z_min; currRelZ <= z_max; ++currRelZ) {

                        BlockState blockState = world.getBlockAt(playerX + currRelX, playerY + currRelY, playerZ + currRelZ).getState();
                        String currName = blockState.getType().toString().toLowerCase();
                        if (currName.equals("air") || currName.equals("cave_air")) continue;

                        String currProps = extractProperties(blockState.getBlockData().getAsString());
                        cmd.append("{id:\"minecraft:block_display\",block_state:{Name:\"minecraft:" + currName + "\",Properties:{" + currProps + "}},transformation:[" + scale + ",0,0," + scale * currRelX + ",0," + scale + ",0," + scale * currRelY + ",0,0," + scale + "," + scale * currRelZ + ",0,0,0,1]}");
                        //                    transformation:[0.0625f,0.0000f,0.0000f,0.5625f,0.0000f,0.0625f,0.0000f,0.1250f,0.0000f,0.0000f,0.0625f,0.6250f,0.0000f,0.0000f,0.0000f,1.0000f]
                        //                                   "0.0625f,0.0000f,0.0000f,x.xxxxf,0.0000f,0.0625f,0.0000f,x.xxxxf,0.0000f,0.0000f,0.0625f,x.xxxxf,0.0000f,0.0000f,0.0000f,1.0000f"
                        //                                   "x_scale,               , x_pos ,       ,y_scale,       , y_pos ,               ,z_scale, z_pos ," ?
                        if (!(currRelX == x_max && currRelY == y_max && currRelZ == z_max)) {
                            cmd.append(",");
                        }
                    }
                }
            }

            cmd.append("]}");
            //Teleios.getServerObject().getConsoleSender().sendMessage(String.valueOf(cmd));
            player.performCommand(cmd.toString());
            return true;
        }
        return false;
    }

    private String extractProperties(String blockData) {
        int start = blockData.indexOf('[');
        int end = blockData.indexOf(']');
        if (start == -1 || end == -1 || start >= end) {
            return ""; // Kein gültiger Bereich
        }
        String properties = blockData.substring(start + 1, end);
        String[] pairs = properties.split(",");
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < pairs.length; i++) {
            String[] keyValue = pairs[i].split("=");
            if (keyValue.length == 2) {
                builder.append(keyValue[0]).append(":\"").append(keyValue[1]).append("\"");
                if (i < pairs.length - 1) {
                    builder.append(",");
                }
            }
        }

        return builder.toString();
    }

}
