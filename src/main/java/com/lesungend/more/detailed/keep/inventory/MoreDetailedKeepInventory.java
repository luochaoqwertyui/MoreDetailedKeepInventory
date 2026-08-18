package com.lesungend.more.detailed.keep.inventory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoreDetailedKeepInventory implements ModInitializer {
	public static final String MOD_ID = "more-detailed-keep-inventory";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("More Detailed Keep Inventory is initializing...");

		KeepInventoryData.load();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			KeepInventoryCommand.register(dispatcher);
		});

		LOGGER.info("More Detailed Keep Inventory initialized successfully!");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}