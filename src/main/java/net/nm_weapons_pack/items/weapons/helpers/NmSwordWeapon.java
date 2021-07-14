package net.nm_weapons_pack.items.weapons.helpers;

import net.minecraft.item.Vanishable;
import net.minecraft.util.Identifier;

public abstract class NmSwordWeapon extends NmWeaponFromConfig implements Vanishable {
    public NmSwordWeapon(WeaponConfigSettings weaponConfigSettings) {
        super(weaponConfigSettings);
    }

    public NmSwordWeapon(Identifier id) {
        super(id);
    }
}
