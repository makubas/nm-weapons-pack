package net.nm_weapons_pack.items.weapons.types;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.nm_weapons_pack.NmWeaponsPack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum NmWeaponType {
    SWORD(NmAttackMethod.MELEE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    WAR_HAMMER(NmAttackMethod.MELEE, true, Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    BATTLE_AXE(NmAttackMethod.MELEE, true, Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING, Enchantments.EFFICIENCY),
    MACE(NmAttackMethod.MELEE, true, Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    SCYTHE(NmAttackMethod.MELEE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING, Enchantments.KNOCKBACK,
            Enchantments.SHARPNESS, Enchantments.SMITE, Enchantments.SWEEPING),
    DAGGER(NmAttackMethod.MELEE, Enchantments.BANE_OF_ARTHROPODS, Enchantments.FIRE_ASPECT, Enchantments.LOOTING,
            Enchantments.SHARPNESS, Enchantments.SMITE),

    BOW(NmAttackMethod.RANGED),
    LONGBOW(NmAttackMethod.RANGED),
    BLOWGUN(NmAttackMethod.RANGED),
    DISC(NmAttackMethod.RANGED),

    TRIDENT(NmAttackMethod.THROWABLE),
    JAVELIN(NmAttackMethod.THROWABLE),
    SPEAR(NmAttackMethod.THROWABLE),
    SHURIKEN(NmAttackMethod.THROWABLE),

    STAFF(NmAttackMethod.MAGIC),
    WAND(NmAttackMethod.MAGIC);

    private final List<Enchantment> availableEnchantments;
    private final NmAttackMethod attackMethod;
    private final boolean doubleHanded;

    NmWeaponType(NmAttackMethod attackMethod, Enchantment... enchantments) {
        this.doubleHanded = false;
        this.attackMethod = attackMethod;
        this.availableEnchantments = new ArrayList<>();
        this.availableEnchantments.addAll(Arrays.asList(enchantments));
    }

    NmWeaponType(NmAttackMethod attackMethod, boolean doubleHanded, Enchantment... enchantments) {
        this.attackMethod = attackMethod;
        this.doubleHanded = doubleHanded;
        this.availableEnchantments = new ArrayList<>();
        this.availableEnchantments.addAll(Arrays.asList(enchantments));
    }


    public List<Enchantment> getAvailableEnchantments() {
        return this.availableEnchantments;
    }

    public NmAttackMethod getAttackMethod() {
        return attackMethod;
    }

    public boolean isDoubleHanded() {
        return this.doubleHanded;
    }

    public static NmWeaponType getWeaponType(String id) {
        for (NmWeaponType type : NmWeaponType.values()) {
            if (type.toString().toLowerCase().equals(id)) {
                return type;
            }
        }
        return null;
    }

    public static String getAttackMethod(String weaponTypeString) {
        NmWeaponType weaponType = getWeaponType(weaponTypeString);
        if (weaponType != null) {
            return weaponType.getAttackMethod().toString();
        } else {
            return null;
        }
    }
}
