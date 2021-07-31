package net.nm_weapons_pack.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public class EnchantmentMixin {
    @Inject(method = "isAcceptableItem", at = @At("RETURN"), cancellable = true)
    public void isAcceptableItem(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
    }
}
