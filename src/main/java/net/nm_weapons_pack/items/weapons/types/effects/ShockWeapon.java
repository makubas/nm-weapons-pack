package net.nm_weapons_pack.items.weapons.types.effects;

public interface ShockWeapon {
    default float getShockProbability() {
        return 0.3F;
    }
}
