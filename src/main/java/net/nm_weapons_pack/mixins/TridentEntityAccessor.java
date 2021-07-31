package net.nm_weapons_pack.mixins;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TridentEntity.class)
public interface TridentEntityAccessor {
    @Accessor("tridentStack")
    ItemStack nm$getTridentStack();

    @Accessor("tridentStack")
    void nm$setTridentStack(ItemStack stack);

    @Accessor("dealtDamage")
    boolean nm$hasDealtDamage();

    @Accessor("dealtDamage")
    void nm$setDealtDamage(boolean dealtDamage);

    @Accessor("LOYALTY")
    static TrackedData<Byte> nm$getLoyalty() {
        return null;
    }

    @Accessor("ENCHANTED")
    static TrackedData<Boolean> nm$getEnchanted() {
        return null;
    }
}
