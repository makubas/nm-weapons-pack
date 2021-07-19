package net.nm_weapons_pack.abilities;

import net.minecraft.text.TranslatableText;
import net.nm_weapons_pack.utils.text_formatting.TextFormatter;

public record AbilityTooltip(String title, String description, AbilityType type, AbilityRarity rarity) {
    @Override
    public String title() {
        return title;
    }

    @Override
    public String description() {
        return description;
    }

    public TranslatableText descriptionFormatted() {
        return new TextFormatter(description).getText();
    }

    @Override
    public AbilityType type() {
        return type;
    }

    @Override
    public AbilityRarity rarity() {
        return rarity;
    }
}
