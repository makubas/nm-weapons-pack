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
import net.nm_weapons_pack.items.weapons.helpers.NmMeleeWeapon;
import net.nm_weapons_pack.abilities.implemented.BleedingWeapon;
import net.nm_weapons_pack.abilities.implemented.ShockWeapon;
import net.nm_weapons_pack.abilities.implemented.VulnerabilityWeapon;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    @Shadow public abstract Map<StatusEffect, StatusEffectInstance> getActiveStatusEffects();

    @Shadow @Nullable private LivingEntity attacker;

    @Shadow public abstract Collection<StatusEffectInstance> getStatusEffects();

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "getHandSwingDuration()I", at = @At("HEAD"), cancellable = true)
    private void getHandSwingDuration(CallbackInfoReturnable<Integer> info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (itemStack.getItem() instanceof NmMeleeWeapon) {
            if ((((NmMeleeWeapon) itemStack.getItem()).getWeaponType()).isDoubleHanded()) {
                info.setReturnValue(10);
            }
        }
    }

    @Inject(method = "blockedByShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/PersistentProjectileEntity;getPierceLevel()B"), cancellable = true)
    private void blockedByShieldMixin(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (itemStack.getItem() instanceof NmMeleeWeapon) {
            if ((((NmMeleeWeapon) itemStack.getItem()).getWeaponType()).isDoubleHanded()) {
                info.setReturnValue(false);
            }
        }
    }

    @Inject(method = "blockedByShield", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/Vec3d;dotProduct(Lnet/minecraft/util/math/Vec3d;)D", shift = At.Shift.AFTER), cancellable = true)
    private void blockedByShieldDamageWeaponMixin(DamageSource source, CallbackInfoReturnable<Boolean> info) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        ItemStack itemStack = livingEntity.getMainHandStack();
        if (itemStack.getItem() instanceof NmMeleeWeapon) {
            if ((((NmMeleeWeapon) itemStack.getItem()).getWeaponType()).isDoubleHanded()) {
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
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity.hasStatusEffect(NmEffects.BLEEDING)) {
            ci.cancel();
        }
    }

    @Inject(method = "damage", at = @At("HEAD"))
    private void damageEffect(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (source.getAttacker() instanceof LivingEntity attacker) {
            if (attacker.getMainHandStack().getItem() instanceof BleedingWeapon) {
                boolean applyEffect = this.world.random.nextFloat() <= ((BleedingWeapon) attacker.getMainHandStack().getItem()).getBleedingProbability();
                if (applyEffect) {
                    if (livingEntity.hasStatusEffect(NmEffects.BLEEDING)) {
                        livingEntity.addStatusEffect(new StatusEffectInstance(NmEffects.BLEEDING, 80, livingEntity.removeStatusEffectInternal(NmEffects.BLEEDING).getAmplifier() + 1));
                    } else {
                        livingEntity.addStatusEffect(new StatusEffectInstance(NmEffects.BLEEDING, 80, 1));
                    }
                }
            }

            if (attacker.getMainHandStack().getItem() instanceof VulnerabilityWeapon) {
                boolean applyEffect = this.world.random.nextFloat() <= ((VulnerabilityWeapon) attacker.getMainHandStack().getItem()).getVulnerabilityProbability();
                if (applyEffect && !livingEntity.hasStatusEffect(NmEffects.VULNERABILITY)) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(NmEffects.VULNERABILITY, 200, 1));
                }
            }

            if (attacker.getMainHandStack().getItem() instanceof ShockWeapon) {
                boolean applyEffect = this.world.random.nextFloat() <= ((ShockWeapon) attacker.getMainHandStack().getItem()).getShockProbability();
                if (applyEffect) {
                    livingEntity.addStatusEffect(new StatusEffectInstance(NmEffects.SHOCK, 1, 1));
                }
            }
        }

    }

    @ModifyVariable(method = "damage", at = @At("HEAD"), ordinal = 0)
    private float damageVulnerability(float amount) {
        LivingEntity livingEntity = (LivingEntity) (Object) this;
        if (livingEntity.hasStatusEffect(NmEffects.VULNERABILITY)) {
            return amount + livingEntity.getStatusEffect(NmEffects.VULNERABILITY).getAmplifier() * 3;
        } else {
            return amount;
        }
    }
}
