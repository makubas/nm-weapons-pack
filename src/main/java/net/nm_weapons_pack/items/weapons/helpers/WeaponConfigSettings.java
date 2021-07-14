package net.nm_weapons_pack.items.weapons.helpers;

import net.minecraft.util.Rarity;

public class WeaponConfigSettings {
    private String name;
    private Rarity rarity;

    public WeaponConfigSettings(String name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public Rarity getRarity() {
        return rarity;
    }
}
