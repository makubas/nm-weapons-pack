package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.ThrowableWeaponConfigSettings;

public abstract class NmThrowableWeapon extends NmWeapon {
    public NmThrowableWeapon(ThrowableWeaponConfigSettings weaponConfigSettings) {
        super(new FabricItemSettings());
    }
}
