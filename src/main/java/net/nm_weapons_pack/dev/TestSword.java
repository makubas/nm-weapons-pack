package net.nm_weapons_pack.dev;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.nm_weapons_pack.abilities.*;
import net.nm_weapons_pack.abilities.implemented.ShockWeapon;
import net.nm_weapons_pack.items.weapons.types.melee.NmSword;

import java.util.ArrayList;
import java.util.List;

public class TestSword extends NmSword implements RightClickAbility, PassiveAbility, ShockWeapon {
    public TestSword() {
        super("test_sword");
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.heal(6f);
        user.getItemCooldownManager().set(user.getStackInHand(hand).getItem(), 70);
        return super.use(world, user, hand);
    }

    @Override
    public List<AbilityTooltip> getImplementedAbilities() {
        return new ArrayList<>() {{
            add(ShockWeapon.getTooltip());
        }};
    }

    @Override
    public AbilityTooltip getRightClickAbilityTooltip() {
        return new AbilityTooltip(
                "Test Right Click Ability",
                "Heals you <hp>6HP<hp> and giv",
                AbilityType.RIGHT_CLICK,
                70
        );
    }

    @Override
    public AbilityTooltip getPassiveAbilityTooltip() {
        return new AbilityTooltip(
                "Test Passive Ability",
                "Gives you <eff>jump boost 2<eff>ttttt",
                AbilityType.PASSIVE,
                0
        );
    }

    @Override
    public float getShockProbability() {
        return 0.5F;
    }
}
