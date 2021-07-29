package net.nm_weapons_pack.items.weapons.helpers.config_settings;

import net.minecraft.util.Rarity;
import net.nm_weapons_pack.materials.NmWeaponMaterial;

public record ThrowableWeaponConfigSettings(Rarity rarity, NmWeaponMaterial material, int stackCount, int maxUseTime) {
    public Rarity getRarity() {
        return rarity;
    }

    public NmWeaponMaterial getMaterial() {
        return material;
    }

    public int getStackCount() {
        return stackCount;
    }

    public int getMaxUseTime() {
        return maxUseTime;
    }
}
