package net.nm_weapons_pack.config;

import com.google.gson.Gson;
import net.fabricmc.loader.api.FabricLoader;
import net.nm_weapons_pack.NmWeaponsPack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class CONFIG {
    private static final Path configPath = FabricLoader.getInstance().getConfigDir().normalize().resolve(NmWeaponsPack.MOD_ID);
    private static final Path userDir = Path.of(System.getProperty("user.dir")).getParent();

    public static void initConfig() {
        NmWeaponsPack.debugMsg("Getting config data...");

        if (!configPath.toFile().exists()) {
            // Generate initial config files
            NmWeaponsPack.warnMsg("Generating initial config files...");
            NmWeaponsPack.debugMsg(String.valueOf(configPath ));

            // Create config folder
            try {
                Files.createDirectories(configPath);
                NmWeaponsPack.debugMsg("Successfully generated config folder!");
            } catch (IOException e) {
                NmWeaponsPack.warnMsg("An error occurred while generating config folder!");
                e.printStackTrace();
            }

            // Copy files to config from mod data/config
            //Path modInitConfigPath = userDir.resolve("src/main/resources/data/nm_weapons_pack/config/").normalize();
            //copyToConfig(modInitConfigPath.resolve("check_file.json"));
        }

        try {
            FileReader json = new FileReader(configPath.resolve("hammer.json").toFile());
            MeleeWeaponsJsonFormat meleeWeaponsJson = new Gson().fromJson(json, MeleeWeaponsJsonFormat.class);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private static void copyToConfig(Path path) {
        try {
            Files.copy(path, configPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            NmWeaponsPack.warnMsg("An error occurred while copying " + path.toFile().getName() + " to config location!");
        }
    }

    private static String jsonFormat(String fileName) {
        return fileName + ".json";
    }
}