package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.Vanishable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nm_weapons_pack.abilities.*;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.RangedWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.types.NmAttackMethod;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmStyle;
import net.nm_weapons_pack.utils.NmUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public abstract class NmRangedWeapon extends RangedWeaponItem implements Vanishable {
    protected Rarity rarity;
    protected NmWeaponType weaponType;
    protected NmWeaponMaterial material;
    protected Identifier identifier;
    protected final NmAttackMethod attackMethod = NmAttackMethod.RANGED;
    protected List<TranslatableText> tooltipsTexts = new ArrayList<>();

    public NmRangedWeapon(String identifierString) {
        super(new FabricItemSettings()
                .maxCount(1)
                .group(NmItems.NM_WEAPONS_PACK_GROUP)
                .rarity(NmConfig.getRangedWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getRarity())
                .maxDamageIfAbsent((NmConfig.getRangedWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getMaterial().getDurability())));
        RangedWeaponConfigSettings rangedWeaponConfigSettings = NmConfig.getRangedWeaponConfigSettings().get(NmUtils.getNmId(identifierString));
        this.identifier = NmUtils.getNmId(identifierString);
        this.material = rangedWeaponConfigSettings.getMaterial();
        this.rarity = rangedWeaponConfigSettings.getRarity();
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.addAll(tooltipsTexts);
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
    }

    @Override
    public boolean isSuitableFor(BlockState state) {
        return false;
    }

    protected Rarity getRarity() {
        return rarity;
    }

    public List<AbilityTooltip> getImplementedAbilities() {
        return new ArrayList<AbilityTooltip>();
    }

    public NmWeaponType getWeaponType() {
        return weaponType;
    }

    public Identifier getId() {
        return identifier;
    }

    public NmAttackMethod getAttackMethod() {
        return attackMethod;
    }

    private void addTooltip(TranslatableText text) {
        tooltipsTexts.add(text);
    }

    private void addAbilityTooltip(AbilityTooltip tooltip) {
        TranslatableText weaponAbilityTitle1 = (TranslatableText) new TranslatableText("Weapon Ability: " + tooltip.title() + " ").formatted(getRarity().formatting);
        TranslatableText weaponAbilityTitle2 = (TranslatableText) new TranslatableText(tooltip.type().toString()).formatted(Formatting.BOLD).formatted(getRarity().formatting);
        addTooltip((TranslatableText) weaponAbilityTitle1.append(weaponAbilityTitle2));
        addTooltip(tooltip.descriptionFormatted());
        if (tooltip.type() != AbilityType.PASSIVE) {
            addTooltip((TranslatableText) new TranslatableText("Cooldown: " + tooltip.cooldownSeconds() + " seconds").setStyle(NmStyle.COOLDOWN.getStyle()));
        }
        addEmptyTooltipLine();
    }

    protected void addRarityTooltip(Rarity rarity, NmWeaponType type) {
        addTooltip((TranslatableText) new TranslatableText(
                rarity.toString().toUpperCase() + " " + type.toString().toUpperCase().replace("_", " "))
                .setStyle(NmStyle.getStyle(rarity).withBold(true)));
    }

    protected void addEmptyTooltipLine() {
        addTooltip(new TranslatableText(""));
    }

    protected <T extends NmRangedWeapon> void addAbilities(T t) {
        if (LeftClickAbility.class.isAssignableFrom(t.getClass())) {
            addAbilityTooltip(((LeftClickAbility) t).getLeftClickAbilityTooltip());
        }
        if (RightClickAbility.class.isAssignableFrom(t.getClass())) {
            addAbilityTooltip(((RightClickAbility) t).getRightClickAbilityTooltip());
        }
        if (PassiveAbility.class.isAssignableFrom(t.getClass())) {
            addAbilityTooltip(((PassiveAbility) t).getPassiveAbilityTooltip());
        }
    }

    protected <T extends NmRangedWeapon> void initializeTooltip(T t) {
        addAbilities(t);
        for (AbilityTooltip abilityTooltip : t.getImplementedAbilities()) {
            addAbilityTooltip(abilityTooltip);
        }
        addRarityTooltip(t.getRarity(), t.getWeaponType());
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return null;
    }

    @Override
    public int getRange() {
        return 0;
    }
}
