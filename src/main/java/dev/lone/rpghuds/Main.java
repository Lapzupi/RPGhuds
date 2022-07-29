package dev.lone.rpghuds;

import co.aikar.commands.PaperCommandManager;
import dev.lone.rpghuds.core.commands.RPGCompassCommand;
import dev.lone.rpghuds.core.RPGHuds;
import dev.lone.rpghuds.core.commands.RPGHudsCommand;
import dev.lone.rpghuds.core.config.Settings;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.event.Listener;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.ConfigurateException;

/**
 * This is a proof of concept, feel free to modify it and help me to make it better.
 * <p>
 * NOTE: make sure your code is optimized, I don't care about clean code just to feel cool or edgy.
 * If it's heavier than the actual design please don't make any change.
 * Make sure your changes are actually optimized and perfectly usable in a large scale server.
 */
public final class Main extends JavaPlugin {
    private static Main instance;
    private Settings settings;
    private static RPGHuds rpgHuds;

    @Nullable
    public static Economy econ = null;

    public static Main inst() {
        return instance;
    }

    public Settings getSettings() {
        return settings;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Override
    public void onEnable() {
        instance = this;

        initVaultEconomy();
        initConfig();

        rpgHuds = new RPGHuds(this);

        PaperCommandManager paperCommandManager = new PaperCommandManager(this);
        paperCommandManager.registerCommand(new RPGCompassCommand(this));
        paperCommandManager.registerCommand(new RPGHudsCommand(this));
    }

    @Override
    public void onDisable() {
        rpgHuds.cleanup();
    }

    public void initConfig() {
        try {
            this.settings = new Settings(this);
            this.settings.saveDefaultConfig();
            this.settings.reloadConfig();
        } catch (ConfigurateException e) {

        }
    }

    private void initVaultEconomy() {
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp != null)
            econ = rsp.getProvider();
    }

    public void reloadPlugin() {
        rpgHuds.cleanup();
        initVaultEconomy();
        settings.reloadConfig();
        rpgHuds.initAllPlayers();
    }
}
