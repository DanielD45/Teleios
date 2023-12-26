/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import com.google.errorprone.annotations.DoNotCall;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.checkerframework.common.reflection.qual.UnknownClass;

import java.util.Objects;


/**
 * This class is only used for storing code samples to copy in its methods.
 */
@UnknownClass
final class SampleCode {

    /**
     * This method holds sample code used to introduce parameters and trim them. Code origin: WarppouchCommand.java
     */
    @DoNotCall
    static void introduceParameter(Player player, CommandSender sender, Command command, String label, String[] args) {
        /*
        If a parameter can cause exceptions, introduce it using a try catch block.
        Also test for valid values inside try{} and throw new exceptions that will be caught by its catch statement to
        keep the parameter declaration and parameter "trimming" together.
        Objects.requireNonNull() is only used to satisfy the IDE.
        */

        // Start of sample
        int storedEPs;
        try {
            storedEPs = (int) Objects.requireNonNull(ConfigEditor.get("Warppouch." + player.getName()));
            if (storedEPs < 0) {
                throw new IllegalArgumentException("The warp pouch stores a negative amount of ender pearls!");
            }
        } catch (NullPointerException | ClassCastException | IllegalArgumentException e) {
            player.sendMessage("§cYour warp pouch is invalid!");
            return;
        }
        // End of sample
    }

    /**
     * This method holds sample code used to introduce parameters and trim them. Code origin: WarppouchCommand.java
     */
    @DoNotCall
    static void introduceParameter2(Player player, CommandSender sender, Command command, String label, String[] args) {

        // Start of sample
        int specifiedAmount;
        try {
            specifiedAmount = Integer.parseInt(args[1]);
            if (specifiedAmount <= 0) {
                player.sendMessage("§cInvalid amount of ender pearls!");
                return;
            }
        } catch (IllegalArgumentException e) {
            specifiedAmount = Integer.MAX_VALUE;
        }
        // End of sample
    }

    // Mark entities, deleted on server restart
    //Entity?.setMetadata("Key",new FixedMetadataValue(Plugin.getInstance(), Value(Object)));

    // Mark entities
    //Entity?.getPersistentDataContainer();

    @DoNotCall
    static void TabCompleter() {
        // Class must extend TabCompleter
        // in method List<String> onTabComplete(...):
        // return null => TabCompleter for player names
    }

}
