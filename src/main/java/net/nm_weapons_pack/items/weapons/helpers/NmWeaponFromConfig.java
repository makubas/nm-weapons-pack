package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.nm_weapons_pack.config.NmConfig;

public class NmWeaponFromConfig extends Item {
    public NmWeaponFromConfig(WeaponConfigSettings weaponConfigSettings) {
        super(new FabricItemSettings().rarity(weaponConfigSettings.getRarity()).group(ItemGroup.COMBAT));
    }

    public NmWeaponFromConfig(Identifier id) {
        super(new FabricItemSettings().rarity(NmConfig.getWeaponConfigSettings().get(id).getRarity()).group(ItemGroup.COMBAT));
    }
}
