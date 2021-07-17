package net.nm_weapons_pack.items.projectiles.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.materials.NmProjectileMaterial;

public class NmProjectileFromConfig extends Item implements Vanishable {
    protected final NmProjectileMaterial material;

    public NmProjectileFromConfig(ProjectileConfigSettings projectileConfigSettings) {
        super(new FabricItemSettings().rarity(projectileConfigSettings.getRarity()).group(NmItems.NM_WEAPONS_PACK_GROUP).maxCount(projectileConfigSettings.getStackAmount()));
        this.material = projectileConfigSettings.getMaterial();
    }

    public NmProjectileMaterial getMaterial() {
        return this.material;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return true;
    }

    @Override
    public int getEnchantability() {
        return 0;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return false;
    }
}