package net.nm_weapons_pack.utils.text_formatting;

import net.minecraft.text.Style;
import net.minecraft.text.TranslatableText;

import net.nm_weapons_pack.utils.NmStyle;

public enum TextTag {
    HP(NmStyle.HEALTH.getStyle(), ""),
    DEF(NmStyle.DEFENCE.getStyle(), ""),
    DMG(NmStyle.DAMAGE.getStyle(), ""),
    SPE(NmStyle.SPEED.getStyle(), ""),
    EFF(NmStyle.EFFECT.getStyle(), ""),
    SPC(NmStyle.SPECIAL.getStyle(), "");

    final Style style;
    final String symbol;

    TextTag(Style style) {
        this.style = style;
        this.symbol = "";
    }

    TextTag(Style style, String symbol) {
        this.style = style;
        this.symbol = symbol;
    }

    public static TextTag getTag(String tag) {
        for (TextTag textTag : TextTag.values()) {
            if (textTag.toString().toLowerCase().equals(tag)) {
                return textTag;
            }
        }
        return null;
    }

    public static TranslatableText getTextWithTag(String text, TextTag tag) {
        return (TranslatableText) new TranslatableText(text + tag.symbol).setStyle(tag.style);
    }
}
