package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerData {
    private final PlayerHudsHolderWrapper holder;
    public final Map<String, Hud<?>> allHudsByNamespacedId = new HashMap<>();
    public final List<Hud<?>> allHuds = new ArrayList<>();
    private final List<Hud<?>> hudsHighFreq = new ArrayList<>();

    public PlayerData(PlayerHudsHolderWrapper holder) {
        this.holder = holder;
    }

    public void registerHud(Hud<?> hud, boolean highFrequency) {
        if (highFrequency)
            hudsHighFreq.add(hud);
        allHuds.add(hud);
        allHudsByNamespacedId.put(hud.hudSettings.namespacedId, hud);
    }

    public PlayerHudsHolderWrapper getHolder() {
        return holder;
    }

    public void refreshAllHuds() {
        refreshHuds(allHuds);
    }

    public void refreshHighFrequency() {
        refreshHuds(hudsHighFreq);
    }

    private void refreshHuds(List<Hud<?>> huds) {
        boolean changedRenderAny = false;
        for (Hud<?> hud : huds) {
            if (hud.refreshRender() == Hud.RenderAction.SEND_REFRESH)
                changedRenderAny = true;
        }

        if (changedRenderAny)
            sendPacket(holder, true);
    }

    public static void sendPacket(PlayerHudsHolderWrapper holder, boolean recalculateOffsets) {
        if (recalculateOffsets)
            holder.recalculateOffsets();
        holder.sendUpdate();
    }

    public void cleanup() {
        for (Hud<?> hud : allHuds)
            hud.deleteRender();

        allHuds.clear();
        hudsHighFreq.clear();
        allHudsByNamespacedId.clear();
    }
}