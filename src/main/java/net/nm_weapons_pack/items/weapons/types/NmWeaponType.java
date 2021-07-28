package net.nm_weapons_pack.items.weapons.types;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.nm_weapons_pack.NmWeaponsPack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum NmWeaponType {
    SWORD(Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    WAR_HAMMER(Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    BATTLE_AXE(Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING, Enchantments.EFFICIENCY),
    MACE(Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    SCYTHE(Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    DAGGER(Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING,
            Enchantments.SHARPNESS, Enchantments.SMITE),

    BOW(),
    LONGBOW(),
    BLOWGUN(),
    DISC(),

    JAVELIN(),
    SPEAR(),
    SHURIKEN(),

    STAFF(),
    WAND();

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

    public boolean isDoubleHanded() {
        return this == WAR_HAMMER || this == MACE || this == BATTLE_AXE;
    }

    public static String getAttackMethod(String weaponTypeString) {
        NmWeaponType weaponType = getWeaponType(weaponTypeString);
        if (weaponType == WAR_HAMMER || weaponType == MACE || weaponType == BATTLE_AXE || weaponType == SCYTHE || weaponType == SWORD) {
            return "melee";
        } else if (weaponType == BOW || weaponType == LONGBOW || weaponType == BLOWGUN || weaponType == DISC) {
            return "ranged";
        } else if (weaponType == SPEAR || weaponType == SHURIKEN || weaponType == JAVELIN) {
            return "throwable";
        } else if (weaponType == WAND || weaponType == STAFF) {
            return "magic";
        } else {
            return null;
        }
    }
}
