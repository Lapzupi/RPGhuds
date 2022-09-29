package dev.lone.rpghuds.core;

import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.utils.EventsUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

class EventsListener implements Listener {
    private final Main plugin;
    private final RPGHuds rpgHuds;
    private ItemsAdderLoadListener itemsAdderLoadListener;

    EventsListener(Main plugin, RPGHuds rpgHuds) {
        this.plugin = plugin;
        this.rpgHuds = rpgHuds;
        this.itemsAdderLoadListener = new ItemsAdderLoadListener(plugin, rpgHuds);
    }

    public void registerListener() {
        EventsUtil.registerEventOnce(this, plugin);
        itemsAdderLoadListener.registerListener();

    }

    @EventHandler
    private void onItemsAdderLoad(@NotNull PluginEnableEvent event) {
        if (!event.getPlugin().getName().equals("ItemsAdder"))
            return;

        if (plugin.getSettings().isDebug())
            plugin.getLogger().log(Level.INFO, "RPGhuds - detected ItemsAdder loading...");

        if (this.itemsAdderLoadListener != null)
            this.itemsAdderLoadListener = new ItemsAdderLoadListener(plugin, rpgHuds);

        rpgHuds.initAllPlayers();
    }

    @EventHandler
    private void onItemsAdderUnload(@NotNull PluginDisableEvent e) {
        if (!e.getPlugin().getName().equals("ItemsAdder"))
            return;

        if (plugin.getSettings().isDebug())
            plugin.getLogger().log(Level.INFO, "RPGhuds - detected ItemsAdder unload...");

        EventsUtil.unregisterEvent(itemsAdderLoadListener);
        itemsAdderLoadListener = null;

        rpgHuds.cleanup();
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        if (rpgHuds.notifyIazip && event.getPlayer().isOp()) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> event.getPlayer().sendMessage(ChatColor.RED + RPGHuds.WARNING), 60L);
            rpgHuds.notifyIazip = false;
        }

        if (!rpgHuds.needsIaZip) {
            rpgHuds.initPlayer(event.getPlayer());
        }
    }
}
