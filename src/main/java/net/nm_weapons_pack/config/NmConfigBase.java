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
import net.nm_weapons_pack.config.json_formats.WeaponJsonFormat;
import net.nm_weapons_pack.config.json_formats.WeaponRegistryJsonFormat;
import net.nm_weapons_pack.config.json_formats.WeaponStatsJsonFormat;
import net.nm_weapons_pack.items.weapons.helpers.WeaponConfigSettings;
import net.nm_weapons_pack.materials.NmWeaponMaterial;
import net.nm_weapons_pack.utils.NmUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class NmConfigBase {
    protected static final Map<Identifier, Boolean> enabledWeapons = new HashMap<>();
    protected static final Map<Identifier, String> weaponTypes = new HashMap<>();
    protected static final Map<Identifier, WeaponConfigSettings> weaponConfigSettings = new HashMap<>();
    protected static final Map<String, NmWeaponMaterial> weaponMaterials = new HashMap<>();

    protected static final Path configPath = FabricLoader.getInstance().getConfigDir().resolve(NmWeaponsPack.MOD_ID);

    protected static void readConfigFile(File file) {
        // Actual reading from json file
        NmWeaponsPack.debugMsg("Reading config data from " + file.getName());
        String fileName = file.getName();
        String fileDir = file.getParentFile().getName();

        if (!fileName.endsWith("json")) {
            NmWeaponsPack.warnMsg("Mod does not allow using other format than '.json' as config!");
            return ;
        }

        try {
            FileReader json = new FileReader(file);
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(json);
            JsonObject jsonObject = (JsonObject) obj;

            // General files layer
            if (fileName.equals("weapon_registry.json")) {
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    WeaponRegistryJsonFormat weaponRegistry = new Gson().fromJson(entry.getValue().getAsJsonObject(), WeaponRegistryJsonFormat.class);
                    Identifier id = NmUtils.getNmId(entry.getKey());
                    enabledWeapons.put(id, weaponRegistry.enabled);
                    weaponTypes.put(id, weaponRegistry.type);
                }
            } else {

                // Weapon categories layer
                if (fileDir.equals("melee_weapons")) {
                    WeaponJsonFormat weaponJson = new Gson().fromJson(jsonObject, WeaponJsonFormat.class);
                    Identifier weaponId = NmUtils.getNmId(fileName.replace(".json", ""));;
                    Rarity weaponRarity = getRarity(weaponJson.rarity);
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

    protected static NmWeaponMaterial getMaterialFromStats(JsonObject stats) {
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

    protected static Rarity getRarity(String rarity) {
        Rarity weaponRarity = switch (rarity) {
            case "epic" -> Rarity.EPIC;
            case "rare" -> Rarity.RARE;
            case "uncommon" -> Rarity.UNCOMMON;
            default -> Rarity.COMMON;
        };
        return weaponRarity;
    }

    protected static void createConfigDir(String dirName) {
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

    public static Map<Identifier, WeaponConfigSettings> getWeaponConfigSettings() {
        return weaponConfigSettings;
    }

    public static Map<String, NmWeaponMaterial> weaponMaterials() {
        return weaponMaterials;
    }
}
