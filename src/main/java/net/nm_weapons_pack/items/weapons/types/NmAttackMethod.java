package net.nm_weapons_pack.items.weapons.types;

public enum NmAttackMethod {
    MELEE,
    RANGED,
    THROWABLE,
    MAGIC;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
