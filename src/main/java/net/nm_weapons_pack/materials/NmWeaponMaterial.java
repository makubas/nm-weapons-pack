package net.nm_weapons_pack.materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class NmWeaponMaterial implements ToolMaterial {

    // Class will be created to store data about weapon stats in config
    private final String name;
    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final double attackSpeed;
    private final Ingredient repairIngredient;

    public NmWeaponMaterial(String name, int miningLevel, int itemDurability, float miningSpeed, float attackDamage, int enchantability, double attackSpeed, Ingredient repairIngredient) {
        this.name = name;
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attackDamage;
        this.enchantability = enchantability;
        this.attackSpeed = attackSpeed;
        this.repairIngredient = repairIngredient;
    }

    public String getName() {
        return name;
    }

    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    public double getAttackSpeed() {
        return this.attackSpeed;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient;
    }
}
