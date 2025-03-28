/*
 2020-2023
 Teleios by Daniel_D45 <https://github.com/DanielD45> is marked with CC0 1.0 Universal <http://creativecommons.org/publicdomain/zero/1.0>.
 Feel free to distribute, remix, adapt, and build upon the material in any medium or format, even for commercial purposes. Just respect the origin. :)
 */

package de.daniel_d45.teleios.core.main;

import de.daniel_d45.teleios.adminfeatures.*;
import de.daniel_d45.teleios.bettergameplay.*;
import de.daniel_d45.teleios.core.ConfigEditor;
import de.daniel_d45.teleios.core.JoinLst;
import de.daniel_d45.teleios.core.ManageteleiosCmdLst;
import de.daniel_d45.teleios.core.NoInteractionInventoryClickLst;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;


/**
 * The plugin's main class as Singleton.
 */
public class Teleios extends JavaPlugin {

    private static Teleios plugin;
    private static Server server;
    static String pluginPrefix;
    private static FileConfiguration config;

    @NonNull
    public static Teleios getPlugin() {
        return plugin;
    }

    public static Server getServerObject() {
        return server;
    }

    public static FileConfiguration getConfigObject() {
        return config;
    }

    /**
     * Fires when the plugin is started. Is used as a setup method.
     * Plugin-added commands and EventListeners have to be activated here. <p>
     * - To register a command use: getCommand("[CommandName]").setExecutor([CommandClassObject]()); <p>
     * - To register an EventListener use: [PluginManagerObject].registerEvents(new [ListenerClassName](), this);
     */
    @Override
    public void onEnable() {

        // Variable instantiation
        plugin = this;
        server = plugin.getServer();
        pluginPrefix = "§5[Teleios Plugin]§r ";
        config = plugin.getConfig();
        PluginManager pluginManager = Bukkit.getPluginManager();
        // Variables for multi-use
        MuteCmdLst muteCmdLst = new MuteCmdLst();
        JoinmessageCmdLst joinmessageCmdLst = new JoinmessageCmdLst();
        ManageteleiosCmdLst manageteleiosCmdLst = new ManageteleiosCmdLst();
        // TODO: LootChestCmd lootChestCmdLst = new LootChestCmd();

        // Config setup
        ConfigEditor.setupConfig();

        // Command instantiation

        // AdminFeatures commands
        Objects.requireNonNull(getCommand("chatclear")).setExecutor(new ChatclearCmd());
        Objects.requireNonNull(getCommand("damage")).setExecutor(new DamageCmd());
        Objects.requireNonNull(getCommand("gma")).setExecutor(new GmaCmd());
        Objects.requireNonNull(getCommand("gmc")).setExecutor(new GmcCmd());
        Objects.requireNonNull(getCommand("gms")).setExecutor(new GmsCmd());
        Objects.requireNonNull(getCommand("gmsp")).setExecutor(new GmspCmd());
        Objects.requireNonNull(getCommand("heal")).setExecutor(new HealCmd());
        Objects.requireNonNull(getCommand("inventories")).setExecutor(new InventoriesCmd());
        Objects.requireNonNull(getCommand("joinmessage")).setExecutor(joinmessageCmdLst);
        // TODO: Objects.requireNonNull(getCommand("lootchest")).setExecutor(lootChestCmdLst);
        Objects.requireNonNull(getCommand("mute")).setExecutor(muteCmdLst);
        Objects.requireNonNull(getCommand("openinventory")).setExecutor(new OpeninventoryCmd());
        Objects.requireNonNull(getCommand("oplist")).setExecutor(new OplistCmd());
        Objects.requireNonNull(getCommand("tphere")).setExecutor(new TphereCmd());
        Objects.requireNonNull(getCommand("unmute")).setExecutor(new UnmuteCmd());
        Objects.requireNonNull(getCommand("warppoint")).setExecutor(new WarppointCmd());
        // BetterGameplay commands
        Objects.requireNonNull(getCommand("configureteleporter")).setExecutor(new ConfigureteleporterCmd());
        // TODO: Objects.requireNonNull(getCommand("enderchest")).setExecutor(new EnderchestCmd());
        Objects.requireNonNull(getCommand("setblocksperpearl")).setExecutor(new SetblocksperpearlCmd());
        Objects.requireNonNull(getCommand("warp")).setExecutor(new WarpCmd());
        Objects.requireNonNull(getCommand("warppouch")).setExecutor(new WarppouchCmd());
        // Core commands
        Objects.requireNonNull(getCommand("manageteleios")).setExecutor(manageteleiosCmdLst);
        // PassiveSkills commands
        //Objects.requireNonNull(getCommand("skills")).setExecutor(new SkillsCmd());


        // Event Listener instantiation

        // AdminFeatures Listeners
        pluginManager.registerEvents(joinmessageCmdLst, plugin);
        // TODO: pluginManager.registerEvents(lootChestCmdLst, plugin);
        pluginManager.registerEvents(muteCmdLst, plugin);
        // BetterGameplay Listeners
        pluginManager.registerEvents(new PlayerInteractWithTeleporterLst(), plugin);
        pluginManager.registerEvents(new TeleporterPlaceLst(), plugin);
        // Core Listeners
        pluginManager.registerEvents(new JoinLst(), plugin);
        pluginManager.registerEvents(manageteleiosCmdLst, plugin);
        pluginManager.registerEvents(new NoInteractionInventoryClickLst(), plugin);
        // PassiveSkills Listeners
        //pluginManager.registerEvents(new BlockBreakListenerPS(), this);
        //pluginManager.registerEvents(new BlockPlaceListenerPS(), this);


        // Prints the message to the console
        Teleios.getServerObject().getConsoleSender().sendMessage(pluginPrefix + "§bPlugin enabled§r");

        // PROGRAM TEST ROOM
        //FileConfiguration testConfig = plugin.getConfig();
        //testConfig.load("teleios");

        //RecipeManager.registerTestRecipes();
        // END OF PROGRAM TEST ROOM
    }

    @Override
    public void onDisable() {
        // Prints the message to the console
        Teleios.getServerObject().getConsoleSender().sendMessage(pluginPrefix + "§3Plugin disabled§r");
    }

}
