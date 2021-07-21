package net.nm_weapons_pack.utils;

import net.minecraft.text.Style;
import net.minecraft.util.Rarity;

public enum NmStyle {
    // Abilities
    COMMON(Style.EMPTY.withFormatting(Rarity.COMMON.formatting)),
    UNCOMMON(Style.EMPTY.withFormatting(Rarity.UNCOMMON.formatting)),
    RARE(Style.EMPTY.withFormatting(Rarity.RARE.formatting)),
    EPIC(Style.EMPTY.withFormatting(Rarity.EPIC.formatting)),

    // Statistics indicators
    HEALTH(Style.EMPTY.withColor(13183530).withItalic(true)),
    SPEED(Style.EMPTY.withColor(2064499).withItalic(true)),
    DEFENCE(Style.EMPTY.withColor(3049263).withItalic(true)),
    DAMAGE(Style.EMPTY.withColor(10712079)),
    EFFECT(Style.EMPTY.withColor(12196458)),
    SPECIAL(Style.EMPTY.withColor(11937409)),

    DESCRIPTION(Style.EMPTY.withColor(11053224)),
    COOLDOWN(Style.EMPTY.withColor(7237230));
    
    private final Style style;
    
    NmStyle(Style style) {
        this.style = style;
    }

    public Style getStyle() {
        return style;
    }

    public static Style getStyle(Rarity rarity) {
        for (NmStyle nmStyle : NmStyle.values()) {
            if (nmStyle.toString().toUpperCase().equals(rarity.toString().toUpperCase())) {
                return nmStyle.getStyle();
            }
        }
        return null;
    }
}
