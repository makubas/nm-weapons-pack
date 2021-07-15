package net.nm_weapons_pack.items;

import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.dev.TestSword;
import net.nm_weapons_pack.utils.NmUtils;

public class NmItems {
    // Item group
    public static final ItemGroup NM_WEAPONS_PACK_GROUP = FabricItemGroupBuilder.build(
            NmUtils.getNmId("general"),
            () -> new ItemStack(NmItems.TEST_SWORD));

    /*
    Example item declaration:
    public static final Item TEST_ITEM = new TestItemClass();
     */

    public static final TestSword TEST_SWORD = new TestSword();

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
