package net.nm_weapons_pack.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.nm_weapons_pack.items.weapons.helpers.NmThrowableWeapon;
import net.nm_weapons_pack.items.weapons.helpers.NmWeapon;
import net.nm_weapons_pack.items.weapons.types.NmSwordWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("RETURN"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof NmWeapon) {
            if (((NmWeapon) stack.getItem()).getWeaponType().getAvailableEnchantments().contains((Object) this)) {
                cir.setReturnValue(true);
            }
        }
    }
}
