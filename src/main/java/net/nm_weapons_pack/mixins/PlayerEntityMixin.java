package net.nm_weapons_pack.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SwordItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.effects.NmEffects;
import net.nm_weapons_pack.items.weapons.types.BleedingWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    public Item attackSwordItemMixin(ItemStack itemStack) {
        if (itemStack.getItem() instanceof BleedingWeapon) {
            return Items.DIAMOND_SWORD;
        } else {
            return itemStack.getItem();
        }
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getSweepingMultiplier(Lnet/minecraft/entity/LivingEntity;)F"))
    public void attackMixin(Entity target, CallbackInfo info) {
        ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
        if (!this.world.isClient && itemStack.getItem() instanceof BleedingWeapon && target instanceof LivingEntity) {
            if (this.world.random.nextFloat() <= ((BleedingWeapon) itemStack.getItem()).getBleedingProbability()) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(NmEffects.BLEEDING, 80));
            }
        }
    }
}
