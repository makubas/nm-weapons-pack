package net.nm_weapons_pack.items.weapons.types.melee;

import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmMace extends NmMeleeWeapon {
    public NmMace(String identifierString) {
        super(identifierString, NmWeaponType.MACE);
    }
}
