package net.nm_weapons_pack.items.weapons.types;

import net.nm_weapons_pack.items.weapons.helpers.NmRangedWeapon;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.RangedWeaponConfigSettings;

public abstract class NmBowWeapon extends NmRangedWeapon {
    public NmBowWeapon(RangedWeaponConfigSettings rangedWeaponConfigSettings) {
        super(rangedWeaponConfigSettings);
        this.weaponType = NmWeaponType.BOW;
    }
}
