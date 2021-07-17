package net.nm_weapons_pack.items.weapons.helpers;

import net.minecraft.util.Rarity;
import net.nm_weapons_pack.materials.NmWeaponMaterial;

public record WeaponConfigSettings(Rarity rarity, NmWeaponMaterial material) {
    public Rarity getRarity() {
        return rarity;
    }

    public NmWeaponMaterial getMaterial() {
        return material;
    }
}
