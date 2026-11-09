package io.github.mentil1.cohesion.datagen;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.data.recipes.SingleItemRecipeBuilder;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBlock;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;

import io.github.mentil1.cohesion.Cohesion;
import io.github.mentil1.cohesion.block.ModBlocks;

public class CohesionRecipeProvider extends FabricRecipeProvider {
	public CohesionRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
		super(output, registries);
	}

	@Override
	protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, BootstrapContext<Recipe<?>> recipes, BootstrapContext<Advancement> advancements) {
		return new RecipeProvider(recipes, advancements) {
			@Override
			public void buildRecipes() {
				// Stone Tiles: 4 stone bricks, or the stonecutter from stone, stone bricks and tiles
				craft(ModBlocks.STONE_TILES, 4, Items.STONE_BRICKS, Items.STONE_BRICKS, "##", "##");
				stairsSlabWall(ModBlocks.STONE_TILES, ModBlocks.STONE_TILE_STAIRS, ModBlocks.STONE_TILE_SLAB, ModBlocks.STONE_TILE_WALL);
				cutAll(Items.STONE, ModBlocks.STONE_TILES, ModBlocks.STONE_TILE_STAIRS, ModBlocks.STONE_TILE_SLAB, ModBlocks.STONE_TILE_WALL);
				cutAll(Items.STONE_BRICKS, ModBlocks.STONE_TILES, ModBlocks.STONE_TILE_STAIRS, ModBlocks.STONE_TILE_SLAB, ModBlocks.STONE_TILE_WALL);

				// Diorite Bricks: 4 diorite, like stone bricks
				craft(ModBlocks.DIORITE_BRICKS, 4, Items.DIORITE, Items.DIORITE, "##", "##");
				stairsSlabWall(ModBlocks.DIORITE_BRICKS, ModBlocks.DIORITE_BRICK_STAIRS, ModBlocks.DIORITE_BRICK_SLAB, ModBlocks.DIORITE_BRICK_WALL);
				cutAll(Items.DIORITE, ModBlocks.DIORITE_BRICKS, ModBlocks.DIORITE_BRICK_STAIRS, ModBlocks.DIORITE_BRICK_SLAB, ModBlocks.DIORITE_BRICK_WALL);

				// Diorite Tiles: 4 diorite bricks, like deepslate tiles
				craft(ModBlocks.DIORITE_TILES, 4, ModBlocks.DIORITE_BRICKS, ModBlocks.DIORITE_BRICKS, "##", "##");
				stairsSlabWall(ModBlocks.DIORITE_TILES, ModBlocks.DIORITE_TILE_STAIRS, ModBlocks.DIORITE_TILE_SLAB, ModBlocks.DIORITE_TILE_WALL);
				cutAll(Items.DIORITE, ModBlocks.DIORITE_TILES, ModBlocks.DIORITE_TILE_STAIRS, ModBlocks.DIORITE_TILE_SLAB, ModBlocks.DIORITE_TILE_WALL);
				cutAll(ModBlocks.DIORITE_BRICKS, ModBlocks.DIORITE_TILES, ModBlocks.DIORITE_TILE_STAIRS, ModBlocks.DIORITE_TILE_SLAB, ModBlocks.DIORITE_TILE_WALL);

				// Mossy Diorite Bricks: diorite bricks with a moss block or a vine
				for (ItemLike moss : List.of(Items.MOSS_BLOCK, Items.VINE)) {
					shapeless(RecipeCategory.BUILDING_BLOCKS, ModBlocks.MOSSY_DIORITE_BRICKS)
							.requires(ModBlocks.DIORITE_BRICKS)
							.requires(moss)
							.group("mossy_diorite_bricks")
							.unlockedBy(getHasName(moss), has(moss))
							.save(this.output, key("mossy_diorite_bricks_from_" + path(moss)));
				}
				stairsSlabWall(ModBlocks.MOSSY_DIORITE_BRICKS, ModBlocks.MOSSY_DIORITE_BRICK_STAIRS, ModBlocks.MOSSY_DIORITE_BRICK_SLAB, ModBlocks.MOSSY_DIORITE_BRICK_WALL);

				// Cracked Diorite Bricks: smelt diorite bricks, like cracked stone bricks
				SimpleCookingRecipeBuilder.smelting(Ingredient.of(ModBlocks.DIORITE_BRICKS), RecipeCategory.BUILDING_BLOCKS, CookingBookCategory.BLOCKS, ModBlocks.CRACKED_DIORITE_BRICKS, 0.1F, 200)
						.unlockedBy(getHasName(ModBlocks.DIORITE_BRICKS), has(ModBlocks.DIORITE_BRICKS))
						.save(this.output, key("cracked_diorite_bricks"));

				// Chiseled Diorite: two diorite brick slabs, or the stonecutter
				craft(ModBlocks.CHISELED_DIORITE, 1, ModBlocks.DIORITE_BRICK_SLAB, ModBlocks.DIORITE_BRICKS, "#", "#");
				cut(ModBlocks.DIORITE_BRICKS, ModBlocks.CHISELED_DIORITE, 1);
				cut(Items.DIORITE, ModBlocks.CHISELED_DIORITE, 1);

				// Polished diorite works in the stonecutter too, like polished deepslate
				cutAll(Items.POLISHED_DIORITE, ModBlocks.DIORITE_BRICKS, ModBlocks.DIORITE_BRICK_STAIRS, ModBlocks.DIORITE_BRICK_SLAB, ModBlocks.DIORITE_BRICK_WALL);
				cutAll(Items.POLISHED_DIORITE, ModBlocks.DIORITE_TILES, ModBlocks.DIORITE_TILE_STAIRS, ModBlocks.DIORITE_TILE_SLAB, ModBlocks.DIORITE_TILE_WALL);
				cut(Items.POLISHED_DIORITE, ModBlocks.CHISELED_DIORITE, 1);

				// Polished Diorite Wall: 6 polished diorite, or the stonecutter from diorite or polished diorite
				craft(ModBlocks.POLISHED_DIORITE_WALL, 6, Items.POLISHED_DIORITE, Items.POLISHED_DIORITE, "###", "###");
				cut(Items.DIORITE, ModBlocks.POLISHED_DIORITE_WALL, 1);
				cut(Items.POLISHED_DIORITE, ModBlocks.POLISHED_DIORITE_WALL, 1);
			}

			// Stairs take 3 blocks for 4 stairs, slabs 3 for 6, walls 6 for 6. The stonecutter gives 1 stairs, 2 slabs or 1 wall.
			private void stairsSlabWall(Block base, Block stairs, Block slab, Block wall) {
				craft(stairs, 4, base, base, "# ", "##");
				craft(slab, 6, base, base, "###");
				craft(wall, 6, base, base, "###", "###");
				cut(base, stairs, 1);
				cut(base, slab, 2);
				cut(base, wall, 1);
			}

			private void cutAll(ItemLike source, Block base, Block stairs, Block slab, Block wall) {
				cut(source, base, 1);
				cut(source, stairs, 1);
				cut(source, slab, 2);
				cut(source, wall, 1);
			}

			private void craft(Block result, int count, ItemLike ingredient, ItemLike unlock, String... rows) {
				var recipe = shaped(category(result), result, count).define('#', ingredient);
				for (String row : rows) {
					recipe.pattern(row);
				}
				recipe.unlockedBy(getHasName(unlock), has(unlock)).save(this.output, key(path(result)));
			}

			private void cut(ItemLike source, Block result, int count) {
				SingleItemRecipeBuilder.stonecutting(Ingredient.of(source), category(result), result, count)
						.unlockedBy(getHasName(source), has(source))
						.save(this.output, key(path(result) + "_from_" + path(source) + "_stonecutting"));
			}

			// walls count as decorations, everything else as building blocks
			private RecipeCategory category(Block block) {
				return block instanceof WallBlock ? RecipeCategory.DECORATIONS : RecipeCategory.BUILDING_BLOCKS;
			}

			private ResourceKey<Recipe<?>> key(String name) {
				return ResourceKey.create(Registries.RECIPE, Cohesion.id(name));
			}

			private String path(ItemLike item) {
				return BuiltInRegistries.ITEM.getKey(item.asItem()).getPath();
			}
		};
	}

	@Override
	public String getName() {
		return "Cohesion recipes";
	}
}
