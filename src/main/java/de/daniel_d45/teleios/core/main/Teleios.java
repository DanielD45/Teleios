/*
 Copyright (c) 2020-2023 Daniel_D45 <https://github.com/DanielD45>
 Teleios by Daniel_D45 is licensed under the Attribution-NonCommercial 4.0 International license <https://creativecommons.org/licenses/by-nc/4.0/>
 */

package de.daniel_d45.teleios.core.main;

import de.daniel_d45.teleios.adminfeatures.*;
import de.daniel_d45.teleios.bettergameplay.*;
import de.daniel_d45.teleios.core.*;
import de.daniel_d45.teleios.passiveskills.SkillsCmd;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;


/**
 * The plugins main class as Singleton. Extends org.bukkit.plugin.java.JavaPlugin.
 *
 * @author Daniel_D45
 */
public class Teleios extends JavaPlugin {

    private static Teleios plugin;
    private static Server server;
    private static int standardDebugLevel;
    private static FileConfiguration config;

    /**
     * Getter for the plugin variable of the Main class.
     *
     * @return [Main] The plugin
     */
    @NonNull
    public static Teleios getPlugin() {
        return plugin;
    }

    public static Server getServerObject() {
        return server;
    }

    public static int getStandardDebugLevel() {
        return standardDebugLevel;
    }

    public static FileConfiguration getConfigObject() {
        return config;
    }

    /**
     * This method fires when the plugin is started. It is used as a setup method. Plugin-added commands and EventListeners have to be activated here.
     * <p>
     * - To register a command use: getCommand("[CommandName]").setExecutor([CommandClassObject]());
     * <p>
     * - To register an EventListener use: [PluginManagerObject].registerEvents(new [ListenerClassName](), this);
     */
    @Override
    public void onEnable() {
        try {

            // Instantiates the variables of this class
            plugin = this;
            server = plugin.getServer();
            standardDebugLevel = 3;
            config = plugin.getConfig();
            PluginManager pluginManager = Bukkit.getPluginManager();

            // Config setup
            ConfigEditor.setupConfig();

            // Variables for multi-use
            MuteCmdLst muteCmdLst = new MuteCmdLst();
            JoinmessageCmdLst joinmessageCmdLst = new JoinmessageCmdLst();
            ManageteleiosCmdLst manageteleiosCmdLst = new ManageteleiosCmdLst();
            MakePersonalLootChestCmdLst makePersonalLootChestCmdLst = new MakePersonalLootChestCmdLst();

            // Command instantiation
            try {
                // Objects.requireNonNull() is only there to suppress IDE warnings

                // AdminFeatures commands
                Objects.requireNonNull(getCommand("chatclear")).setExecutor(new ChatclearCmd());
                //getCommand("chatclear").unregister();
                //getCommand("countdown").setExecutor(new ChatclearCommand());
                Objects.requireNonNull(getCommand("damage")).setExecutor(new DamageCmd());
                Objects.requireNonNull(getCommand("gma")).setExecutor(new GmaCmd());
                Objects.requireNonNull(getCommand("gmc")).setExecutor(new GmcCmd());
                Objects.requireNonNull(getCommand("gms")).setExecutor(new GmsCmd());
                Objects.requireNonNull(getCommand("gmsp")).setExecutor(new GmspCmd());
                Objects.requireNonNull(getCommand("heal")).setExecutor(new HealCmd());
                Objects.requireNonNull(getCommand("inventories")).setExecutor(new InventoriesCmd());
                Objects.requireNonNull(getCommand("joinmessage")).setExecutor(joinmessageCmdLst);
                Objects.requireNonNull(getCommand("makepersonallootchest")).setExecutor(makePersonalLootChestCmdLst);
                Objects.requireNonNull(getCommand("mute")).setExecutor(muteCmdLst);
                Objects.requireNonNull(getCommand("openinventory")).setExecutor(new OpeninventoryCmd());
                Objects.requireNonNull(getCommand("tphere")).setExecutor(new TphereCmd());
                Objects.requireNonNull(getCommand("unmute")).setExecutor(new UnmuteCmd());

                // BetterGameplay commands
                Objects.requireNonNull(getCommand("configureteleporter")).setExecutor(new ConfigureteleporterCmd());
                Objects.requireNonNull(getCommand("enderchest")).setExecutor(new EnderchestCmd());
                Objects.requireNonNull(getCommand("setblocksperpearl")).setExecutor(new SetblocksperpearlCmd());
                Objects.requireNonNull(getCommand("warp")).setExecutor(new WarpCmd());
                Objects.requireNonNull(getCommand("warppoint")).setExecutor(new WarppointCmd());
                Objects.requireNonNull(getCommand("warppouch")).setExecutor(new WarppouchCmd());

                // Core commands
                Objects.requireNonNull(getCommand("manageteleios")).setExecutor(manageteleiosCmdLst);
                Objects.requireNonNull(getCommand("setdebuglevel")).setExecutor(new SetDebugLevelCmd());

                // PassiveSkills commands
                Objects.requireNonNull(getCommand("skills")).setExecutor(new SkillsCmd());

            } catch (Exception e) {
                MessageMaster.sendFailMessage("Teleios", "onEnable(): Command instantiation", e);
            }

            // Event Listener instantiation
            try {

                // TODO: reorganise
                // Core Listeners
                pluginManager.registerEvents(new ArtificialInventoryClickLst(), plugin);
                pluginManager.registerEvents(manageteleiosCmdLst, plugin);
                pluginManager.registerEvents(new JoinListener(), plugin);

                // AdminFeatures Listeners
                pluginManager.registerEvents(joinmessageCmdLst, plugin);
                pluginManager.registerEvents(makePersonalLootChestCmdLst, plugin);

                // BetterGameplay Listeners
                pluginManager.registerEvents(muteCmdLst, plugin);
                pluginManager.registerEvents(new PlayerInteractWithTeleporterLst(), plugin);
                pluginManager.registerEvents(new TeleporterPlaceLst(), plugin);

                // PassiveSkills Listeners
                //pM.registerEvents(new BlockBreakListenerPS(), this);
                //pM.registerEvents(new BlockPlaceListenerPS(), this);

            } catch (Exception e) {
                MessageMaster.sendFailMessage("Teleios", "onEnable(): Event listeners instantiation", e);
            }

            MessageMaster.sendEnableMessage();

            // Program test room
            //FileConfiguration testConfig = plugin.getConfig();
            //testConfig.load("teleios");
            // End of program test room
        } catch (Exception e) {
            System.out.println("§cAn error occured while starting the plugin!");
            MessageMaster.sendFailMessage("Teleios", "onEnable()", e);
        }
    }

    @Override
    public void onDisable() {
        MessageMaster.sendDisableMessage();
    }

}
