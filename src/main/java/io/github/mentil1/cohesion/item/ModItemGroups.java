package io.github.mentil1.cohesion.item;

import java.util.List;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;

import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;

import io.github.mentil1.cohesion.Cohesion;
import io.github.mentil1.cohesion.block.ModBlocks;

public class ModItemGroups {
	public static final ResourceKey<CreativeModeTab> COHESION_TAB_KEY = ResourceKey.create(
			BuiltInRegistries.CREATIVE_MODE_TAB.key(), Cohesion.id("cohesion")
	);

	public static final CreativeModeTab COHESION_TAB = FabricCreativeModeTab.builder()
			.icon(() -> new ItemStack(ModBlocks.STONE_TILES))
			.title(Component.translatable("creativeTab.cohesion"))
			.displayItems((params, output) -> {
				for (Block block : ModBlocks.STONE_TILE_SET) {
					output.accept(block);
				}
				for (Block block : ModBlocks.DIORITE_SET) {
					output.accept(block);
				}
			})
			.build();

	public static void initialize() {
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, COHESION_TAB_KEY, COHESION_TAB);

		// The blocks also go into the vanilla Building Blocks tab, next to their vanilla relatives.
		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.BUILDING_BLOCKS).register(tab -> {
			// Stone Tiles sit right after the vanilla stone brick group.
			tab.insertAfter(Items.MOSSY_STONE_BRICK_WALL, items(ModBlocks.STONE_TILE_SET));
			// The diorite blocks sit right after the vanilla diorite group.
			tab.insertAfter(Items.POLISHED_DIORITE_SLAB, items(ModBlocks.DIORITE_SET));
		});
	}

	private static Item[] items(List<Block> blocks) {
		return blocks.stream().map(Block::asItem).toArray(Item[]::new);
	}
}
