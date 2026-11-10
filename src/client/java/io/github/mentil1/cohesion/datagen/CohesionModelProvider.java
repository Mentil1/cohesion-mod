package io.github.mentil1.cohesion.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;

import io.github.mentil1.cohesion.block.ModBlocks;

public class CohesionModelProvider extends FabricModelProvider {
	public CohesionModelProvider(FabricPackOutput output) {
		super(output);
	}

	@Override
	public void generateBlockStateModels(BlockModelGenerators generator) {
		family(generator, ModBlocks.STONE_TILES, ModBlocks.STONE_TILE_STAIRS, ModBlocks.STONE_TILE_SLAB, ModBlocks.STONE_TILE_WALL);
		family(generator, ModBlocks.DIORITE_BRICKS, ModBlocks.DIORITE_BRICK_STAIRS, ModBlocks.DIORITE_BRICK_SLAB, ModBlocks.DIORITE_BRICK_WALL);
		family(generator, ModBlocks.MOSSY_DIORITE_BRICKS, ModBlocks.MOSSY_DIORITE_BRICK_STAIRS, ModBlocks.MOSSY_DIORITE_BRICK_SLAB, ModBlocks.MOSSY_DIORITE_BRICK_WALL);
		family(generator, ModBlocks.DIORITE_TILES, ModBlocks.DIORITE_TILE_STAIRS, ModBlocks.DIORITE_TILE_SLAB, ModBlocks.DIORITE_TILE_WALL);
		// the wall uses the texture of vanilla polished diorite, so nothing new is drawn
		generator.new BlockFamilyProvider(TextureMapping.cube(Blocks.POLISHED_DIORITE)).wall(ModBlocks.POLISHED_DIORITE_WALL);
		generator.createTrivialCube(ModBlocks.CRACKED_DIORITE_BRICKS);
		generator.createTrivialCube(ModBlocks.CHISELED_DIORITE);
	}

	// The base block's texture is used for its stairs, slab and wall too.
	private static void family(BlockModelGenerators generator, Block base, Block stairs, Block slab, Block wall) {
		generator.family(base).stairs(stairs).slab(slab).wall(wall);
	}

	@Override
	public void generateItemModels(ItemModelGenerators generator) {
	}
}
