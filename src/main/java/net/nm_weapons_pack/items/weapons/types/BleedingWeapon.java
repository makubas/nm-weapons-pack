package net.nm_weapons_pack.items.weapons.types;

public interface BleedingWeapon {
    default float getBleedingProbability() {
        return 0.3F;
    }
}
