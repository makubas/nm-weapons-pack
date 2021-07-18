package net.nm_weapons_pack.dev;

import net.minecraft.util.Identifier;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.weapons.types.NmSwordWeapon;
import net.nm_weapons_pack.utils.NmUtils;

public class TestSword extends NmSwordWeapon {
    protected static Identifier id = NmUtils.getNmId("test_sword");

    public TestSword() {
        super(NmConfig.getWeaponConfigSettings().get(TestSword.getId()));
    }

    public static Identifier getId() {
        return id;
    }
}
