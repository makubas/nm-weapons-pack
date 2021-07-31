package net.nm_weapons_pack.items.weapons.helpers;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nm_weapons_pack.abilities.*;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.ThrowableWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.types.NmAttackMethod;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmStyle;
import net.nm_weapons_pack.utils.NmUtils;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public abstract class NmThrowableWeapon extends Item implements Vanishable {
    protected Rarity rarity;
    protected NmWeaponType weaponType;
    protected Identifier identifier;
    protected List<TranslatableText> tooltipsTexts = new ArrayList<>();
    protected NmWeaponMaterial material;
    protected final NmAttackMethod attackMethod = NmAttackMethod.THROWABLE;
    protected Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    protected int maxUseTime;

    public NmThrowableWeapon(String identifierString) {
        super(new FabricItemSettings()
                .rarity(NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getRarity())
                .group(NmItems.NM_WEAPONS_PACK_GROUP)
                .maxCount(NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getStackCount())
                .maxDamageIfAbsent((NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getMaterial().getDurability())));
        ThrowableWeaponConfigSettings throwableWeaponConfigSettings = NmConfig.getThrowableWeaponConfigSettings().get(NmUtils.getNmId(identifierString));
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", throwableWeaponConfigSettings.getMaterial().getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", throwableWeaponConfigSettings.getMaterial().getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
        this.identifier = NmUtils.getNmId(identifierString);
        this.attributeModifiers = builder.build();
        this.material = throwableWeaponConfigSettings.getMaterial();
        this.rarity = throwableWeaponConfigSettings.getRarity();
        this.maxUseTime = throwableWeaponConfigSettings.getMaxUseTime();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getDamage() >= itemStack.getMaxDamage() - 1) {
            return TypedActionResult.fail(itemStack);
        }  else {
            user.setCurrentHand(hand);
            return TypedActionResult.consume(itemStack);
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return this.maxUseTime;
    }

    public NmWeaponMaterial getMaterial() {
        return this.material;
    }

    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        for (TranslatableText text : tooltipsTexts) {
            tooltip.add(text);
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
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

    // Tooltips functions

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
        addTooltip((TranslatableText) new TranslatableText(rarity.toString().toUpperCase() + " " + type.toString().toUpperCase().replace("_", " ")).setStyle(NmStyle.getStyle(rarity).withBold(true)));
    }

    protected void addEmptyTooltipLine() {
        addTooltip(new TranslatableText(""));
    }

    protected <T extends NmThrowableWeapon> void addAbilities(T t) {
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

    protected <T extends NmThrowableWeapon> void initializeTooltip(T t) {
        addAbilities(t);
        for (AbilityTooltip abilityTooltip : t.getImplementedAbilities()) {
            addAbilityTooltip(abilityTooltip);
        }
        addRarityTooltip(t.getRarity(), t.getWeaponType());
    }
}
