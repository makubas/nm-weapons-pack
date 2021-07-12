package net.nm_weapons_pack;

import net.fabricmc.api.ModInitializer;
import net.nm_weapons_pack.config.CONFIG;
import net.nm_weapons_pack.items.NmItems;


public class NmWeaponsPack implements ModInitializer {

	public static final String MOD_ID = "nm_weapons_pack";

	@Override
	public void onInitialize() {
		System.out.println("NM's weapons pack is initialising...");

		// Mod elements initialization
		CONFIG.initConfig();
		NmItems.registerItems();
	}
}
