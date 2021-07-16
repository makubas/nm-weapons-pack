package net.nm_weapons_pack.config;

import net.minecraft.util.Identifier;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import net.nm_weapons_pack.materials.NmMaterials;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class NmConfigRelease extends NmConfigBase {
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
            copyFileFromJar(modConfigPath + "weapon_registry.json", configPath + "\\weapon_registry.json");
            readConfigFile(Path.of(configPath + "\\weapon_registry.json").toFile());

            // Copying materials
            for (String materialName : NmMaterials.getWeaponMaterialsNames()) {
                copyFileFromJar(modConfigPath + "_materials" + "/" + materialName + ".json",
                        configPath + "\\" + "_materials" + "\\" + materialName + ".json");
            }

            // Copying and the rest of files
            for (Identifier resourceIdentifier : enabledWeapons.keySet()) {
                String resourceName = resourceIdentifier.toString().replace(resourceIdentifier.getNamespace(), "").replace(":", "");
                String resourceType = weaponTypes.get(resourceIdentifier);
                copyFileFromJar(modConfigPath + resourceType + "/" + resourceName + ".json",
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

    // Jar operations
    private static void copyFileFromJar(String string, String destination) {
        InputStream source = NmConfig.class.getResourceAsStream(string);
        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            NmWeaponsPack.warnMsg("Error while copying file: " + ex.toString());
        }
    }
}
