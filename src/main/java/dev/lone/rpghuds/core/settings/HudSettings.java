package dev.lone.rpghuds.core.settings;

import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;

import java.util.Set;

public abstract class HudSettings {
    public final String namespacedID;
    public final int initialOffsetX;
    public final Set<String> worlds;

    HudSettings(String namespacedID, int initialOffsetX, Set<String> worlds) {
        this.namespacedID = namespacedID;
        this.initialOffsetX = initialOffsetX;
        this.worlds = worlds;
    }

    public PlayerCustomHudWrapper newInstanceByPlayer(PlayerHudsHolderWrapper holder) {
        return new PlayerCustomHudWrapper(holder, namespacedID);
    }
}
