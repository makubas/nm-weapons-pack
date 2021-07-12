package net.nm_weapons_pack.config;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.nio.file.Path;

public class CONFIG {
    // Default values - change that if you'll fuck up config.json
    public static float testValue = 1.0f;

    public static void initConfig() {
        JsonParser parser = new JsonParser();
        Path userDir = Path.of(System.getProperty("user.dir")).getParent();
        System.out.println("Getting config data...");

        try {
            Object obj = parser.parse(new FileReader(userDir + "/src/main/java/net/nm_weapons_pack/config/config.json"));
            JsonObject jsonObject = (JsonObject) obj;

            // Reading data from config.json
            testValue = jsonObject.get("testValue").getAsFloat();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}