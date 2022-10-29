/*
 Copyright (c) 2020-2022 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.main;

import de.daniel_d45.teleios.adminfeatures.commands.*;
import de.daniel_d45.teleios.bettergameplay.commands.*;
import de.daniel_d45.teleios.bettergameplay.listeners.PlayerInteractWithTeleporterListener;
import de.daniel_d45.teleios.bettergameplay.listeners.TeleporterPlaceListener;
import de.daniel_d45.teleios.core.commands.ManageteleiosCommandListener;
import de.daniel_d45.teleios.core.commands.SetDebugLevelCommand;
import de.daniel_d45.teleios.core.listeners.ArtificialInventoryClickListener;
import de.daniel_d45.teleios.core.listeners.JoinListener;
import de.daniel_d45.teleios.core.program.ConfigEditor;
import de.daniel_d45.teleios.core.program.MessageMaster;
import de.daniel_d45.teleios.passiveskills.commands.SkillsCommand;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


/**
 * The plugins main class as Singleton. Extends org.bukkit.plugin.java.JavaPlugin.
 *
 * @author Daniel_D45
 */
public class Teleios extends JavaPlugin {

    private static Teleios plugin;
    private static Server server;
    private static int standardDebugLevel;

    /**
     * Getter for the plugin variable of the Main class.
     *
     * @return [Main] The plugin
     */
    public static Teleios getPlugin() {
        return plugin;
    }

    public static Server getServerObject() {
        return server;
    }

    public static int getDebugLevel() {
        try {
            return Integer.parseInt(ConfigEditor.get("DebugLevel").toString());
        } catch (Exception e) {
            return 1;
        }
    }

    public static int getStandardDebugLevel() {
        return standardDebugLevel;
    }

    /**
     * - Method fires when the plugin is started
     * - Is used as a setup method
     * - Plugin-added commands and EventListeners have to be activated here
     * - To register a command use: getCommand("[CommandName]").setExecutor(new [CommandClassName]());
     * - To register an EventListener use: [PluginManagerObject].registerEvents(new [ListenerClassName](), this);
     */
    @Override
    public void onEnable() {
        try {

            // Instantiates the variables of this class
            plugin = this;
            server = plugin.getServer();
            PluginManager pM = Bukkit.getPluginManager();
            standardDebugLevel = 1;

            ConfigEditor.setupConfig();

            // Variables for multi-use
            MuteCommandListener muteCommandListener = new MuteCommandListener();
            JoinmessageCommandListener joinmessageCommandListener = new JoinmessageCommandListener();
            ManageteleiosCommandListener manageteleiosCommandListener = new ManageteleiosCommandListener();

            // COMMAND REGISTRATION
            try {

                // AdminFeatures commands
                getCommand("chatclear").setExecutor(new ChatclearCommand());
                //getCommand("countdown").setExecutor(new ChatclearCommand());
                getCommand("damage").setExecutor(new DamageCommand());
                getCommand("gma").setExecutor(new GmaCommand());
                getCommand("gmc").setExecutor(new GmcCommand());
                getCommand("gms").setExecutor(new GmsCommand());
                getCommand("gmsp").setExecutor(new GmspCommand());
                getCommand("heal").setExecutor(new HealCommand());
                getCommand("inventories").setExecutor(new InventoriesCommand());
                getCommand("joinmessage").setExecutor(joinmessageCommandListener);
                getCommand("mute").setExecutor(muteCommandListener);
                getCommand("openinventory").setExecutor(new OpeninventoryCommand());
                getCommand("tphere").setExecutor(new TphereCommand());
                getCommand("unmute").setExecutor(new UnmuteCommand());

                // BetterGameplay commands
                getCommand("configureteleporter").setExecutor(new ConfigureteleporterCommand());
                getCommand("enderchest").setExecutor(new EnderchestCommand());
                getCommand("setblocksperpearl").setExecutor(new SetblocksperpearlCommand());
                getCommand("warp").setExecutor(new WarpCommand());
                getCommand("warppoint").setExecutor(new WarppointCommand());
                getCommand("warppouch").setExecutor(new WarppouchCommand());

                // Core commands
                getCommand("manageteleios").setExecutor(manageteleiosCommandListener);
                getCommand("setdebuglevel").setExecutor(new SetDebugLevelCommand());

                // PassiveSkills commands
                getCommand("skills").setExecutor(new SkillsCommand());

            } catch (Exception e) {
                MessageMaster.sendFailMessage("Main", "onEnable(): Command setup", e);
            }

            // EVENT LISTENER REGISTRATION
            try {

                // Core Listeners
                pM.registerEvents(new ArtificialInventoryClickListener(), this);
                pM.registerEvents(joinmessageCommandListener, this);
                pM.registerEvents(manageteleiosCommandListener, this);
                pM.registerEvents(new JoinListener(), this);

                // AdvancedCommands Listeners
                pM.registerEvents(muteCommandListener, this);
                pM.registerEvents(new PlayerInteractWithTeleporterListener(), this);
                pM.registerEvents(new TeleporterPlaceListener(), this);

                // PassiveSkills Listeners
                //pM.registerEvents(new BlockBreakListenerPS(), this);
                //pM.registerEvents(new BlockPlaceListenerPS(), this);

            } catch (Exception e) {
                MessageMaster.sendFailMessage("Main", "onEnable(): Event listeners setup", e);
            }

            MessageMaster.sendEnableMessage();
            // Program test room

            // End of program test room
        } catch (Exception e) {
            System.out.println("§4An error occured while starting the plugin!");
            MessageMaster.sendFailMessage("Main", "onEnable()", e);
        }
    }

    @Override
    public void onDisable() {
        MessageMaster.sendDisableMessage();
    }

}
