package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.materials.NmWeaponMaterial;

public class NmWeaponFromConfig extends Item implements Vanishable {
    protected final NmWeaponMaterial material;

    public NmWeaponFromConfig(WeaponConfigSettings weaponConfigSettings) {
        super(new FabricItemSettings().rarity(weaponConfigSettings.getRarity()).group(NmItems.NM_WEAPONS_PACK_GROUP).maxCount(1).maxDamageIfAbsent(weaponConfigSettings.getMaterial().getDurability()));
        this.material = weaponConfigSettings.getMaterial();
    }

    public NmWeaponMaterial getMaterial() {
        return this.material;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }
}
