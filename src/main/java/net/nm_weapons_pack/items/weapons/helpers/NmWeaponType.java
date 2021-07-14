package net.nm_weapons_pack.items.weapons.helpers;

public enum NmWeaponType {
    WAR_HAMMER,
    JAVELIN,
    SPEAR,
    SHURIKEN,
    SWORD,
    BOW,
    LONGBOW,
    DART,
    MACE,
    STAFF,
    DAGGER,
    BATTLE_AXE,
    KATAR,
    DISC,
    SCYTHE;

    public static NmWeaponType getWeaponType(String id) {
        for (NmWeaponType type : NmWeaponType.values()) {
            if (type.toString().toLowerCase().equals(id)) {
                return type;
            }
        }
        return null;
    }
}
