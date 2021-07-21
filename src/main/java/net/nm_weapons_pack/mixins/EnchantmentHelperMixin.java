package net.nm_weapons_pack.mixins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.ItemStack;
import net.nm_weapons_pack.items.weapons.helpers.NmWeapon;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {
    @Inject(method = "getPossibleEntries(ILnet/minecraft/item/ItemStack;Z)Ljava/util/List;", at = @At("RETURN"), cancellable = true)
    private static void getPossibleEntries(int i, ItemStack stack, boolean treasureAllowed, CallbackInfoReturnable<List<EnchantmentLevelEntry>> info) {
        if (stack.getItem() instanceof NmWeapon) {
            info.setReturnValue(addEnchantments(info.getReturnValue(), (NmWeapon) stack.getItem() , i));
        }
    }

    private static <T extends NmWeapon> List<EnchantmentLevelEntry> addEnchantments(List<EnchantmentLevelEntry> entryList, T weapon, int i) {
        for (Enchantment enchantment : weapon.getWeaponType().getAvailableEnchantments()) {
            for (int level = enchantment.getMaxLevel(); level > enchantment.getMinLevel() - 1; --level) {
                if (i >= enchantment.getMinPower(level) && i <= enchantment.getMaxPower(level)) {
                    entryList.add(new EnchantmentLevelEntry(enchantment, level));
                }
            }
        }
        return entryList;
    }
}
