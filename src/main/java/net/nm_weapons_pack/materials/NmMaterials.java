package net.nm_weapons_pack.materials;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NmMaterials {
    private static final List<String> weaponMaterialsNames = new ArrayList<>();
    private static final Map<String, NmWeaponMaterial> weaponMaterials = new HashMap<>();
    private static final List<String> projectileMaterialsNames = new ArrayList<>();
    private static final Map<String, NmProjectileMaterial> projectileMaterials = new HashMap<>();

    public static void registerMaterials() {
        weaponMaterialsNames.add("test_material");
    }

    public static Map<String, NmWeaponMaterial> getWeaponMaterials() {
        return weaponMaterials;
    }

    public static List<String> getWeaponMaterialsNames() {
        return weaponMaterialsNames;
    }

    public static Map<String, NmProjectileMaterial> getProjectileMaterials() {
        return projectileMaterials;
    }

    public static List<String> getProjectileMaterialsNames() {
        return projectileMaterialsNames;
    }
}
