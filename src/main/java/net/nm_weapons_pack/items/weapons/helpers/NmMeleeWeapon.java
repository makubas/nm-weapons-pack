package net.nm_weapons_pack.items.weapons.helpers;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.MeleeWeaponConfigSettings;
import net.nm_weapons_pack.materials.NmWeaponMaterial;

public abstract class NmMeleeWeapon extends NmWeapon {
    protected NmWeaponMaterial material;
    protected Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public NmMeleeWeapon(MeleeWeaponConfigSettings meleeWeaponConfigSettings) {
        super(new FabricItemSettings().rarity(meleeWeaponConfigSettings.getRarity()).group(NmItems.NM_WEAPONS_PACK_GROUP).maxCount(1).maxDamageIfAbsent(meleeWeaponConfigSettings.getMaterial().getDurability()));
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", (double) meleeWeaponConfigSettings.getMaterial().getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", (double) meleeWeaponConfigSettings.getMaterial().getAttackSpeed(), EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
        this.material = meleeWeaponConfigSettings.getMaterial();
        this.rarity = meleeWeaponConfigSettings.getRarity();
    }

    public NmWeaponMaterial getMaterial() {
        return this.material;
    }

    @Override
    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
        return !miner.isCreative();
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
