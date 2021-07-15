package net.nm_weapons_pack.dev;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.ClickType;
import net.minecraft.util.Identifier;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.weapons.types.NmSwordWeapon;
import net.nm_weapons_pack.utils.NmUtils;

public class TestSword extends NmSwordWeapon {
    protected static Identifier id = NmUtils.getNmId("test_sword");

    public TestSword() {
        super(NmConfig.getWeaponConfigSettings().get(TestSword.getId()));
    }

    @Override
    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        NmWeaponsPack.warnMsg(this.getMaterial().getName() + " " + this.getMaterial().getAttackDamage());
        return super.onStackClicked(stack, slot, clickType, player);
    }

    public static Identifier getId() {
        return id;
    }
}
