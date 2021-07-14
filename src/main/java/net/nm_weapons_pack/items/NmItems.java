package net.nm_weapons_pack.items;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.dev.TestSword;

public class NmItems {
    /*
    Example item declaration:
    public static final Item TEST_ITEM = new TestItemClass();
     */

    public static final Item TEST_SWORD = new TestSword(NmConfig.getWeaponConfigSettings().get(TestSword.getId()));

    // Items registration
    public static void registerItems() {
        /*
        Example item registration:
        registerItem("test_item", TEST_ITEM)
         */

        registerItem(TestSword.getId(), TEST_SWORD);
    }



    private static void registerItem(Identifier id, Item item) {
        if (NmConfig.getEnabledWeapons().get(id)) {
            Registry.register(Registry.ITEM, id, item);
        } else {
            NmWeaponsPack.warnMsg(id + " is disabled!");
        }
    }
}
