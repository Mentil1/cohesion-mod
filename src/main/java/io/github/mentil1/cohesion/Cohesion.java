package io.github.mentil1.cohesion;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.github.mentil1.cohesion.block.ModBlocks;

public class Cohesion implements ModInitializer {
	public static final String MOD_ID = "cohesion";

	// Using the mod id as the logger name makes it clear which mod wrote a message.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.initialize();
		LOGGER.info("Cohesion loaded.");
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
