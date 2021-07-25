package net.nm_weapons_pack.dev;

import net.minecraft.util.Identifier;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.MeleeWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.types.BleedingWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWarHammerWeapon;
import net.nm_weapons_pack.utils.NmUtils;

public class TestWarHammer extends NmWarHammerWeapon implements BleedingWeapon {
    public TestWarHammer() {
        super(NmConfig.getWeaponConfigSettings().get(getId()));
        initializeTooltip(this);
    }

    public static Identifier getId() {
        return NmUtils.getNmId("test_war_hammer");
    }

    @Override
    public float getBleedingProbability() {
        return 0.4F;
    }
}
