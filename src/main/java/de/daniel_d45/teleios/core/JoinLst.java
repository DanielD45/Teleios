/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.bettergameplay.BetterGameplay;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class JoinLst implements Listener {

    /**
     * Method fires when a player joins and initiates the player's paths in the config file if the player
     * is not yet listed.
     *
     * @param event [PlayerJoinEvent]
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // BetterGameplay segment
        BetterGameplay.initiateWarppouch(player.getName());
        // PassiveSkills segment
        //PassiveSkills.initiatePlayerRecords(player.getName());
    }

}
