package dev.lone.rpghuds.core.config;

import java.util.List;

/**
 * @author sarhatabaot
 */
public class MoneyHudConfig extends HudConfig{
    private String icon;

    public MoneyHudConfig(final HudType type, final boolean enabled, final int offset, final String papiPlaceholder, final List<String> worlds, final String namespaceId, final String icon) {
        super(type, enabled, offset, papiPlaceholder, worlds, namespaceId);
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }
}
