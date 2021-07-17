package net.nm_weapons_pack.items.projectiles.helpers;

import net.minecraft.util.Rarity;
import net.nm_weapons_pack.materials.NmProjectileMaterial;
import net.nm_weapons_pack.materials.NmWeaponMaterial;

public record ProjectileConfigSettings(Rarity rarity, int stackAmount, NmProjectileMaterial material) {
    public Rarity getRarity() {
        return rarity;
    }

    public int getStackAmount() {
        return stackAmount;
    }

    public NmProjectileMaterial getMaterial() {
        return material;
    }
}