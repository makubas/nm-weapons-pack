package net.nm_weapons_pack.items.weapons.types;

import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;

public abstract class NmBattleAxe extends NmMeleeWeapon {
    public NmBattleAxe(String identifierString) {
        super(identifierString);
        this.weaponType = NmWeaponType.BATTLE_AXE;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0F) {
            stack.damage(2, miner, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
        }
        return true;
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return state.getMaterial() == Material.WOOD || state.getMaterial() == Material.NETHER_WOOD;
    }
}
