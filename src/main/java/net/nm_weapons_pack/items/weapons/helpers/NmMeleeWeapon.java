package net.nm_weapons_pack.items.weapons.helpers;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.MeleeWeaponConfigSettings;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmUtils;

public abstract class NmMeleeWeapon extends NmWeapon {
    protected NmWeaponMaterial material;
    protected Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public NmMeleeWeapon(String identifierString) {
        super(new FabricItemSettings()
                .rarity(NmConfig.getMeleeWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getRarity())
                .group(NmItems.NM_WEAPONS_PACK_GROUP)
                .maxCount(1)
                .maxDamageIfAbsent((NmConfig.getMeleeWeaponConfigSettings().get(NmUtils.getNmId(identifierString)).getMaterial().getDurability())));
        MeleeWeaponConfigSettings meleeWeaponConfigSettings = NmConfig.getMeleeWeaponConfigSettings().get(NmUtils.getNmId(identifierString));
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", meleeWeaponConfigSettings.getMaterial().getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", meleeWeaponConfigSettings.getMaterial().getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
        this.identifier = NmUtils.getNmId(identifierString);
        this.attributeModifiers = builder.build();
        this.material = meleeWeaponConfigSettings.getMaterial();
        this.rarity = meleeWeaponConfigSettings.getRarity();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        stack.damage(1, attacker, (e) -> {
            e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
        });
        return true;
    }

    @Override
    public boolean postMine(ItemStack stack, World world, BlockState state, BlockPos pos, LivingEntity miner) {
        if (state.getHardness(world, pos) != 0.0F) {
            stack.damage(2, miner, (e) -> {
                e.sendEquipmentBreakStatus(EquipmentSlot.MAINHAND);
            });
        }
        return true;
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
