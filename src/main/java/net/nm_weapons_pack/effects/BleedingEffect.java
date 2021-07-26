package net.nm_weapons_pack.effects;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
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
        entity.damage(DamageSource.STARVE, amplifier * 0.5F);
        for (int i = 0; i < amplifier * 2; i++) {
            double random = 0d;
            if (MinecraftClient.getInstance().world != null) {
                random = MinecraftClient.getInstance().world.random.nextDouble() * 0.5;
            }
            entity.getEntityWorld().addParticle(NmParticles.BLEEDING_PARTICLE, entity.getX() + random, entity.getY(), entity.getZ() + random, 0, 0, 0);
        }
    }
}
