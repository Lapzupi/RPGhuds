package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerCustomHudWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.core.settings.HudSettings;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class Hud<T extends HudSettings> {
    public final T hudSettings;
    boolean hidden;
    public final PlayerHudsHolderWrapper holder;
    protected final PlayerCustomHudWrapper customHudWrapper;
    private final int initialXOffset;

    protected final List<FontImageWrapper> imgsBuffer;

    protected Hud(PlayerHudsHolderWrapper holder, @NotNull T settings) {
        this.imgsBuffer = new ArrayList<>();
        this.holder = holder;
        this.customHudWrapper = settings.newInstanceByPlayer(holder);
        this.hudSettings = settings;
        this.initialXOffset = settings.initialOffsetX;
        this.customHudWrapper.setOffsetX(initialXOffset);
    }

    public abstract RenderAction refreshRender(boolean force);

    public abstract RenderAction refreshRender();

    public abstract void deleteRender();

    public void hide() {
        hide(true);
    }

    public void show() {
        hide(false);
    }

    public void hide(boolean hide) {
        customHudWrapper.setVisible(!hide);
        refreshRender(); // Is this call needed?
    }

    public void toggle() {
        hide(!customHudWrapper.isVisible());
    }

    /**
     * Call this if:
     * - HUD is on the left part of the screen and has text on the left side of the HUD
     * - HUD is on the right part of the screen and has text on the right side of the HUD
     */
    public void adjustOffset() {
        adjustOffset(initialXOffset);
    }

    /**
     * Call this if:
     * - HUD is on the left part of the screen and has text on the left side of the HUD
     * - HUD is on the right part of the screen and has text on the right side of the HUD
     *
     * @param initialOffset initial X offset of the HUD.
     */
    public void adjustOffset(int initialOffset) {
        int offset = initialOffset;
        for (FontImageWrapper img : imgsBuffer)
            offset -= img.getWidth();
        customHudWrapper.setOffsetX(offset);
    }

}
