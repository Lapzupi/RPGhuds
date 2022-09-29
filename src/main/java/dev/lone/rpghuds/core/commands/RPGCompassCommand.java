package dev.lone.rpghuds.core.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.core.RPGHuds;
import dev.lone.rpghuds.core.data.CompassHud;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

/**
 * @author sarhatabaot
 */
@CommandAlias("rpgcompass")
public class RPGCompassCommand extends BaseCommand {
    private final Main plugin;
    private static final String COMPASS_ID = "rpghuds:compass";

    public RPGCompassCommand(final Main plugin) {
        this.plugin = plugin;
    }

    @Subcommand("set")
    @CommandPermission("rpghuds.compass.set")
    @CommandCompletion("@players @worlds")
    public void onSet(final CommandSender sender, final OnlinePlayer player, final World world, final int x, final int y, final int z) {
        Location location = new Location(world, x, y, z);

        CompassHud hud = (CompassHud) RPGHuds.inst().getPlayerHud(player.getPlayer(), COMPASS_ID);
        if (hud == null) {
            sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
            return;
        }

        hud.setDestination(new CompassHud.Destination(location));
        sender.sendMessage(plugin.getSettings().getMsgDestinationSet());
    }

    @Subcommand("remove")
    @CommandPermission("rpghuds.compass.remove")
    public void onRemove(final CommandSender sender, final OnlinePlayer player) {
        CompassHud hud = (CompassHud) RPGHuds.inst().getPlayerHud(player.getPlayer(), COMPASS_ID);
        if (hud == null) {
            sender.sendMessage(plugin.getSettings().getMsgHudNotFound());
            return;
        }
        hud.removeDestination();
        sender.sendMessage(plugin.getSettings().getMsgDestinationRemoved());
    }
}
