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
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.reflection.qual.UnknownClass;


/**
 * This class is only used for storing code samples to copy in its methods.
 */
@UnknownClass
final class SampleCode {

    @DoNotCall
    static void introduceParameterOLD(Player player, CommandSender sender, Command command, String label,
                                      String[] args) {
        /*
         If a parameter can cause exceptions, introduce it using a try catch block.
        Also test for valid values inside try{} and throw new exceptions that will be caught by its catch statement to
        keep the parameter declaration and parameter "trimming" together.
        Objects.requireNonNull() is only used to satisfy the IDE.
        */

        int storedEPs;
        try {
            storedEPs = (int) ConfigEditor.get("Warppouch." + player.getName());
            if (storedEPs < 0) {
                throw new IllegalArgumentException("The warp pouch stores a negative amount of ender pearls!");
            }
        } catch (NullPointerException | ClassCastException | IllegalArgumentException e) {
            player.sendMessage("§cYour warp pouch is invalid!");
            return;
        }
    }

    @DoNotCall
    static boolean getUserInt(@NonNull CommandSender sender, @NonNull String[] args) {
        /*
        try {
            int INPUTNAME = GlobalMethods.trimInt(Integer.parseInt(INPUT), MIN_VALUE, MAX_VALUE);
        } catch (NumberFormatException e) {
            // The input is not an int
            sender.sendMessage("ERROR_MESSAGE");
            return true;
        }
        */
        return true;
    }

    @DoNotCall
    static boolean getUserString(@NonNull CommandSender sender, @NonNull String[] args) {
        /*
        String userString = INPUT;
        if (!GlobalMethods.stringUsable(userString, MIN_LENGTH, MAX_LENGTH)) {
            // The input is too long
            sender.sendMessage("ERROR_MESSAGE");
            return true;
        }
        */

        return true;
    }

    static void markEntities() {
    /*
    Mark entities, deleted on server restart:
    Entity?.setMetadata("Key",new FixedMetadataValue(Plugin.getInstance(), Value(Object)));

    Mark entities:
    Entity?.getPersistentDataContainer();
     */
    }

    @DoNotCall
    static void TabCompleter() {
        /*
        Class must extend TabCompleter.
        in method List<String> onTabComplete(...):
            return the list of options to complete
            return empty list => no tab completion
            return null => TabCompleter for player names
         */
    }

}
