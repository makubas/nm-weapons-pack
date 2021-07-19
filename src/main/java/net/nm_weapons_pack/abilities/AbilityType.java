package net.nm_weapons_pack.abilities;

import net.minecraft.util.Formatting;

public enum AbilityType {
    RIGHT_CLICK,
    LEFT_CLICK,
    PASSIVE;

    @Override
    public String toString() {
        return super.toString().replace("_", " ");
    }
}
