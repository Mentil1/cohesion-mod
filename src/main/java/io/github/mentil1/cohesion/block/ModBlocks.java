package io.github.mentil1.cohesion.block;

import java.util.List;
import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import io.github.mentil1.cohesion.Cohesion;

public class ModBlocks {
	public static final Block STONE_TILES = register(
			"stone_tiles",
			Block::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
	);

	public static final Block STONE_TILE_STAIRS = register(
			"stone_tile_stairs",
			props -> new StairBlock(STONE_TILES.defaultBlockState(), props),
			BlockBehaviour.Properties.ofFullCopy(STONE_TILES)
	);

	public static final Block STONE_TILE_SLAB = register(
			"stone_tile_slab",
			SlabBlock::new,
			BlockBehaviour.Properties.ofFullCopy(STONE_TILES)
	);

	public static final Block STONE_TILE_WALL = register(
			"stone_tile_wall",
			WallBlock::new,
			BlockBehaviour.Properties.ofFullCopy(STONE_TILES).forceSolidOn()
	);

	public static final Block DIORITE_BRICKS = register(
			"diorite_bricks",
			Block::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.DIORITE)
	);

	public static final Block DIORITE_BRICK_STAIRS = register(
			"diorite_brick_stairs",
			props -> new StairBlock(DIORITE_BRICKS.defaultBlockState(), props),
			BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS)
	);

	public static final Block DIORITE_BRICK_SLAB = register(
			"diorite_brick_slab",
			SlabBlock::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS)
	);

	public static final Block DIORITE_BRICK_WALL = register(
			"diorite_brick_wall",
			WallBlock::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS).forceSolidOn()
	);

	public static final Block CRACKED_DIORITE_BRICKS = register(
			"cracked_diorite_bricks",
			Block::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS)
	);

	public static final Block MOSSY_DIORITE_BRICKS = register(
			"mossy_diorite_bricks",
			Block::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS)
	);

	public static final Block MOSSY_DIORITE_BRICK_STAIRS = register(
			"mossy_diorite_brick_stairs",
			props -> new StairBlock(MOSSY_DIORITE_BRICKS.defaultBlockState(), props),
			BlockBehaviour.Properties.ofFullCopy(MOSSY_DIORITE_BRICKS)
	);

	public static final Block MOSSY_DIORITE_BRICK_SLAB = register(
			"mossy_diorite_brick_slab",
			SlabBlock::new,
			BlockBehaviour.Properties.ofFullCopy(MOSSY_DIORITE_BRICKS)
	);

	public static final Block MOSSY_DIORITE_BRICK_WALL = register(
			"mossy_diorite_brick_wall",
			WallBlock::new,
			BlockBehaviour.Properties.ofFullCopy(MOSSY_DIORITE_BRICKS).forceSolidOn()
	);

	public static final Block CHISELED_DIORITE = register(
			"chiseled_diorite",
			Block::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS)
	);

	public static final Block DIORITE_TILES = register(
			"diorite_tiles",
			Block::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_BRICKS)
	);

	public static final Block DIORITE_TILE_STAIRS = register(
			"diorite_tile_stairs",
			props -> new StairBlock(DIORITE_TILES.defaultBlockState(), props),
			BlockBehaviour.Properties.ofFullCopy(DIORITE_TILES)
	);

	public static final Block DIORITE_TILE_SLAB = register(
			"diorite_tile_slab",
			SlabBlock::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_TILES)
	);

	public static final Block DIORITE_TILE_WALL = register(
			"diorite_tile_wall",
			WallBlock::new,
			BlockBehaviour.Properties.ofFullCopy(DIORITE_TILES).forceSolidOn()
	);

	public static final List<Block> STONE_TILE_SET = List.of(
			STONE_TILES, STONE_TILE_STAIRS, STONE_TILE_SLAB, STONE_TILE_WALL
	);

	public static final List<Block> DIORITE_SET = List.of(
			DIORITE_BRICKS, CRACKED_DIORITE_BRICKS, MOSSY_DIORITE_BRICKS, CHISELED_DIORITE,
			DIORITE_BRICK_STAIRS, DIORITE_BRICK_SLAB, DIORITE_BRICK_WALL,
			MOSSY_DIORITE_BRICK_STAIRS, MOSSY_DIORITE_BRICK_SLAB, MOSSY_DIORITE_BRICK_WALL,
			DIORITE_TILES, DIORITE_TILE_STAIRS, DIORITE_TILE_SLAB, DIORITE_TILE_WALL
	);

	// Registers the block and a matching block item under the same name.
	private static Block register(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties properties) {
		BlockItemId id = BlockItemId.create(Cohesion.id(name), Cohesion.id(name));

		Block block = factory.apply(properties.setId(id.block()));
		Registry.register(BuiltInRegistries.BLOCK, id.block(), block);

		BlockItem item = new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix().setId(id.item()));
		Registry.register(BuiltInRegistries.ITEM, id.item(), item);

		return block;
	}

	public static void initialize() {
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(tab -> {
			// Stone Tiles sit right after the vanilla stone brick group.
			tab.insertAfter(Items.MOSSY_STONE_BRICK_WALL, items(STONE_TILE_SET));
			// The diorite blocks sit right after the vanilla diorite group.
			tab.insertAfter(Items.POLISHED_DIORITE_SLAB, items(DIORITE_SET));
		});
	}

	private static Item[] items(List<Block> blocks) {
		return blocks.stream().map(Block::asItem).toArray(Item[]::new);
	}
}
