package dev.lone.rpghuds.core;

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
    private final String compassId = "rpghuds:compass";

    @Subcommand("set")
    @CommandPermission("rpghuds.compass.set")
    @CommandCompletion("@players @worlds")
    public void onSet(final CommandSender sender, final OnlinePlayer player, final World world, final int x, final int y, final int z) {
        Location location = new Location(world, x, y, z);

        CompassHud hud = (CompassHud) RPGHuds.inst().getPlayerHud(player.getPlayer(), compassId);
        if (hud == null) {
            sender.sendMessage(Main.settings.msgHudNotFound);
            return;
        }

        hud.setDestination(new CompassHud.Destination(location));
        sender.sendMessage(Main.settings.msgDestinationSet);
    }

    @Subcommand("remove")
    @CommandPermission("rpghuds.compass.remove")
    public void onRemove(final CommandSender sender, final OnlinePlayer player) {
        CompassHud hud = (CompassHud) RPGHuds.inst().getPlayerHud(player.getPlayer(), compassId);
        if (hud == null) {
            sender.sendMessage(Main.settings.msgHudNotFound);
            return;
        }
        hud.removeDestination();
        sender.sendMessage(Main.settings.msgDestinationRemoved);
    }
}
