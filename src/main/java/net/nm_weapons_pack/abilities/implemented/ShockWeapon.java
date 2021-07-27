package net.nm_weapons_pack.abilities.implemented;

import net.nm_weapons_pack.abilities.AbilityTooltip;
import net.nm_weapons_pack.abilities.AbilityType;

public interface ShockWeapon extends ImplementedPassiveAbility {
    float getShockProbability();

    static AbilityTooltip getTooltip() {
        return new AbilityTooltip(
                "Shocking",
                "<eff>Shocks<eff> your target!",
                AbilityType.PASSIVE,
                0
        );
    }
}
