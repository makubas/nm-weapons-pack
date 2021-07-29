package net.nm_weapons_pack.items.weapons.types.melee;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmWarHammer extends NmMeleeWeapon {
    public NmWarHammer(String identifierString) {
        super(identifierString);
        this.weaponType = NmWeaponType.WAR_HAMMER;
        initializeTooltip(this);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.getMaterial() == Material.STONE || state.getMaterial() == Material.METAL;
    }
}
