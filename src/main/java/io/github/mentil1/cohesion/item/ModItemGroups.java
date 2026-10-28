package io.github.mentil1.cohesion.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

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
	}
}
