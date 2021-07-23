package net.nm_weapons_pack.dev;

import net.minecraft.util.Identifier;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.MeleeWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.types.BleedingWeapon;
import net.nm_weapons_pack.items.weapons.types.NmWarHammerWeapon;
import net.nm_weapons_pack.utils.NmUtils;

public class TestWarHammer extends NmWarHammerWeapon implements BleedingWeapon {
    protected static Identifier id = NmUtils.getNmId("test_war_hammer");

    public TestWarHammer(MeleeWeaponConfigSettings meleeWeaponConfigSettings) {
        super(meleeWeaponConfigSettings);
    }

    public static Identifier getId() {
        return id;
    }
}
