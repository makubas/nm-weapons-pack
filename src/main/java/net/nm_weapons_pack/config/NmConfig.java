package net.nm_weapons_pack.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.items.weapons.helpers.WeaponConfigSettings;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NmConfig {
    private static final Path configPath = FabricLoader.getInstance().getConfigDir().normalize().resolve(NmWeaponsPack.MOD_ID);
    private static final Path modConfigPath = Path.of(System.getProperty("user.dir")).getParent().resolve("src/main/resources/data/nm_weapons_pack/config/").normalize();

    private static final Map<Identifier, Boolean> enabledWeapons = new HashMap<>();
    private static final Map<Identifier, WeaponConfigSettings> weaponConfigSettings = new HashMap<>();
    private static final Map<String, NmWeaponMaterial> weaponMaterials = new HashMap<>();

    public static void initConfig() {
        NmWeaponsPack.debugMsg("Getting config data...");

        if (!configPath.toFile().exists()) {
            // Generate initial config files
            NmWeaponsPack.warnMsg("No config found in config folder!");
            NmWeaponsPack.warnMsg("Generating initial config files...");

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

        /* Reading config data (recursively)
        1. Getting materials from materials/*.json
        2. Getting all other files *.json because it might contain reference to materials
         */
        readConfigMaterials();
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
                    if (entry.getValue().getAsString().equals("enabled")) {
                        enabledWeapons.put(NmUtils.getNmId(entry.getKey()), true);
                    } else {
                        enabledWeapons.put(NmUtils.getNmId(entry.getKey()), false);
                    }
                }
            } else {

                // Weapon categories layer
                if (fileDir.equals("melee_weapons")) {
                    WeaponJsonFormat weaponJson = new Gson().fromJson(jsonObject, WeaponJsonFormat.class);
                    Identifier weaponId = NmUtils.getNmId(fileName.replace(".json", ""));;
                    Rarity weaponRarity = switch (weaponJson.rarity) {
                        case "epic" -> Rarity.EPIC;
                        case "rare" -> Rarity.RARE;
                        case "uncommon" -> Rarity.UNCOMMON;
                        default -> Rarity.COMMON;
                    };
                    if (weaponJson.material != null) {
                        // Getting material stats from other json / vanilla material
                        NmWeaponMaterial material = weaponMaterials.get(weaponJson.material);
                        WeaponConfigSettings configSettings = new WeaponConfigSettings(weaponRarity, material);
                        weaponConfigSettings.put(weaponId, configSettings);
                    } else {
                        if (weaponJson.stats != null) {
                            // Generating material from stats
                            NmWeaponMaterial material = getMaterialFromStats(weaponJson.stats);
                            WeaponConfigSettings configSettings = new WeaponConfigSettings(weaponRarity, material);
                            weaponConfigSettings.put(weaponId, configSettings);
                        } else {
                            enabledWeapons.replace(weaponId, false);
                            NmWeaponsPack.warnMsg(fileName + " in config folder is corrupted!");
                        }
                    }

                } else if (fileDir.equals("ranged_weapons")) {
                    //RangedWeaponsJsonFormat rangedWeaponJson = new Gson().fromJson(json, RangedWeaponsJsonFormat.class);

                } else if (fileDir.equals("materials")) {
                    NmWeaponMaterial material = getMaterialFromStats(jsonObject);
                    weaponMaterials.put(fileName.replace(".json", ""), material);

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
                if (file.isDirectory() && !file.getName().equals("materials")) {
                    readConfigDir(String.valueOf(Path.of(dirPath).resolve(file.getName())));
                } else if (file.isFile()) {
                    readConfigFile(file);
                } else if (!file.getName().equals("materials")) {
                    NmWeaponsPack.warnMsg("Error occurred while opening " + dirPath + " config location!");
                }
            }
        }
    }

    private static void readConfigMaterials() {
        // Putting all materials to weaponMaterials map
        File dir = new File(String.valueOf(configPath.resolve("materials")));
        File[] dirFiles = dir.listFiles();
        if (dirFiles != null) {
            for (File file : dirFiles) {
                if (file.isFile()) {
                    readConfigFile(file);
                }
            }
        }
    }

    private static NmWeaponMaterial getMaterialFromStats(JsonObject stats) {
        // Getting WeaponMaterial from json stats
        WeaponStatsJsonFormat statsJson = new Gson().fromJson(stats, WeaponStatsJsonFormat.class);
        List<Item> ingredientSupplier = new ArrayList<Item>();
        for (int i = 0; i < statsJson.repairIngredient.getAsJsonArray().size(); i++) {
             Item item = Registry.ITEM.get(Identifier.tryParse(statsJson.repairIngredient.getAsJsonArray().get(i).getAsString()));
             ingredientSupplier.add(item);
        }
        ItemConvertible[] itemConvertibles = new ItemConvertible[ingredientSupplier.size()];
        Ingredient ingredient =  Ingredient.ofItems(ingredientSupplier.toArray(itemConvertibles));

        return new NmWeaponMaterial(
                statsJson.name,
                statsJson.miningLevel,
                statsJson.itemDurability,
                statsJson.miningSpeed,
                statsJson.attackDamage,
                statsJson.enchantability,
                statsJson.attackSpeed,
                ingredient
        );
    }

    public static Map<Identifier, Boolean> getEnabledWeapons() {
        return enabledWeapons;
    }

    public static Map<Identifier, WeaponConfigSettings> getWeaponConfigSettings() {
        return weaponConfigSettings;
    }

    public static Map<String, NmWeaponMaterial> weaponMaterials() {
        return weaponMaterials;
    }
}