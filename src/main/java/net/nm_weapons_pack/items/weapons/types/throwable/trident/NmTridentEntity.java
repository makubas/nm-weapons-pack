package net.nm_weapons_pack.items.weapons.types.throwable.trident;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.nm_weapons_pack.items.weapons.helpers.NmThrowableWeaponEntity;
import net.nm_weapons_pack.mixins.TridentEntityAccessor;
import org.jetbrains.annotations.Nullable;

public class NmTridentEntity extends NmThrowableWeaponEntity {
    public NmTridentEntity(EntityType<? extends NmThrowableWeaponEntity> entityType, World world) {
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
