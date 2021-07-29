package net.nm_weapons_pack.items.weapons.helpers;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.ThrowableWeaponConfigSettings;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmUtils;

public abstract class NmThrowableWeapon extends NmWeapon {
    protected NmWeaponMaterial material;
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
}
