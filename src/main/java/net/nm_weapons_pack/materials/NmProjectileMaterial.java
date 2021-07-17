package net.nm_weapons_pack.materials;

import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class NmProjectileMaterial {

    // Class will be created to store data about projectile stats in config
    private final String name;
    private final float projectileDamage;
    private final double projectileSpeed;

    public NmProjectileMaterial(String name, float projectileDamage, double projectileSpeed) {
        this.name = name;
        this.projectileDamage = projectileDamage;
        this.projectileSpeed = projectileSpeed;
    }

    public String getName() {
        return name;
    }

    public float getProjectileDamage() {
        return this.projectileDamage;
    }

    public double getProjectileSpeed() {
        return this.projectileSpeed;
    }
}
