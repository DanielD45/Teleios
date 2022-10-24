/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.creatureevolution.listeners;

import de.daniel_d45.teleios.core.util.MessageMaster;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;


public class EntityDeathListenerCE implements Listener {

    // TODO: implement

    /**
     * Method fires when a living entity dies.
     *
     * @param event [EntityDeathEvent]
     */
    @EventHandler(priority = EventPriority.NORMAL)
    public boolean onEntityDeathCE(EntityDeathEvent event) {
        try {

            LivingEntity entity;
            Entity killer;

            // VARIABLE INSTANTIATION
            try {

                entity = event.getEntity();
                killer = entity.getKiller();

            } catch (Exception e) {
                MessageMaster.sendFailMessage("EntityDeathListener", "onEntityDeath(" + event + ")", e);
                return false;
            }

            // Checks for the entity type
            if (entity.getType() == EntityType.ZOMBIE) {

                MessageMaster.sendSuccessMessage("EntityDeathListener", "onEntityDeath(" + event + ")");
                return true;
            }
            else {

                MessageMaster.sendSkipMessage("EntityDeathListener", "Skipped method onEntityDeath(" + event + "), no access to the CoreProtect API.");
            }
            // After entity type check

            return true;
        } catch (Exception e) {
            MessageMaster.sendFailMessage("EntityDeathListener", "onEntityDeath(" + event + ")", e);
            return false;
        }

    }

}
