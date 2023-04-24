package dev.lone.rpghuds.core.settings;

import com.google.common.collect.ImmutableMap;
import dev.lone.itemsadder.api.FontImages.FontImageWrapper;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;

import static dev.lone.rpghuds.utils.Utils.getFontImage;

/**
 * @author sarhatabaot
 */
public class IconAmountSettings extends HudSettings {
    private final FontImageWrapper icon;
    private final FontImageWrapper digit_0;
    private final FontImageWrapper digit_1;
    private final FontImageWrapper digit_2;
    private final FontImageWrapper digit_3;
    private final FontImageWrapper digit_4;
    private final FontImageWrapper digit_5;
    private final FontImageWrapper digit_6;
    private final FontImageWrapper digit_7;
    private final FontImageWrapper digit_8;
    private final FontImageWrapper digit_9;
    private final FontImageWrapper char_unknown;

    protected final HashMap<Character, FontImageWrapper> charMap = new HashMap<>();

    public IconAmountSettings(final String namespacedId, final int initialOffsetX, final List<String> worlds, final String iconPath,  @NotNull ImmutableMap<String, String> settingsMap) {
        super(namespacedId, initialOffsetX, worlds);

        this.icon = getFontImage(iconPath);
        this.digit_0 = getFontImage(settingsMap.get("digit_0"));
        this.digit_1 = getFontImage(settingsMap.get("digit_1"));
        this.digit_2 = getFontImage(settingsMap.get("digit_2"));
        this.digit_3 = getFontImage(settingsMap.get("digit_3"));
        this.digit_4 = getFontImage(settingsMap.get("digit_4"));
        this.digit_5 = getFontImage(settingsMap.get("digit_5"));
        this.digit_6 = getFontImage(settingsMap.get("digit_6"));
        this.digit_7 = getFontImage(settingsMap.get("digit_7"));
        this.digit_8 = getFontImage(settingsMap.get("digit_8"));
        this.digit_9 = getFontImage(settingsMap.get("digit_9"));
        this.char_unknown = getFontImage(settingsMap.get("char_unknown"));

        charMap.put('0', this.digit_0);
        charMap.put('1', this.digit_1);
        charMap.put('2', this.digit_2);
        charMap.put('3', this.digit_3);
        charMap.put('4', this.digit_4);
        charMap.put('5', this.digit_5);
        charMap.put('6', this.digit_6);
        charMap.put('7', this.digit_7);
        charMap.put('8', this.digit_8);
        charMap.put('9', this.digit_9);
    }



    /**
     * Appends the FontImages representation of the provided amount String to the provided FontImages list.
     *
     * @param amount amount string (example: 25.3M)
     * @param list   FontImages list of the HUD.
     */
    public void appendAmountToImages(String amount, List<FontImageWrapper> list) {
        FontImageWrapper img;
        char c;
        for (int i = 0; i < amount.length(); i++) {
            c = amount.charAt(i);
            img = charMap.get(c);
            if (img == null)
                list.add(char_unknown);
            else
                list.add(img);
        }
    }

    public FontImageWrapper getIcon() {
        return icon;
    }

    public FontImageWrapper getDigit_0() {
        return digit_0;
    }

    public FontImageWrapper getDigit_1() {
        return digit_1;
    }

    public FontImageWrapper getDigit_2() {
        return digit_2;
    }

    public FontImageWrapper getDigit_3() {
        return digit_3;
    }

    public FontImageWrapper getDigit_4() {
        return digit_4;
    }

    public FontImageWrapper getDigit_5() {
        return digit_5;
    }

    public FontImageWrapper getDigit_6() {
        return digit_6;
    }

    public FontImageWrapper getDigit_7() {
        return digit_7;
    }

    public FontImageWrapper getDigit_8() {
        return digit_8;
    }

    public FontImageWrapper getDigit_9() {
        return digit_9;
    }

    public FontImageWrapper getChar_unknown() {
        return char_unknown;
    }
}
