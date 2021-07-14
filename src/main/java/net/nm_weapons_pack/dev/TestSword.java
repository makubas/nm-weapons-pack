package net.nm_weapons_pack.dev;

import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.nm_weapons_pack.items.weapons.helpers.NmSwordWeapon;
import net.nm_weapons_pack.items.weapons.helpers.WeaponConfigSettings;
import net.nm_weapons_pack.utils.NmUtils;

public class TestSword extends NmSwordWeapon {
    protected static Identifier id = NmUtils.getNmId("test_sword");

    public TestSword(WeaponConfigSettings weaponConfigSettings) {
        super(weaponConfigSettings);
    }

    public TestSword(Identifier id) {
        super(id);
    }

    public static Identifier getId() {
        return id;
    }

    public static String getIdString() {
        return id.toString().replace("nm_weapons_pack:", "");
    }

    @Override
    public Text getName() {
        if (MinecraftClient.getInstance().getLanguageManager().getLanguage().getCode().equals("en_us")) {
            return Text.of("Test Sword");
        } else {
            return super.getName();
        }
    }
}
