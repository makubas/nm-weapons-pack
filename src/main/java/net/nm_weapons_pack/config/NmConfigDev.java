package net.nm_weapons_pack.config;

import net.minecraft.util.Identifier;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import net.nm_weapons_pack.materials.NmMaterials;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class NmConfigDev extends NmConfigBase {
    private static final String modConfigPath = "/data/nm_weapons_pack/config/";

    public static void initConfig() {
        NmWeaponsPack.debugMsg("Getting config data...");


        if (!configPath.toFile().exists()) {
            // Generate initial config files
            NmWeaponsPack.warnMsg("No config found in config folder!");
            NmWeaponsPack.warnMsg("Generating initial config files...");

            // Create config folders for all weapon types
            for (NmWeaponType weaponType : NmWeaponType.values()) {
                createConfigDir(weaponType.toString().toLowerCase().replace(" ", "_"));
            }
            createConfigDir("_materials");

            // Copying and reading weapon registry json
            readConfigFile(Path.of(configPath + "\\weapon_registry.json").toFile());
            copyFileFromConfig(modConfigPath + "weapon_registry.json", configPath + "\\weapon_registry.json");

            // Copying materials
            for (String materialName : NmMaterials.getWeaponMaterialsNames()) {
                copyFileFromConfig(modConfigPath + "_materials" + "/" + materialName + ".json",
                        configPath + "\\" + "_materials" + "\\" + materialName + ".json");
            }

            // Copying and the rest of files
            for (Identifier resourceIdentifier : enabledWeapons.keySet()) {
                String resourceName = resourceIdentifier.toString().replace(resourceIdentifier.getNamespace(), "").replace(":", "");
                String resourceType = weaponTypes.get(resourceIdentifier);
                copyFileFromConfig(modConfigPath + resourceType + "/" + resourceName + ".json",
                        configPath + "\\" + resourceType + "\\" + resourceName + ".json");
            }
        }

        // Reading files in proper order
        readConfigFile(Path.of(configPath + "\\weapon_registry.json").toFile());
        for (String entry : NmMaterials.getWeaponMaterials().keySet()) {
            readConfigFile(Path.of(configPath + "\\" + "_materials" + "\\" + entry + ".json").toFile());
        }
        for (Identifier resourceIdentifier : enabledWeapons.keySet()) {
            String resourceName = resourceIdentifier.toString().replace(resourceIdentifier.getNamespace(), "").replace(":", "");
            String resourceType = weaponTypes.get(resourceIdentifier);
            readConfigFile(Path.of(configPath + "\\" + resourceType + "\\" + resourceName + ".json").toFile());
        }
    }

    private static void copyFileFromConfig(String from, String to) {
        try {
            Files.copy(Path.of(from), Path.of(to));
        } catch (IOException e) {
            NmWeaponsPack.warnMsg("An error occurred while copying " + Path.of(from).toString() + " to " + Path.of(to).toString());
            e.printStackTrace();
        }
    }
}
