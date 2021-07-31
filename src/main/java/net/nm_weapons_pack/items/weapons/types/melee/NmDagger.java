package net.nm_weapons_pack.items.weapons.types.melee;

import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmDagger extends NmMeleeWeapon {
    public NmDagger(String identifierString) {
        super(identifierString, NmWeaponType.DAGGER);
    }
}
