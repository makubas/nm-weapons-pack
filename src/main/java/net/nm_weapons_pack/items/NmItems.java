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
import net.nm_weapons_pack.dev.TestTrident;
import net.nm_weapons_pack.dev.TestWarHammer;
import net.nm_weapons_pack.utils.NmUtils;

public class NmItems {
    public static final ItemGroup NM_WEAPONS_PACK_GROUP = FabricItemGroupBuilder.build(
            NmUtils.getNmId("general"),
            () -> new ItemStack(NmItems.TEST_SWORD));

    // Items variables registration
    public static final TestSword TEST_SWORD = new TestSword();
    public static final TestWarHammer TEST_WAR_HAMMER = new TestWarHammer();
    public static final TestTrident TEST_TRIDENT = new TestTrident();

    // Actual item registry
    public static void registerItems() {
        registerItem(TEST_SWORD.getId(), TEST_SWORD);
        registerItem(TEST_WAR_HAMMER.getId(), TEST_WAR_HAMMER);

        registerItem(TEST_TRIDENT.getId(), TEST_TRIDENT);
    }

    private static void registerItem(Identifier id, Item item) {
        if (NmConfig.getEnabledWeapons().get(id)) {
            Registry.register(Registry.ITEM, id, item);
        } else {
            NmWeaponsPack.warnMsg(id + " is disabled!");
        }
    }
}
