package net.nm_weapons_pack.items.weapons.types.melee;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;

public abstract class NmBattleAxe extends NmMeleeWeapon {
    public NmBattleAxe(String identifierString) {
        super(identifierString, NmWeaponType.BATTLE_AXE);
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD;
    }
}
