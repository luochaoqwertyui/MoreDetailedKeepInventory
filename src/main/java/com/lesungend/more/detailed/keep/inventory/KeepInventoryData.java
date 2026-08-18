package com.lesungend.more.detailed.keep.inventory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class KeepInventoryData {
	private static final Map<UUID, String> PLAYER_MAP = new ConcurrentHashMap<>();
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static final Type MAP_TYPE = new TypeToken<HashMap<UUID, String>>() {}.getType();

	private static Path getDataFile() {
		return FabricLoader.getInstance().getGameDir().resolve("more-detailed-keep-inventory.json");
	}

	public static void load() {
		Path file = getDataFile();
		if (Files.exists(file)) {
			try (Reader reader = Files.newBufferedReader(file)) {
				Map<UUID, String> loaded = GSON.fromJson(reader, MAP_TYPE);
				if (loaded != null) {
					PLAYER_MAP.clear();
					PLAYER_MAP.putAll(loaded);
				}
				MoreDetailedKeepInventory.LOGGER.info("Loaded {} players into keepInventory set.", PLAYER_MAP.size());
			} catch (Exception e) {
				MoreDetailedKeepInventory.LOGGER.error("Failed to load keepInventory data, resetting file.", e);
				try {
					Files.delete(file);
				} catch (IOException ignored) {
				}
			}
		}
	}

	public static void save() {
		Path file = getDataFile();
		try (Writer writer = Files.newBufferedWriter(file)) {
			Map<UUID, String> copy = new HashMap<>(PLAYER_MAP);
			GSON.toJson(copy, writer);
		} catch (IOException e) {
			MoreDetailedKeepInventory.LOGGER.error("Failed to save keepInventory data.", e);
		}
	}

	public static boolean isInSet(UUID uuid) {
		return PLAYER_MAP.containsKey(uuid);
	}

	public static boolean addPlayer(UUID uuid, String playerName) {
		String old = PLAYER_MAP.put(uuid, playerName);
		if (old == null || !old.equals(playerName)) {
			save();
			MoreDetailedKeepInventory.LOGGER.info("Added {} ({}) to keepInventory set.", playerName, uuid);
			return true;
		}
		return false;
	}

	public static boolean removePlayer(UUID uuid, String playerName) {
		String removed = PLAYER_MAP.remove(uuid);
		if (removed != null) {
			save();
			MoreDetailedKeepInventory.LOGGER.info("Removed {} ({}) from keepInventory set.", playerName, uuid);
			return true;
		}
		return false;
	}

	public static Set<UUID> getPlayers() {
		return Collections.unmodifiableSet(PLAYER_MAP.keySet());
	}

	public static String getPlayerName(UUID uuid) {
		return PLAYER_MAP.get(uuid);
	}

	public static int getPlayerCount() {
		return PLAYER_MAP.size();
	}
}