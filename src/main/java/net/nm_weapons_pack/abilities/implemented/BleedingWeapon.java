package net.nm_weapons_pack.abilities.implemented;

import net.nm_weapons_pack.abilities.AbilityTooltip;
import net.nm_weapons_pack.abilities.AbilityType;

public interface BleedingWeapon extends ImplementedPassiveAbility {
    float getBleedingProbability();

    static AbilityTooltip getTooltip() {
        return new AbilityTooltip(
                "Bleeding",
                "Applies <eff>bleeding<eff> to attacked target!",
                AbilityType.PASSIVE,
                0
        );
    }
}
