package net.nm_weapons_pack.effects;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class NmEffects {
    public static final StatusEffect BLEEDING = new BleedingEffect();
    public static final StatusEffect VULNERABILITY = new VulnerabilityEffect();
    public static final StatusEffect SHOCK = new ShockEffect();

    public static void registerEffects() {
        registerEffect(BleedingEffect.getId(), BLEEDING);
        registerEffect(VulnerabilityEffect.getId(), VULNERABILITY);
        registerEffect(ShockEffect.getId(), SHOCK);
    }

    private static void registerEffect(Identifier id, StatusEffect effect) {
        Registry.register(Registry.STATUS_EFFECT, id, effect);
    }
}
