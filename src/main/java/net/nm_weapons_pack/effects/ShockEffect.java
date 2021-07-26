package net.nm_weapons_pack.effects;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.InstantStatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.nm_weapons_pack.utils.NmUtils;

public class ShockEffect extends InstantStatusEffect {
    protected static Identifier id = NmUtils.getNmId("shock");

    public ShockEffect() {
        super(StatusEffectType.HARMFUL, 938436);
    }

    public static Identifier getId() {
        return id;
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        double randomX = 0d;
        double randomZ = 0d;
        if (MinecraftClient.getInstance().world != null) {
            randomX = MinecraftClient.getInstance().world.random.nextDouble();
            if (randomX > 0.5) {
                randomX = 0.5 - randomX;
            }
            randomZ = MinecraftClient.getInstance().world.random.nextDouble();
            if (randomZ > 0.5) {
                randomZ = 0.5 - randomZ;
            }
        }
        entity.stopUsingItem();
        entity.setSprinting(false);
        entity.setMovementSpeed(0f);
        entity.damage(DamageSource.MAGIC, amplifier * 3);
        entity.takeKnockback(0.3d, randomX, randomZ);
    }
}
