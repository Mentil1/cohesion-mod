package io.github.mentil1.cohesion.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import io.github.mentil1.cohesion.block.ModBlocks;

public class CohesionBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
	public CohesionBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		for (Block block : ModBlocks.ALL) {
			builder(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.idOf(block).block());
			if (block instanceof StairBlock) {
				builder(BlockTags.STAIRS).add(ModBlocks.idOf(block).block());
			} else if (block instanceof SlabBlock) {
				builder(BlockTags.SLABS).add(ModBlocks.idOf(block).block());
			} else if (block instanceof WallBlock) {
				builder(BlockTags.WALLS).add(ModBlocks.idOf(block).block());
			}
		}
	}
}
