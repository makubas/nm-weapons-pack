package net.nm_weapons_pack.config;

import net.minecraft.util.Identifier;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import net.nm_weapons_pack.materials.NmMaterials;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
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

            // Copying weapon registry json
            copyFileFromJar(modConfigPath + "weapon_registry.json", configPath + "\\weapon_registry.json");

            // Copying materials
            for (String entry : NmMaterials.getWeaponMaterials().keySet()) {
                copyFileFromJar(modConfigPath + "_materials" + "/" + entry + ".json",
                        configPath + "\\" + "_materials" + "\\" + entry + ".json");
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
    private static boolean copyFileFromJar(String string, String destination) {
        boolean succeess = true;
        InputStream source = NmConfig.class.getResourceAsStream(string);
        NmWeaponsPack.debugMsg("Copying -> " + source + " to -> " + destination);

        try {
            Files.copy(source, Paths.get(destination), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            NmWeaponsPack.warnMsg("Error while copying file: " + ex.toString());
            succeess = false;
        }
        return succeess;
    }

    private static String getBasePathForClass(Class<?> classs) {
        File file;
        String basePath = "";
        boolean failed = false;

        try {
            file = new File(classs.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            if (file.isFile() || file.getPath().endsWith(".jar") || file.getPath().endsWith(".zip")) {
                basePath = file.getParent();
            } else {
                basePath = file.getPath();
            }
        } catch (URISyntaxException ex) {
            failed = true;
            NmWeaponsPack.warnMsg("Cannot figure out base path for class with way (1): " + ex);
        }

        if (failed) {
            try {
                file = new File(classs.getClassLoader().getResource("").toURI().getPath());
                basePath = file.getAbsolutePath();
            } catch (URISyntaxException ex) {
                NmWeaponsPack.warnMsg("Cannot figure out base path for class with way (2): " + ex);
            }
        }

        if (basePath.endsWith(File.separator + "lib") || basePath.endsWith(File.separator + "bin")
                || basePath.endsWith("bin" + File.separator) || basePath.endsWith("lib" + File.separator)) {
            basePath = basePath.substring(0, basePath.length() - 4);
        }
        if (basePath.endsWith(File.separator + "build" + File.separator + "classes")) {
            basePath = basePath.substring(0, basePath.length() - 14);
        }
        if (!basePath.endsWith(File.separator)) {
            basePath = basePath + File.separator;
        }
        return basePath;
    }
}
