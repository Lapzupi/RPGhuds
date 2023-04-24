package dev.lone.rpghuds.core.settings;

import com.google.common.collect.ImmutableMap;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static dev.lone.rpghuds.utils.Utils.getFontImage;

/**
 * @author sarhatabaot
 */
public class MoneySettings extends IconAmountSettings{
    private final FontImageWrapper char_k;
    private final FontImageWrapper char_m;
    private final FontImageWrapper char_b;
    private final FontImageWrapper char_t;
    private final FontImageWrapper char_dot;
    private final FontImageWrapper char_comma;
    private final FontImageWrapper char_arrow_up;
    private final FontImageWrapper char_arrow_down;

    public MoneySettings(final String namespacedId, final int initialOffsetX, final List<String> worlds, final String iconPath,
                         @NotNull final ImmutableMap<String, String> settingsMap, @NotNull final ImmutableMap<String, String> charSettings) {
        super(namespacedId, initialOffsetX, worlds, iconPath, settingsMap);

        this.char_k = getFontImage(charSettings.get("char_k"));
        this.char_m = getFontImage(charSettings.get("char_m"));
        this.char_b = getFontImage(charSettings.get("char_b"));
        this.char_t = getFontImage(charSettings.get("char_t"));
        this.char_dot = getFontImage(charSettings.get("char_dot"));
        this.char_comma = getFontImage(charSettings.get("char_comma"));
        this.char_arrow_up = getFontImage(charSettings.get("char_arrow_up"));
        this.char_arrow_down = getFontImage(charSettings.get("char_arrow_down"));

        charMap.put('k', this.char_k);
        charMap.put('K', this.char_k);
        charMap.put('m', this.char_m);
        charMap.put('M', this.char_m);
        charMap.put('b', this.char_b);
        charMap.put('B', this.char_b);
        charMap.put('t', this.char_t);
        charMap.put('T', this.char_t);
        charMap.put('.', this.char_dot);
        charMap.put(',', this.char_comma);
    }

    public FontImageWrapper getChar_k() {
        return char_k;
    }

    public FontImageWrapper getChar_m() {
        return char_m;
    }

    public FontImageWrapper getChar_b() {
        return char_b;
    }

    public FontImageWrapper getChar_t() {
        return char_t;
    }

    public FontImageWrapper getChar_dot() {
        return char_dot;
    }

    public FontImageWrapper getChar_comma() {
        return char_comma;
    }

    public FontImageWrapper getChar_arrow_up() {
        return char_arrow_up;
    }

    public FontImageWrapper getChar_arrow_down() {
        return char_arrow_down;
    }
}
