package net.nm_weapons_pack;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.nm_weapons_pack.config.NmConfig;
import net.nm_weapons_pack.items.NmItems;
import net.nm_weapons_pack.materials.NmMaterials;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class NmWeaponsPack implements ModInitializer {

	public static final String MOD_ID = "nm_weapons_pack";
	public static final Logger logger = LogManager.getLogger("NmWeaponsPack");

	@Override
	public void onInitialize() {
		logger.info("NM's weapons pack is initialising...");

		// Things only for testing purposes
		if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
			debugMsg("######################################################");
			debugMsg("# WARNING: NM's Weapons Pack is running in dev mode! #");
			debugMsg("######################################################");
		}

		// Mod elements initialization
		NmMaterials.registerMaterials();
		NmConfig.initConfig();
		NmItems.registerItems();

		logger.info("NM's Weapons Pack successfully initialized");
	}

	public static void debugMsg(String msg) {
		logger.warn("[DEBUG] " + msg);
	}

	public static void warnMsg(String msg) {
		logger.error("[WARN] " + msg);
	}
}
