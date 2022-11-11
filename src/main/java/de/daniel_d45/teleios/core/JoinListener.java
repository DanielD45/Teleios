/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core;

import de.daniel_d45.teleios.bettergameplay.BetterGameplay;
import de.daniel_d45.teleios.passiveskills.PassiveSkills;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;


public class JoinListener implements Listener {

    /**
     * Method fires when a player joins and initiate the player's paths in the config file if the player
     * is not yet listed.
     *
     * @param event [PlayerJoinEvent]
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        try {

            Player player = event.getPlayer();

            // BetterGameplay segment
            BetterGameplay.initiateWarppouch(player.getName());

            // PassiveSkills segment
            PassiveSkills.initiatePlayerRecords(player.getName());

            MessageMaster.sendSuccessMessage("JoinListener", "onPlayerJoin(" + event + ")");
        } catch (Exception e) {
            MessageMaster.sendFailMessage("JoinListener", "onPlayerJoin(" + event + ")", e);
        }
    }

}
