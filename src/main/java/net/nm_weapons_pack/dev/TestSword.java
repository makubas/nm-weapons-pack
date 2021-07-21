package net.nm_weapons_pack.dev;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nm_weapons_pack.abilities.*;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.weapons.types.NmSwordWeapon;
import net.nm_weapons_pack.utils.NmUtils;

public class TestSword extends NmSwordWeapon implements RightClickAbility, PassiveAbility {
    protected static Identifier id = NmUtils.getNmId("test_sword");

    public TestSword() {
        super(NmConfig.getWeaponConfigSettings().get(TestSword.getId()));
        initializeTooltip(this);
    }

    public static Identifier getId() {
        return id;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.heal(6f);
        user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 70);
        return super.use(world, user, hand);
    }

    @Override
    public AbilityTooltip getRightClickAbilityTooltip() {
        return new AbilityTooltip(
                "Test Right Click Ability",
                "Heals you <hp>6HP<hp> and gives you <spe>+100% speed<spe>",
                AbilityType.RIGHT_CLICK,
                rarity,
                70
        );
    }

    @Override
    public AbilityTooltip getPassiveAbilityTooltip() {
        return new AbilityTooltip(
                "Test Passive Ability",
                "Gives you <eff>jump boost 2<eff>",
                AbilityType.PASSIVE,
                rarity,
                0
        );
    }
}
