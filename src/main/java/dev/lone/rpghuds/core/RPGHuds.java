package dev.lone.rpghuds.core;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.config.CompassHudConfig;
import dev.lone.rpghuds.core.config.HudConfig;
import dev.lone.rpghuds.core.config.MoneyHudConfig;
import dev.lone.rpghuds.core.data.*;
import dev.lone.rpghuds.core.settings.MoneySettings;
import dev.lone.rpghuds.core.settings.old.CompassSettingsOld;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class RPGHuds {
    private static RPGHuds instance;

    static final String WARNING = "Please don't forget to regen your resourcepack using /iazip command.";

    private final Main plugin;
    private final HashMap<Player, PlayerData> datasByPlayer = new HashMap<>();
    private final List<PlayerData> datas = new ArrayList<>();
    private final List<BukkitTask> refreshTasks = new ArrayList<>();

    boolean needsIaZip;
    boolean notifyIazip;
    private boolean allPlayersInitialized;

    //TODO: recode this shit. Very dirty
    private final List<String> hudsNames = List.of("rpghuds:money", "rpghuds:compass");


    public RPGHuds(Main plugin) {
        instance = this;

        this.plugin = plugin;

        new EventsListener(plugin, this).registerListener();

        extractDefaultAssets();
    }

    public static RPGHuds inst() {
        return instance;
    }

    //TODO: recode this shit. Very dirty
    public List<String> getHudsNames() {
        return hudsNames;
    }

    @Nullable
    public Hud<?> getPlayerHud(Player player, String namespacedID) {
        PlayerData playerData = datasByPlayer.get(player);
        if (playerData == null)
            return null;
        return playerData.allHudsByNamespacedId.get(namespacedID);
    }

    public void initAllPlayers() {
        if (allPlayersInitialized) {
            plugin.getLogger().severe("Error: players already initialized! Be sure to first call RPGHuds#cleanup().");
            return;
        }
        try {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                initPlayer(player);
            }
            scheduleRefresh();
            allPlayersInitialized = true;
        } catch (NullPointerException e) {
            plugin.getLogger().warning(WARNING);
        }
    }

    void initPlayer(Player player) {

        PlayerData playerData = new PlayerData(new PlayerHudsHolderWrapper(player));
        for (HudConfig hudConfig : plugin.getSettings().getHudList()) {
            Hud<?> hud;

            try {
                hud = getHudFromConfig(playerData.getHolder(), hudConfig);
            } catch (NullPointerException e) {
                plugin.getLogger().warning(() -> "Could not load hud %s for player %s".formatted(hudConfig.getNamespaceId(), player.getName()));
                if (plugin.getSettings().isDebug()) {
                    plugin.getLogger().log(Level.WARNING, "NullPointerException while obtaining hud.", e);
                }
                continue;
            }

            if (hud instanceof MoneyHud) {
                playerData.registerHud(hud, false);
            } else if (hud instanceof CompassHud) {
                playerData.registerHud(hud, true);
            }
        }
        plugin.getLogger().info(() -> "Loaded huds: %s for %s".formatted(player.getUniqueId(), playerData.allHudsByNamespacedId.keySet().toString()));
        datasByPlayer.put(player, playerData);
        datas.add(playerData);

    }

    private Hud<?> getHudFromConfig(final PlayerHudsHolderWrapper holder, final HudConfig hudConfig) throws NullPointerException {
        if (hudConfig instanceof MoneyHudConfig moneyHudConfig) {
            return new MoneyHud(
                    moneyHudConfig.getPapiPlaceholder(),
                    holder,
                    new MoneySettings(moneyHudConfig.getNamespaceId(),
                            moneyHudConfig.getOffset(),
                            moneyHudConfig.getWorlds(),
                            moneyHudConfig.getIcon(),
                            plugin.getSettings().getMoneyType(), plugin.getSettings().getMoneyType()));
        }
        if (hudConfig instanceof CompassHudConfig compassHudConfig) {
            return new CompassHud(
                    holder,
                    new CompassSettingsOld(
                            "rpghuds:compass",
                            "rpghuds:hud_compass_",
                            compassHudConfig.getOffset(),
                            compassHudConfig.getWorlds()
                    ));
        }
        throw new NullPointerException();
    }

    //TODO: implement animated icons.
    // Warning: make sure to increment the refresh rate only when it's actually needed by the animation.
    // I don't want the plugin to become heavy just for a stupid animation.

    private void scheduleRefresh() {
        refreshTasks.add(Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (PlayerData data : datas)
                data.refreshAllHuds();
        }, plugin.getSettings().getRefreshIntervalTicks(), plugin.getSettings().getRefreshIntervalTicks()));

        refreshTasks.add(Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            for (PlayerData data : datas)
                data.refreshHighFrequency();
        }, plugin.getSettings().getRefreshHighFrequencyIntervalTicks(), plugin.getSettings().getRefreshHighFrequencyIntervalTicks()));
    }

    void unregisterAllPlayers() {
        for (BukkitTask task : refreshTasks)
            task.cancel();
        refreshTasks.clear();
        allPlayersInitialized = false;
    }

    public void cleanup() {
        unregisterAllPlayers();

        for (PlayerData data : datas)
            data.cleanup();

        datas.clear();
        datasByPlayer.clear();
    }

    private void extractDefaultAssets() {
        CodeSource src = Main.class.getProtectionDomain().getCodeSource();
        if (src != null) {
            File itemsadderRoot = new File(plugin.getDataFolder().getParent() + "/ItemsAdder");

            URL jar = src.getLocation();

            try (ZipInputStream zip = new ZipInputStream(jar.openStream())) {
                plugin.getLogger().info(ChatColor.AQUA + "Extracting assets...");
                while (true) {
                    ZipEntry e = zip.getNextEntry();
                    if (e == null)
                        break;
                    String name = e.getName();
                    if (!e.isDirectory() && name.startsWith("data/")) {
                        File dest = new File(itemsadderRoot, name);
                        if (!dest.exists()) {
                            FileUtils.copyInputStreamToFile(plugin.getResource(name), dest);
                            plugin.getLogger().info(() -> ChatColor.AQUA + "       - Extracted " + name);
                            needsIaZip = true;
                        }
                    }
                }
                plugin.getLogger().info(ChatColor.GREEN + "DONE extracting assets!");

            } catch (IOException e) {
                plugin.getLogger().severe("        ERROR EXTRACTING assets! StackTrace:");
                e.printStackTrace();
            }
        }

        notifyIazip = needsIaZip;
        if (needsIaZip)
            plugin.getLogger().warning(WARNING);
    }
}
