package net.nm_weapons_pack.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.nm_weapons_pack.items.weapons.helpers.NmThrowableWeapon;
import net.nm_weapons_pack.items.weapons.types.NmSwordWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("RETURN"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (((Object) this == Enchantments.PIERCING || (Object) this == Enchantments.IMPALING || (Object) this == Enchantments.LOYALTY) && stack.getItem() instanceof NmThrowableWeapon) {
            cir.setReturnValue(true);
        } else if (((Object) this == Enchantments.BANE_OF_ARTHROPODS || (Object) this == Enchantments.EFFICIENCY || (Object) this == Enchantments.FIRE_ASPECT || (Object) this == Enchantments.LOOTING || (Object) this == Enchantments.KNOCKBACK || (Object) this == Enchantments.SHARPNESS || (Object) this == Enchantments.SMITE || (Object) this == Enchantments.SWEEPING) && stack.getItem() instanceof NmSwordWeapon) {
            cir.setReturnValue(true);
        }
    }
}
