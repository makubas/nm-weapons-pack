package net.nm_weapons_pack.abilities;

import net.minecraft.util.Formatting;

public enum AbilityRarity {
    COMMON,
    RARE,
    EPIC,
    LEGENDARY;

    public Formatting getColor() {
        return switch (this) {
            case RARE -> Formatting.BLUE;
            case EPIC -> Formatting.LIGHT_PURPLE;
            case LEGENDARY -> Formatting.GOLD;
            default -> Formatting.WHITE;
        };
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
