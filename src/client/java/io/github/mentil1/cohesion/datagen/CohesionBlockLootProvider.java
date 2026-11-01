package io.github.mentil1.cohesion.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;

import io.github.mentil1.cohesion.block.ModBlocks;

public class CohesionBlockLootProvider extends FabricBlockLootSubProvider {
	public CohesionBlockLootProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	public void generate() {
		for (Block block : ModBlocks.ALL) {
			if (block instanceof SlabBlock) {
				// a double slab drops two slabs
				add(block, createSlabItemTable(block));
			} else {
				dropSelf(block);
			}
		}
	}
}
