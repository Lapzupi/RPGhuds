package dev.lone.rpghuds.core.config;


import java.util.List;

public class HudConfig {
    private HudType type;
    private boolean enabled;
    private int offset;
    private String papiPlaceholder;

    private List<String> worlds;

    private String namespaceId;

    public HudConfig(final HudType type, final boolean enabled, final int offset, final String papiPlaceholder, final List<String> worlds, final String namespaceId) {
        this.type = type;
        this.enabled = enabled;
        this.offset = offset;
        this.papiPlaceholder = papiPlaceholder;
        this.worlds = worlds;
        this.namespaceId = namespaceId;
    }

    public HudType getType() {
        return type;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public int getOffset() {
        return offset;
    }

    public String getPapiPlaceholder() {
        return papiPlaceholder;
    }

    public String getNamespaceId() {
        return namespaceId;
    }

    public List<String> getWorlds() {
        return worlds;
    }
}
