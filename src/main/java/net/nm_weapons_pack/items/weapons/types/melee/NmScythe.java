package net.nm_weapons_pack.items.weapons.types.melee;

import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmScythe extends NmMeleeWeapon {
    public NmScythe(String identifierString) {
        super(identifierString);
        this.weaponType = NmWeaponType.SCYTHE;
        initializeTooltip(this);
    }
}
