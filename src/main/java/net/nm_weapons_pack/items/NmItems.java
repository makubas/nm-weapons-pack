package net.nm_weapons_pack.items;

import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import static net.nm_weapons_pack.NmWeaponsPack.MOD_ID;

public class NmItems {
    /*
    Example item declaration:
    public static final Item TEST_ITEM = new TestItemClass();
     */

    // Items registration
    public static void registerItems() {
        /*
        Example item registration:
        registerItem("test_item", TEST_ITEM)
         */
        ;
    }



    private void registerItem(String id, Item item) {
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, id), item);
    }
}
