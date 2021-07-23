package net.nm_weapons_pack.effects;

import net.minecraft.client.option.ParticlesMode;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.Identifier;
import net.nm_weapons_pack.particles.NmParticles;
import net.nm_weapons_pack.utils.NmUtils;

public class BleedingEffect extends StatusEffect {
    protected static Identifier id = NmUtils.getNmId("bleeding");

    public BleedingEffect() {
        super(StatusEffectType.HARMFUL, 14364954);
    }

    public static Identifier getId() {
        return id;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(DamageSource.STARVE, 1F);
        // TODO: 23.07.2021 change particle type to bleeding
        entity.getEntityWorld().addParticle(ParticleTypes.ASH, entity.getX(), entity.getY(), entity.getZ(), 0, 0, 0);
    }
}
