package net.nm_weapons_pack.items.weapons.helpers;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.nm_weapons_pack.mixins.TridentEntityAccessor;

public class NmThrowableWeaponEntity extends TridentEntity {
    public NmThrowableWeaponEntity(EntityType<? extends NmThrowableWeaponEntity> entityType, World world) {
        super(entityType, world);
    }

    public void setTridentAttributes(World world, LivingEntity owner, ItemStack stack) {
        this.setTridentStack(stack.copy());
        this.dataTracker.set(TridentEntityAccessor.nm$getLoyalty(), (byte) EnchantmentHelper.getLoyalty(stack));
        this.dataTracker.set(TridentEntityAccessor.nm$getEnchanted(), stack.hasGlint());
    }


    protected float getDragInWater() {
        return 0.99f;
    }

    public void setTridentStack(ItemStack tridentStack) {
        ((TridentEntityAccessor) this).nm$setTridentStack(tridentStack);
    }

    public ItemStack getTridentStack() {
        return ((TridentEntityAccessor) this).nm$getTridentStack();
    }

    protected void setDealtDamage(boolean dealtDamage) {
        ((TridentEntityAccessor) this).nm$setDealtDamage(dealtDamage);
    }

    protected boolean hasDealtDamage() {
        return ((TridentEntityAccessor) this).nm$hasDealtDamage();
    }
}
