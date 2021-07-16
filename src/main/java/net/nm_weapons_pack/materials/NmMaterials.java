package net.nm_weapons_pack.materials;

import java.util.HashMap;
import java.util.Map;

public class NmMaterials {
    private static final Map<String, NmWeaponMaterial> weaponMaterials = new HashMap<>();
    /*
    Example material:
    public static final ToolMaterial TEST_MATERIAL = new TestMaterialClass();
     */


    public static Map<String, NmWeaponMaterial> getWeaponMaterials() {
        return weaponMaterials;
    }
}
