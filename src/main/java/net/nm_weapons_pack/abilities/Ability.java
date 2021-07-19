package net.nm_weapons_pack.abilities;

import net.minecraft.text.TranslatableText;

public interface Ability {
    String getAbilityName();
    String getAbilityDescription();
    AbilityType getAbilityType();
    AbilityRarity getAbilityRarity();
}
