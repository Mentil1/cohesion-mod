package io.github.mentil1.cohesion.block;

import java.util.function.Function;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.references.BlockItemId;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;

import io.github.mentil1.cohesion.Cohesion;

public class ModBlocks {
	public static final Block STONE_TILES = register(
			"stone_tiles",
			Block::new,
			BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
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
			tab.accept(STONE_TILES.asItem());
		});
	}
}
