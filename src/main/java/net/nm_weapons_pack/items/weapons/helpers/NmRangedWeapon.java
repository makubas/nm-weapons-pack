package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.RangedWeaponConfigSettings;

public abstract class NmRangedWeapon extends NmWeapon {
    public NmRangedWeapon(RangedWeaponConfigSettings weaponConfigSettings) {
        super(new FabricItemSettings());
    }
}
