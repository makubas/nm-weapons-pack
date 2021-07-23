package net.nm_weapons_pack.items.weapons.types;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.nm_weapons_pack.NmWeaponsPack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum NmWeaponType {
    WAR_HAMMER(),
    JAVELIN(),
    SPEAR(),
    SHURIKEN(),
    SWORD(Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK, Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    BOW(),
    LONGBOW(),
    DART(),
    MACE(),
    STAFF(),
    DAGGER(),
    BATTLE_AXE(),
    KATAR(),
    DISC(),
    SCYTHE();

    private List<Enchantment> availableEnchantments;

    NmWeaponType(Enchantment... enchantments) {
        this.availableEnchantments = new ArrayList<>();
        if (this.availableEnchantments != null) {
            this.availableEnchantments.addAll(Arrays.asList(enchantments));
        }
    }

    public List<Enchantment> getAvailableEnchantments() {
        return this.availableEnchantments;
    }

    public static NmWeaponType getWeaponType(String id) {
        for (NmWeaponType type : NmWeaponType.values()) {
            if (type.toString().toLowerCase().equals(id)) {
                return type;
            }
        }
        NmWeaponsPack.warnMsg("getWeaponType method did not found any matches for " + id + " weapon type!");
        return null;
    }

    public static boolean isDoubleHanded(NmWeaponType weaponType) {
        if (weaponType == WAR_HAMMER || weaponType == MACE) {
            return true;
        } else {
            return false;
        }
    }
}
