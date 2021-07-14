package net.nm_weapons_pack.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.nm_weapons_pack.NmWeaponsPack;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class NmConfig {
    private static final Path configPath = FabricLoader.getInstance().getConfigDir().normalize().resolve(NmWeaponsPack.MOD_ID);
    private static final Path modConfigPath = Path.of(System.getProperty("user.dir")).getParent().resolve("src/main/resources/data/nm_weapons_pack/config/").normalize();

    public static Map<String, Boolean> enabledWeapons = new HashMap<>();

    public static void initConfig() {
        NmWeaponsPack.debugMsg("Getting config data...");

        if (!configPath.toFile().exists()) {
            // Generate initial config files
            NmWeaponsPack.warnMsg("No config found in config folder!");
            NmWeaponsPack.warnMsg("Generating initial config files...");
            NmWeaponsPack.debugMsg(String.valueOf(configPath));

            // Create config folder
            try {
                Files.createDirectory(configPath);
                NmWeaponsPack.debugMsg("Successfully generated config folder!");
            } catch (IOException e) {
                NmWeaponsPack.warnMsg("An error occurred while generating config folder!");
            }

            // Copy files to config from mod data/config
            File dir = new File(String.valueOf(modConfigPath));
            File[] dirFiles = dir.listFiles();
            if (dirFiles != null) {
                for (File file : dirFiles) {
                    boolean isDirectory = file.isDirectory();
                    copyToConfig(file.getName(), isDirectory);
                }
            } else {
                NmWeaponsPack.warnMsg("Couldn't find mod config directory!");
            }
        }

        // Reading config data from
        // run/config/nm_weapons_pack dir
        readConfigDir("");
    }

    private static void readConfigFile(File file) {
        // Actual reading from json file
        NmWeaponsPack.debugMsg("Reading config data from " + file.getName());
        String fileName = file.getName();
        String fileDir = file.getParentFile().getName();

        if (fileName.endsWith("json5")) {
            NmWeaponsPack.warnMsg("Mod does not allow using json5 as config file format!");
            return ;
        }

        try {
            FileReader json = new FileReader(file);
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(json);
            JsonObject jsonObject = (JsonObject) obj;

            // General files layer
            if (fileName.equals("enabled_weapons.json")) {
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    enabledWeapons.put(entry.getKey(), entry.getValue().getAsBoolean());
                    NmWeaponsPack.debugMsg(entry.getKey() + " " + entry.getValue());
                }
            } else {

                // Weapon categories layer
                if (fileDir.equals("melee_weapons")) {
                    MeleeWeaponsJsonFormat meleeWeaponJson = new Gson().fromJson(json, MeleeWeaponsJsonFormat.class);
                } else if (fileDir.equals("ranged_weapons")) {
                    RangedWeaponsJsonFormat rangedWeaponJson = new Gson().fromJson(json, RangedWeaponsJsonFormat.class);
                } else {
                    NmWeaponsPack.warnMsg("Unknown file in config folder: " + file.getName());
                }
            }

        } catch (FileNotFoundException e) {
            NmWeaponsPack.warnMsg("An error occurred while reading json data from " + file.getName());
        }

    }

    private static void copyToConfig(String filePath, boolean isDirectory) {
        // Copying all files and dirs from mod config
        // to general config folder
        if (isDirectory) {
            try {
                FileUtils.copyDirectory(modConfigPath.resolve(filePath).toFile(), configPath.resolve(filePath).toFile());
            } catch (IOException e) {
                NmWeaponsPack.warnMsg("An error occurred while copying " + filePath + " directory to config location!");
            }
        } else {
            try {
                Files.copy(modConfigPath.resolve(filePath), configPath.resolve(filePath), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                NmWeaponsPack.warnMsg("An error occurred while copying " + filePath + " to config location!");
            }
        }

    }

    private static void readConfigDir(String dirPath) {
        // Recursive function from reading all files/dirs
        // in dirPath subdirectory of config folder
        File dir = new File(String.valueOf(configPath.resolve(dirPath)));
        File[] dirFiles = dir.listFiles();
        if (dirFiles != null) {
            for (File file : dirFiles) {
                if (file.isDirectory()) {
                    readConfigDir(String.valueOf(Path.of(dirPath).resolve(file.getName())));
                } else if (file.isFile()) {
                    readConfigFile(file);
                } else {
                    NmWeaponsPack.warnMsg("Error occurred while opening " + dirPath + " config location!");
                }
            }
        }
    }
}