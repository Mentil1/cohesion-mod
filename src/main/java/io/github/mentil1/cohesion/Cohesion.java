package io.github.mentil1.cohesion;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Cohesion implements ModInitializer {
	public static final String MOD_ID = "cohesion";

	// Using the mod id as the logger name makes it clear which mod wrote a message.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		// Runs as soon as Minecraft is ready for mods to load.
		// Block and item registration will go here.
		LOGGER.info("Cohesion loaded.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
