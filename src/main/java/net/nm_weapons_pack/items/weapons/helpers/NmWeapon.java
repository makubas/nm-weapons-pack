package net.nm_weapons_pack.items.weapons.helpers;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.Vanishable;

public abstract class NmWeapon extends Item implements Vanishable {
    public NmWeapon(FabricItemSettings settings) {
        super(settings);
    }
}
