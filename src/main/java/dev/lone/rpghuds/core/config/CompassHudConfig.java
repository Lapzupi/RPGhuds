package dev.lone.rpghuds.core.config;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class CompassHudConfig extends HudConfig{
    private String prefix;

    public CompassHudConfig(final HudType type, final boolean enabled, final int offset, final String papiPlaceholder, final List<String> worlds, final String namespaceId, final String prefix) {
        super(type, enabled, offset, papiPlaceholder, worlds, namespaceId);
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }
}
