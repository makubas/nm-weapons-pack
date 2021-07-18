package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.MagicWeaponConfigSettings;

public abstract class NmMagicWeapons extends NmWeapon {
    public NmMagicWeapons(MagicWeaponConfigSettings weaponConfigSettings) {
        super(new FabricItemSettings());
    }
}
