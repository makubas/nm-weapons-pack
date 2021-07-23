package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.RangedWeaponConfigSettings;
import net.nm_weapons_pack.materials.NmWeaponMaterial;

import java.util.function.Predicate;

public abstract class NmRangedWeapon extends NmWeapon {
    protected final NmWeaponMaterial material;

    public NmRangedWeapon(RangedWeaponConfigSettings rangedWeaponConfigSettings) {
        super(new FabricItemSettings());
        this.material = rangedWeaponConfigSettings.getMaterial();
    }

    public NmWeaponMaterial getMaterial() {
        return this.material;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    public static final Predicate<ItemStack> PROJECTILES = (stack) -> {
        return stack.isIn(ItemTags.ARROWS);
    };

    public Predicate<ItemStack> getHeldProjectiles() {
        return this.getProjectiles();
    }

    public abstract Predicate<ItemStack> getProjectiles();

    public static ItemStack getHeldProjectile(LivingEntity entity, Predicate<ItemStack> predicate) {
        if (predicate.test(entity.getStackInHand(Hand.OFF_HAND))) {
            return entity.getStackInHand(Hand.OFF_HAND);
        } else {
            return predicate.test(entity.getStackInHand(Hand.MAIN_HAND)) ? entity.getStackInHand(Hand.MAIN_HAND) : ItemStack.EMPTY;
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        boolean bl = !user.getArrowType(itemStack).isEmpty();
        if (!user.getAbilities().creativeMode && !bl) {
            return TypedActionResult.fail(itemStack);
        } else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public int getEnchantability() {
        return material.getEnchantability();
    }

    public abstract int getRange();
}
