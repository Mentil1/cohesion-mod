package io.github.mentil1.cohesion.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

import io.github.mentil1.cohesion.block.ModBlocks;

public class CohesionLanguageProvider extends FabricLanguageProvider {
	public CohesionLanguageProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, "en_us", registries);
	}

	@Override
	public void generateTranslations(HolderLookup.Provider registries, TranslationBuilder builder) {
		for (Block block : ModBlocks.ALL) {
			builder.add(block, title(block));
		}
		builder.add("creativeTab.cohesion", "Cohesion");
	}

	// stone_tile_stairs -> Stone Tile Stairs
	private static String title(Block block) {
		StringBuilder name = new StringBuilder();
		for (String word : BuiltInRegistries.BLOCK.getKey(block).getPath().split("_")) {
			if (name.length() > 0) {
				name.append(' ');
			}
			name.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
		}
		return name.toString();
	}
}
