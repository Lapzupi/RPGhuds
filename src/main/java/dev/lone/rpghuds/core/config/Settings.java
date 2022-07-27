package dev.lone.rpghuds.core.config;

import com.github.sarhatabaot.kraken.core.config.Transformation;
import com.github.sarhatabaot.kraken.core.config.YamlConfigurateFile;
import dev.lone.rpghuds.Main;
import dev.lone.rpghuds.utils.Utilz;
import org.bukkit.configuration.file.FileConfiguration;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.configurate.CommentedConfigurationNode;
import org.spongepowered.configurate.ConfigurateException;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Settings extends YamlConfigurateFile<Main> {
    private long refreshIntervalTicks;
    private long refreshHighFrequencyIntervalTicks;
    private boolean debug;
    private String msgHudNotFound;
    private String msgWrongUsage;
    private String msgDestinationSet;
    private String msgDestinationRemoved;

    private List<HudConfig> hudList;

    public Settings(@NotNull final Main plugin) throws ConfigurateException {
        super(plugin, "", "config.yml", "");
    }

    @Override
    protected void initValues() throws ConfigurateException {
        this.refreshIntervalTicks = rootNode.node("huds_refresh_interval_ticks").getLong(30);
        this.refreshHighFrequencyIntervalTicks = rootNode.node("huds_high_frequency_refresh_interval_ticks").getLong(2);

        this.hudList = rootNode.node("huds").getList(HudConfig.class);

        this.debug = rootNode.node("log").node("debug").getBoolean(false);
        CommentedConfigurationNode langNode = rootNode.node("lang");
        this.msgHudNotFound = Utilz.color(langNode.node("hud_not_found").getString("&6Hud not found!"));
        this.msgWrongUsage = Utilz.color(langNode.node("wrong_usage").getString("&cWrong command usage."));
        this.msgDestinationSet = Utilz.color(langNode.node("destination_set").getString("&aDestination set."));
        this.msgDestinationRemoved = Utilz.color(langNode.node("destination_removed").getString("&7Destination removed."));
    }

    @Override
    protected void builderOptions() {
        loaderBuilder.defaultOptions(options -> options.serializers(builder ->
                builder.registerExact(HudConfig.class, new HudConfigTypeSerializer())));
        //nothing
    }

    @Override
    protected Transformation getTransformation() {
        return null;
    }

    public long getRefreshIntervalTicks() {
        return refreshIntervalTicks;
    }


    public long getRefreshHighFrequencyIntervalTicks() {
        return refreshHighFrequencyIntervalTicks;
    }


    public boolean isDebug() {
        return debug;
    }

    public String getMsgHudNotFound() {
        return msgHudNotFound;
    }


    public String getMsgWrongUsage() {
        return msgWrongUsage;
    }


    public String getMsgDestinationSet() {
        return msgDestinationSet;
    }

    public String getMsgDestinationRemoved() {
        return msgDestinationRemoved;
    }


    public static class HudConfigTypeSerializer implements TypeSerializer<HudConfig> {
        private static final String TYPE = "type";
        private static final String ENABLED = "enabled";
        private static final String OFFSET = "offset";
        private static final String PAPI_PLACEHOLDER = "papi_placeholder";
        private static final String NAMESPACED_ID = "namespaced_id";

        @Override
        public HudConfig deserialize(final Type type, final ConfigurationNode node) throws SerializationException {
            final HudType hudType = node.node(TYPE).get(HudType.class);
            final boolean enabled = node.node(ENABLED).getBoolean(true);
            final int offset = node.node(OFFSET).getInt();
            final String papiPlaceholder = node.node(PAPI_PLACEHOLDER).getString();

            final ConfigurationNode settings = node.node("settings");
            final String namespacedId = settings.node(NAMESPACED_ID).getString();
            final List<String> worlds = node.node("worlds").getList(String.class);
            if (hudType == HudType.MONEY) {
                final String icon = settings.node("icon").getString();
                return new MoneyHudConfig(hudType, enabled, offset, papiPlaceholder, worlds, namespacedId, icon);
            }

            final String prefix= settings.node("prefix").getString();
            return new CompassHudConfig(hudType, enabled, offset, papiPlaceholder, worlds, namespacedId, prefix);

        }

        @Override
        public void serialize(final Type type, @Nullable final HudConfig obj, final ConfigurationNode node) throws SerializationException {
            //we don't use this
        }
    }

    public List<HudConfig> getHudList() {
        return hudList;
    }
}
