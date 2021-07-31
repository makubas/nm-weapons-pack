package net.nm_weapons_pack.items.weapons.types.melee;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmWarHammer extends NmMeleeWeapon {
    public NmWarHammer(String identifierString) {
        super(identifierString, NmWeaponType.WAR_HAMMER);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.getMaterial() == Material.STONE || state.getMaterial() == Material.METAL;
    }
}
