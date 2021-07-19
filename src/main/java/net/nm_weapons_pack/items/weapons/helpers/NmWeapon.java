package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.nm_weapons_pack.abilities.AbilityTooltip;
import net.nm_weapons_pack.utils.NmStyle;
import net.nm_weapons_pack.utils.text_formatting.TextFormatter;
import org.jetbrains.annotations.Nullable;
import org.w3c.dom.css.RGBColor;

import java.util.ArrayList;
import java.util.List;

public abstract class NmWeapon extends Item implements Vanishable {
    protected List<TranslatableText> tooltipsTexts = new ArrayList<>();

    public NmWeapon(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        for (TranslatableText text : tooltipsTexts) {
            tooltip.add(text);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    protected void addTooltip(TranslatableText text) {
        tooltipsTexts.add(text);
    }

    protected void addAbilityTooltip(AbilityTooltip tooltip) {
        //         addTooltip((TranslatableText) new TranslatableText("Weapon Ability: " + tooltip.title()).formatted(tooltip.rarity().getColor()) + (TranslatableText) new TranslatableText(tooltip.type().toString()).formatted(Formatting.BOLD).formatted(tooltip.rarity().getColor()));
        // new TranslatableText("Weapon Ability: " + tooltip.title()).formatted(tooltip.rarity().getColor()).append(new TranslatableText(tooltip.type().toString()).formatted(Formatting.BOLD).formatted(tooltip.rarity().getColor()));

        // TextColor.fromRgb(3628263)

        TranslatableText weaponAbilityTitle1 = (TranslatableText) new TranslatableText("Weapon Ability: " + tooltip.title() + " ").formatted(tooltip.rarity().getColor());
        TranslatableText weaponAbilityTitle2 = (TranslatableText) new TranslatableText(tooltip.type().toString()).formatted(Formatting.BOLD).formatted(tooltip.rarity().getColor());
        addTooltip((TranslatableText) weaponAbilityTitle1.append(weaponAbilityTitle2));
        addTooltip(tooltip.descriptionFormatted());


    }
}
