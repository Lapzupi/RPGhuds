package dev.lone.rpghuds.utils;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredListener;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class Utils {
    public static FontImageWrapper getFontImage(String id) throws NullPointerException {
        FontImageWrapper fontImageWrapper = new FontImageWrapper(id);
        if (!fontImageWrapper.exists())
            throw new NullPointerException("Can't find font_image: " + id);
        return fontImageWrapper;
    }
    
    @Contract(pure = true)
    public static @NotNull String color(@NotNull String msg) {
        return msg.replace("&", "§");
    }
    
    public static void registerEventOnce(Listener li, Plugin plugin) {
        for (RegisteredListener listener : HandlerList.getRegisteredListeners(plugin))
            if (li.getClass().isInstance(listener.getListener()))
                return;
        Bukkit.getPluginManager().registerEvents(li, plugin);
    }
}
