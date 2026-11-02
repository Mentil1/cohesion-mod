# Adding a block set

Example: a granite bricks set with stairs, a slab and a wall. Models, names, recipes, recipe unlocks, loot tables and tags are written by datagen, so most of the work is a few lines of code and the texture.

## 1. Register the blocks

In `src/main/java/io/github/mentil1/cohesion/block/ModBlocks.java` add the blocks. The base block copies the settings of a vanilla block and the rest copy the base block:

```java
public static final Block GRANITE_BRICKS = register(
		"granite_bricks",
		Block::new,
		BlockBehaviour.Properties.ofFullCopy(Blocks.GRANITE)
);

public static final Block GRANITE_BRICK_STAIRS = register(
		"granite_brick_stairs",
		props -> new StairBlock(GRANITE_BRICKS.defaultBlockState(), props),
		BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS)
);

public static final Block GRANITE_BRICK_SLAB = register(
		"granite_brick_slab",
		SlabBlock::new,
		BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS)
);

public static final Block GRANITE_BRICK_WALL = register(
		"granite_brick_wall",
		WallBlock::new,
		BlockBehaviour.Properties.ofFullCopy(GRANITE_BRICKS).forceSolidOn()
);
```

Names are lowercase letters, numbers and underscores only.

## 2. Add them to a set list

Put the new constants in a list under the other lists, add that list to `ALL`, and use it in `ModItemGroups` (the Cohesion tab, and the vanilla tab placement):

```java
public static final List<Block> GRANITE_SET = List.of(
		GRANITE_BRICKS, GRANITE_BRICK_STAIRS, GRANITE_BRICK_SLAB, GRANITE_BRICK_WALL
);
```

The resource checker fails if a block is missing from a list.

## 3. Describe them to datagen

All in `src/client/java/io/github/mentil1/cohesion/datagen/`:

- `CohesionModelProvider`: add a `family(...)` line for the base block, stairs, slab and wall. Single blocks use `createTrivialCube`.
- `CohesionRecipeProvider`: add the crafting recipe, `stairsSlabWall(...)` for the stairs, slab and wall recipes, and `cutAll(...)` for the stonecutter recipes.

Names, loot tables and tags come from `ModBlocks.ALL` automatically.

## 4. Add the texture

Put the 16x16 PNG in `src/main/resources/assets/cohesion/textures/block/`. The file name must match the block name, for example `granite_bricks.png`. Stairs, slabs and walls use the base block's texture.

## 5. Generate and check

```
.\gradlew.bat runDatagen
python3 scripts/check_resources.py
```

Commit the new files in `src/main/generated`. Do not edit them by hand.

## 6. Test

Run `.\gradlew.bat runClient` and go through the checklist in [testing.md](testing.md).

## What GitHub checks

Every push builds the mod, runs the resource checker and runs datagen. The datagen check fails if the committed files in `src/main/generated` are not exactly what datagen makes.
