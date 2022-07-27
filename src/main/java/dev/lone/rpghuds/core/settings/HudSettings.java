package dev.lone.rpghuds.core.settings;

import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;

import java.util.Set;

public abstract class HudSettings {
    public final String namespacedId;
    public final int initialOffsetX;
    public final Set<String> worlds;

    protected HudSettings(String namespacedId, int initialOffsetX, Set<String> worlds) {
        this.namespacedId = namespacedId;
        this.initialOffsetX = initialOffsetX;
        this.worlds = worlds;
    }

    public PlayerCustomHudWrapper newInstanceByPlayer(PlayerHudsHolderWrapper holder) {
        return new PlayerCustomHudWrapper(holder, namespacedId);
    }
}
