package net.nm_weapons_pack.items.weapons.types.melee;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmSword extends NmMeleeWeapon {
    public NmSword(String identifierString) {
        super(identifierString);
        this.weaponType = NmWeaponType.SWORD;
        initializeTooltip(this);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.isOf(Blocks.COBWEB);
    }
}
