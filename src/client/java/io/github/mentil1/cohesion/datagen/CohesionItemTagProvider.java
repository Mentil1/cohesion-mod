package io.github.mentil1.cohesion.datagen;

import java.util.concurrent.CompletableFuture;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;

import io.github.mentil1.cohesion.block.ModBlocks;

public class CohesionItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
	// vanilla's stairs and slabs item tags have no constant, so they are made by name
	private static final TagKey<Item> STAIRS = TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace("stairs"));
	private static final TagKey<Item> SLABS = TagKey.create(Registries.ITEM, Identifier.withDefaultNamespace("slabs"));

	public CohesionItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected void addTags(HolderLookup.Provider registries) {
		for (Block block : ModBlocks.ALL) {
			if (block instanceof StairBlock) {
				builder(STAIRS).add(ModBlocks.idOf(block).item());
			} else if (block instanceof SlabBlock) {
				builder(SLABS).add(ModBlocks.idOf(block).item());
			} else if (block instanceof WallBlock) {
				builder(ItemTags.WALLS).add(ModBlocks.idOf(block).item());
			}
		}
	}
}
