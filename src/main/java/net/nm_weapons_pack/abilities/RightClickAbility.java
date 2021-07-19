package net.nm_weapons_pack.abilities;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public interface RightClickAbility extends Ability {
    @Override
    default AbilityType getAbilityType() {
        return AbilityType.RIGHT_CLICK;
    }

    TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand);
    ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand);
    void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks);
    int getMaxUseTime(ItemStack stack);
    ActionResult useOnBlock(ItemUsageContext context);
}
