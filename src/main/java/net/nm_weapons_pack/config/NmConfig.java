package net.nm_weapons_pack.config;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;
import net.nm_weapons_pack.NmWeaponsPack;
import net.nm_weapons_pack.config.json_formats.*;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.MagicWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.MeleeWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.RangedWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.helpers.config_settings.ThrowableWeaponConfigSettings;
import net.nm_weapons_pack.items.weapons.types.NmWeaponType;
import net.nm_weapons_pack.materials.NmMaterials;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NmConfig {
    private static final Map<Identifier, Boolean> enabledWeapons = new HashMap<>();
    private static final Map<Identifier, String> weaponTypes = new HashMap<>();
    private static final Map<Identifier, MeleeWeaponConfigSettings> meleeWeaponConfigSettings = new HashMap<>();
    private static final Map<Identifier, RangedWeaponConfigSettings> rangedWeaponConfigSettings = new HashMap<>();
    private static final Map<Identifier, ThrowableWeaponConfigSettings> throwableWeaponConfigSettings = new HashMap<>();
    private static final Map<Identifier, MagicWeaponConfigSettings> magicWeaponConfigSettings = new HashMap<>();
    private static final Map<String, NmWeaponMaterial> weaponMaterials = new HashMap<>();
    private static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve(NmWeaponsPack.MOD_ID);

    public static void initConfig() {
        NmWeaponsPack.debugMsg("Getting config data...");

        String modConfigPath;
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            modConfigPath = Path.of(System.getProperty("user.dir")).getParent().resolve("src/main/resources/data/nm_weapons_pack/config/") + "\\";
        } else {
            modConfigPath = "/data/nm_weapons_pack/config/";
        }

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
            copyFileFromConfig(modConfigPath + "weapon_registry.json", configPath + "\\weapon_registry.json");
            readConfigFile(Path.of(configPath + "\\weapon_registry.json").toFile());

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
        for (String entry : NmMaterials.getWeaponMaterialsNames()) {
            readConfigFile(Path.of(configPath + "\\" + "_materials" + "\\" + entry + ".json").toFile());
        }
        for (Identifier resourceIdentifier : enabledWeapons.keySet()) {
            String resourceName = resourceIdentifier.toString().replace(resourceIdentifier.getNamespace(), "").replace(":", "");
            String resourceType = weaponTypes.get(resourceIdentifier);
            readConfigFile(Path.of(configPath + "\\" + resourceType + "\\" + resourceName + ".json").toFile());
        }
    }

    private static void readConfigFile(File file) {
        // Actual reading from json file
        NmWeaponsPack.debugMsg("Reading config data from " + file.getName());
        String fileName = file.getName();
        String fileDir = file.getParentFile().getName();

        if (!fileName.endsWith("json")) {
            NmWeaponsPack.warnMsg("Mod does not allow using other format than '.json' as config!");
            return ;
        }

        try {
            JsonObject jsonObject = (JsonObject) new JsonParser().parse(new FileReader(file));

            // General files layer
            if (fileName.equals("weapon_registry.json")) {
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    NmWeaponRegistryJsonFormat weaponRegistry = new Gson().fromJson(entry.getValue().getAsJsonObject(), NmWeaponRegistryJsonFormat.class);
                    Identifier id = NmUtils.getNmId(entry.getKey());
                    enabledWeapons.put(id, weaponRegistry.enabled);
                    weaponTypes.put(id, weaponRegistry.type);
                }
            } else {
                if (fileDir.equals("_materials")) {
                    weaponMaterials.put(fileName.replace(".json", ""), getMaterialFromStats(jsonObject));
                } else {
                    switch (NmWeaponType.getAttackMethod(fileDir)) {
                        case "melee" -> readMelee(jsonObject, file);
                        case "ranged" -> readRanged(jsonObject, file);
                        case "throwable" -> readThrowable(jsonObject, file);
                        case "magic" -> readMagic(jsonObject, file);
                        default -> NmWeaponsPack.warnMsg("Unknown file in config folder: " + fileName);
                    }
                }
            }

        } catch (FileNotFoundException e) {
            NmWeaponsPack.warnMsg("An error occurred while reading json data from " + fileName);
        }
    }

    private static void readMelee(JsonObject jsonObject, File file) {
        NmMeleeWeaponJsonFormat weaponJson = new Gson().fromJson(jsonObject, NmMeleeWeaponJsonFormat.class);
        Identifier weaponId = NmUtils.getNmId(file.getName().replace(".json", ""));
        Rarity weaponRarity = getRarity(weaponJson.rarity);
        NmWeaponMaterial material = getMaterialOrStats(weaponJson);
        if (material == null) {
            enabledWeapons.replace(weaponId, false);
            NmWeaponsPack.warnMsg(file.getName() + " in config folder is corrupted!");
        }
        MeleeWeaponConfigSettings configSettings = new MeleeWeaponConfigSettings(weaponRarity, material);
        meleeWeaponConfigSettings.put(weaponId, configSettings);
    }

    private static void readRanged(JsonObject jsonObject, File file) {

    }

    private static void readThrowable(JsonObject jsonObject, File file) {
        NmThrowableWeaponJsonFormat weaponJson = new Gson().fromJson(jsonObject, NmThrowableWeaponJsonFormat.class);
        Identifier weaponId = NmUtils.getNmId(file.getName().replace(".json", ""));
        Rarity weaponRarity = getRarity(weaponJson.rarity);
        int stackCount = weaponJson.stackCount;
        int maxUseTime = weaponJson.maxUseTime;
        NmWeaponMaterial material = getMaterialOrStats(weaponJson);
        if (material == null) {
            enabledWeapons.replace(weaponId, false);
            NmWeaponsPack.warnMsg(file.getName() + " in config folder is corrupted!");
        }
        ThrowableWeaponConfigSettings configSettings = new ThrowableWeaponConfigSettings(weaponRarity, material, stackCount, maxUseTime);
        throwableWeaponConfigSettings.put(weaponId, configSettings);
    }

    private static void readMagic(JsonObject jsonObject, File file) {

    }

    private static <T extends NmWeaponJsonFormat> NmWeaponMaterial getMaterialOrStats(T weaponJson) {
        if (weaponJson.material != null) {
            return weaponMaterials.get(weaponJson.material);
        } else if (weaponJson.stats != null) {
            return getMaterialFromStats(weaponJson.stats);
        } else {
            return null;
        }
    }

    private static NmWeaponMaterial getMaterialFromStats(JsonObject stats) {
        // Getting WeaponMaterial from json stats
        NmStatsJsonFormat statsJson = new Gson().fromJson(stats, NmStatsJsonFormat.class);
        List<Item> ingredientSupplier = new ArrayList<>();
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

    private static Rarity getRarity(String rarity) {
        return switch (rarity) {
            case "epic" -> Rarity.EPIC;
            case "rare" -> Rarity.RARE;
            case "uncommon" -> Rarity.UNCOMMON;
            default -> Rarity.COMMON;
        };
    }

    private static void copyFileFromConfig(String from, String to) {
        if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
            try {
                Files.copy(Path.of(from), Path.of(to), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                NmWeaponsPack.warnMsg("An error occurred while copying " + Path.of(from).toString() + " to " + Path.of(to).toString());
            }
        } else {
            try {
                InputStream source = NmConfig.class.getResourceAsStream(from);
                if (source != null) {
                    Files.copy(source, Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
                }
            } catch (IOException e) {
                NmWeaponsPack.warnMsg("Error while copying file: " + e);
            }
        }
    }

    private static void createConfigDir(String dirName) {
        try {
            if (!configPath.toFile().exists()) {
                Files.createDirectory(configPath);
            }
            Files.createDirectory(configPath.resolve(dirName));
            NmWeaponsPack.debugMsg("Successfully generated " + dirName + " in config folder");
        } catch (IOException e) {
            NmWeaponsPack.warnMsg("Error while creating " + dirName + " in " + configPath);
        }
    }

    public static Map<Identifier, Boolean> getEnabledWeapons() {
        return enabledWeapons;
    }

    public static Map<Identifier, MeleeWeaponConfigSettings> getMeleeWeaponConfigSettings() {
        return meleeWeaponConfigSettings;
    }

    public static Map<Identifier, RangedWeaponConfigSettings> getRangedWeaponConfigSettings() {
        return rangedWeaponConfigSettings;
    }

    public static Map<Identifier, ThrowableWeaponConfigSettings> getThrowableWeaponConfigSettings() {
        return throwableWeaponConfigSettings;
    }

    public static Map<Identifier, MagicWeaponConfigSettings> getMagicWeaponConfigSettings() {
        return magicWeaponConfigSettings;
    }

    public static Map<String, NmWeaponMaterial> weaponMaterials() {
        return weaponMaterials;
    }
}
