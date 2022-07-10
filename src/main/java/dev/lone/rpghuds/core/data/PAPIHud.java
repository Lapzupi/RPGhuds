package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.core.settings.HudSettings;

public abstract class PAPIHud<T extends HudSettings> extends Hud<T> {
    public final String placeholder;

    protected PAPIHud(String placeholder, PlayerHudsHolderWrapper holder, T settings) {
        super(holder, settings);
        this.placeholder = placeholder;
    }
}
