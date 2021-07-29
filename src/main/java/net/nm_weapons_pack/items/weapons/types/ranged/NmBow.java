package net.nm_weapons_pack.items.weapons.types.ranged;

import net.nm_weapons_pack.items.weapons.helpers.NmRangedWeapon;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.RangedWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmBow extends NmRangedWeapon {
    public NmBow(RangedWeaponConfigSettings rangedWeaponConfigSettings) {
        super(rangedWeaponConfigSettings);
        this.weaponType = NmWeaponType.BOW;
    }
}
