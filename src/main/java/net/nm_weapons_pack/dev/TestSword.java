package net.nm_weapons_pack.dev;

import net.minecraft.util.Identifier;
import net.nm_weapons_pack.abilities.AbilityRarity;
import net.nm_weapons_pack.abilities.AbilityTooltip;
import net.nm_weapons_pack.abilities.RightClickAbility;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.weapons.types.NmSwordWeapon;
import net.nm_weapons_pack.utils.NmUtils;

public class TestSword extends NmSwordWeapon implements RightClickAbility {
    protected static Identifier id = NmUtils.getNmId("test_sword");

    public TestSword() {
        super(NmConfig.getWeaponConfigSettings().get(TestSword.getId()));
        AbilityTooltip abilityTooltip = new AbilityTooltip(getAbilityName(), getAbilityDescription(), getAbilityType(), getAbilityRarity());
        addAbilityTooltip(abilityTooltip);
    }

    public static Identifier getId() {
        return id;
    }

    @Override
    public String getAbilityName() {
        return "Test Ability";
    }

    @Override
    public String getAbilityDescription() {
        String description = "Heals you <hp>6HP<hp> and gives you <spe>+100% speed<spe>!";
        return description;
    }

    @Override
    public AbilityRarity getAbilityRarity() {
        return AbilityRarity.RARE;
    }
}
