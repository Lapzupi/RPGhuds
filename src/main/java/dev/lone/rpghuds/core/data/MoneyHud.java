package dev.lone.rpghuds.core.data;

import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import dev.lone.itemsadder.api.FontImages.PlayerHudsHolderWrapper;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.settings.MoneySettings;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class MoneyHud extends PAPIHud<MoneySettings> {
    private static boolean HAS_CHECKED_PLACEHOLDER = false;

    private final Player player;

    private double prevBalance;
    private String prevAmount;

    @Nullable
    private BukkitTask arrowRemoveSchedule;
    @Nullable
    private FontImageWrapper currentArrow;

    public MoneyHud(String placeholder, PlayerHudsHolderWrapper holder, MoneySettings settings) throws NullPointerException {
        super(placeholder, holder, settings);
        this.player = holder.getPlayer();

        this.prevBalance = Main.econ.getBalance(player);

        customHudWrapper.setVisible(true);
    }

    @Override
    public RenderAction refreshRender() {
        return refreshRender(false);
    }

    private FontImageWrapper getArrowState(double balance) {
        if (balance > prevBalance)
            return hudSettings.getChar_arrow_up();
        return hudSettings.getChar_arrow_down();
    }

    @Override
    public RenderAction refreshRender(boolean forceRender) {
        if (hidden)
            return RenderAction.HIDDEN;

        if(player.getRemainingAir() < player.getMaximumAir()) {
            customHudWrapper.setVisible(false);
            return RenderAction.HIDDEN;
        } else {
            customHudWrapper.setVisible(true);
        }

        if (!hudSettings.worlds.contains(player.getWorld().getName())) {
            customHudWrapper.setVisible(false); //I think this will cause problems
            return RenderAction.HIDDEN;
        }

        if (isVault()) {
            double balance = Main.econ.getBalance(player);
            if (balance != prevBalance) {
                currentArrow = getArrowState(balance);
                prevBalance = balance;

                if (arrowRemoveSchedule != null)
                    arrowRemoveSchedule.cancel();
                arrowRemoveSchedule = Bukkit.getScheduler().runTaskLaterAsynchronously(Main.inst(), () -> {
                    currentArrow = null;
                    arrowRemoveSchedule.cancel();
                    arrowRemoveSchedule = null;
                    refreshRender(true);
                }, 20L * 3);
            }
        }


        String amount = placeholderHack(PlaceholderAPI.setPlaceholders(holder.getPlayer(), placeholder));

        if (isSameAsBefore(amount,forceRender))
            return RenderAction.SAME_AS_BEFORE;

        //TODO: Shit, recode this. PAPI doesn't allow me to preemptively check if a placeholder is working or not.
        if (!HAS_CHECKED_PLACEHOLDER && amount.equals(placeholder)) {
            Main.inst().getLogger().severe(() -> (
                    "%sFailed to replace PAPI placeholder for player %s. '%s'probably doesn't exists. " +
                            "Check RPGhuds/config.yml file and check if you have the correct economy plugin installed."
            ).formatted(ChatColor.RED, player.getName(), placeholder));
            prevAmount = amount;
            HAS_CHECKED_PLACEHOLDER = true;
            return RenderAction.HIDDEN;
        }

        imgsBuffer.clear();

        if (currentArrow != null)
            imgsBuffer.add(currentArrow);

        hudSettings.appendAmountToImages(amount, imgsBuffer);
        imgsBuffer.add(hudSettings.getIcon());

        customHudWrapper.setFontImages(imgsBuffer);

        adjustOffset();

        prevAmount = amount;

        return RenderAction.SEND_REFRESH;
    }

    private boolean isVault() {
        return placeholder.contains("vault") && Main.econ != null;
    }

    private boolean isSameAsBefore(final String amount,final boolean forceRender) {
        return !forceRender && currentArrow == null && amount.equals(prevAmount);
    }


    //Prevents unknown char from placeholder, dirty
    private @NotNull String placeholderHack(final @NotNull String placeholder) {
        if (placeholder.contains("$")) {
            return placeholder.replace("$", "");
        }
        return placeholder;
    }

    @Override
    public void deleteRender() {
        customHudWrapper.clearFontImagesAndRefresh();

        if (arrowRemoveSchedule != null)
            arrowRemoveSchedule.cancel();
        arrowRemoveSchedule = null;
    }
}
