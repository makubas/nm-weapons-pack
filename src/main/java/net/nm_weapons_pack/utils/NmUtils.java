package net.nm_weapons_pack.utils;

import net.minecraft.util.Identifier;

import static net.nm_weapons_pack.NmWeaponsPack.MOD_ID;

public class NmUtils {
    public static Identifier getNmId(String id) {
        return new Identifier(MOD_ID, id);
    }
}
