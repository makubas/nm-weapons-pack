package net.nm_weapons_pack.mixins;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    /*
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"))
    public Item attackSwordItemMixin(ItemStack itemStack) {

        if (itemStack.getItem() instanceof NmWeapon) {
            return Items.DIAMOND_SWORD;
        } else {
            return itemStack.getItem();
        }

    }

     */
}
