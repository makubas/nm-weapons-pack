package net.nm_weapons_pack.mixins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.effects.NmEffects;
import net.nm_weapons_pack.items.weapons.helpers.NmWeapon;
import net.nm_weapons_pack.items.weapons.types.BleedingWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getHandSwingDuration()I", at = @At("HEAD"), cancellable = true)
    private void getHandSwingDuration(CallbackInfoReturnable<Integer> info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (itemStack.getItem() instanceof NmWeapon) {
            if (NmWeaponType.isDoubleHanded(((NmWeapon) itemStack.getItem()).getWeaponType())) {
                info.setReturnValue(10);
            }
        }
    }

    @Inject(method = "blockedByShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;getPierceLevel()B"), cancellable = true)
    private void blockedByShieldMixin(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (itemStack.getItem() instanceof NmWeapon) {
            if (NmWeaponType.isDoubleHanded(((NmWeapon) itemStack.getItem()).getWeaponType())) {
                info.setReturnValue(false);
            }
        }
    }

    @Inject(method = "blockedByShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;dotProduct(Lnet/minecraft/util/math/Vec3d;)D", shift = At.Shift.AFTER), cancellable = true)
    private void blockedByShieldDamageWeaponMixin(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (itemStack.getItem() instanceof NmWeapon) {
            if (NmWeaponType.isDoubleHanded(((NmWeapon) itemStack.getItem()).getWeaponType())) {
                if (livingEntity instanceof PlayerEntity) {
                    ((PlayerEntity) livingEntity).getItemCooldownManager().set(itemStack.getItem(), 5);
                }
                if (!world.isClient) {
                    livingEntity.getMainHandStack().damage(1, livingEntity, (p) -> p.sendToolBreakStatus(p.getActiveHand()));
                }
            }
        }
    }

    @Inject(method = "tickStatusEffects()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)V"), cancellable = true)
    private void tickStatusEffectMixin(CallbackInfo ci) {
        if (getActiveStatusEffects().containsKey(NmEffects.BLEEDING)) {
            ci.cancel();
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void damageEffect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (source.getAttacker() instanceof LivingEntity) {
            if (((LivingEntity) source.getAttacker()).getMainHandStack().getItem() instanceof BleedingWeapon) {
                boolean applyEffect = this.world.random.nextFloat() <= ((BleedingWeapon) ((LivingEntity) source.getAttacker()).getMainHandStack().getItem()).getBleedingProbability();
                if (applyEffect && !livingEntity.hasStatusEffect(NmEffects.BLEEDING)) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(NmEffects.BLEEDING, 80, 1));
                } else if (applyEffect && livingEntity.hasStatusEffect(NmEffects.BLEEDING)) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(NmEffects.BLEEDING, 80, livingEntity.removeStatusEffectInternal(NmEffects.BLEEDING).getAmplifier() + 1));
                }
            }
        }
    }
}
