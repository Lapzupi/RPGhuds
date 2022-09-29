package dev.lone.rpghuds.core.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.RPGHuds;
import dev.lone.rpghuds.core.config.HudConfig;
import dev.lone.rpghuds.core.data.Hud;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@CommandAlias("rpghuds")
public class RPGHudsCommand extends BaseCommand {
    private final Main plugin;

    private static final String MUST_BE_PLAYER = "You must be player to execute this command without specifying a target.";

    public RPGHudsCommand(final Main plugin) {
        this.plugin = plugin;
    }

    @Subcommand("list")
    @CommandPermission("rpghuds.list")
    @Description("List all potential huds.")
    public void onList(final @NotNull CommandSender sender) {
        sender.sendMessage(this.plugin.getSettings().getHudList().stream().map(HudConfig::getNamespaceId).toList().toArray(new String[]{}));
    }

    @Subcommand("reload")
    @CommandPermission("rpghuds.reload")
    public void onReload(final CommandSender sender) {
        Main.inst().reloadPlugin();
        Main.inst().getLogger().info(ChatColor.GREEN + "Reloaded");
        if (sender instanceof Player)
            sender.sendMessage(ChatColor.GREEN + "Reloaded");
    }

    @Subcommand("toggle")
    @CommandCompletion("@huds @players")
    @Description("Toggles the state of a given hud.")
    public void onToggle(final CommandSender sender, final String hudId, @Optional final OnlinePlayer target) {
        if (target == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(MUST_BE_PLAYER);
                return;
            }


            Hud<?> playerHud = RPGHuds.inst().getPlayerHud(player, hudId);
            if (playerHud == null) {
                sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
                return;
            }

            playerHud.toggle();
            return;
        }

        if (!sender.hasPermission("rpghuds.toggle.others")) {
            sender.sendMessage(ChatColor.RED + "No permission rpghuds.toggle.others");
            return;
        }
        Hud<?> playerHud = RPGHuds.inst().getPlayerHud(target.getPlayer(), hudId);
        if (playerHud == null) {
            sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
            return;
        }

        playerHud.toggle();
    }

    @Subcommand("show")
    @CommandCompletion("@huds @players")
    @CommandPermission("rpghuds.show")
    public void onShow(final CommandSender sender, final String hudId, @Optional final OnlinePlayer target) {
        if (target == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(MUST_BE_PLAYER);
                return;
            }


            Hud<?> playerHud = RPGHuds.inst().getPlayerHud(player, hudId);
            if (playerHud == null) {
                sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
                return;
            }

            playerHud.show();
            return;
        }

        if (!sender.hasPermission("rpghuds.show.others")) {
            sender.sendMessage(ChatColor.RED + "No permission rpghuds.show.others");
            return;
        }
        Hud<?> playerHud = RPGHuds.inst().getPlayerHud(target.getPlayer(), hudId);
        if (playerHud == null) {
            sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
            return;
        }

        playerHud.show();

    }

    @Subcommand("hide")
    @CommandPermission("rpghuds.hide")
    public void onHide(final CommandSender sender, final String hudId, @Optional final OnlinePlayer target) {
        if (target == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage(MUST_BE_PLAYER);
                return;
            }


            Hud<?> playerHud = RPGHuds.inst().getPlayerHud(player, hudId);
            if (playerHud == null) {
                sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
                return;
            }

            playerHud.hide();
            return;
        }

        if (!sender.hasPermission("rpghuds.hide.others")) {
            sender.sendMessage(ChatColor.RED + "No permission rpghuds.hide.others");
            return;
        }

        Hud<?> playerHud = RPGHuds.inst().getPlayerHud(target.getPlayer(), hudId);
        if (playerHud == null) {
            sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
            return;
        }

        playerHud.hide();
    }
}
