package net.nm_weapons_pack.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.nm_weapons_pack.effects.BleedingEffect;
import net.nm_weapons_pack.effects.NmEffects;
import net.nm_weapons_pack.items.weapons.helpers.NmWeapon;
import net.nm_weapons_pack.items.weapons.types.BleedingWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getSweepingMultiplier(Lnet/minecraft/entity/LivingEntity;)F"))
    public void attackMixin(Entity target, CallbackInfo info) {
        ItemStack itemStack = this.getStackInHand(Hand.MAIN_HAND);
        if (!this.world.isClient && itemStack.getItem() instanceof BleedingWeapon && target instanceof LivingEntity) {
            if (this.world.random.nextFloat() <= 0.1F + ((BleedingWeapon) itemStack.getItem()).getBleedingProbability()) {
                ((LivingEntity) target).addStatusEffect(new StatusEffectInstance(NmEffects.BLEEDING, 80));
            }
        }
    }
}
