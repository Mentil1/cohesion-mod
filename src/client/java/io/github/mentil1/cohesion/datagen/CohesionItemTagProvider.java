package io.github.mentil1.cohesion.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import io.github.mentil1.cohesion.block.ModBlocks;

public class CohesionItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	public CohesionItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		for (Block block : ModBlocks.ALL) {
			if (block instanceof StairBlock) {
				builder(ItemTags.STAIRS).add(ModBlocks.idOf(block).item());
			} else if (block instanceof SlabBlock) {
				builder(ItemTags.SLABS).add(ModBlocks.idOf(block).item());
			} else if (block instanceof WallBlock) {
				builder(ItemTags.WALLS).add(ModBlocks.idOf(block).item());
			}
		}
	}
}
