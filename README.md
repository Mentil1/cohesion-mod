# Cohesion

[![build](https://github.com/Mentil1/cohesion-mod/actions/workflows/build.yml/badge.svg)](https://github.com/Mentil1/cohesion-mod/actions/workflows/build.yml)

A Fabric mod for Minecraft that adds new building blocks to improve consistency in blocksets.

## Blocks

| Set | Blocks | How you get it |
|---|---|---|
| Stone Tiles | tiles, stairs, slab, wall | 4 stone bricks, like deepslate tiles. Stonecutter from stone, stone bricks or stone tiles. |
| Diorite Bricks | bricks, stairs, slab, wall | 4 diorite, like stone bricks. Stonecutter from diorite or diorite bricks. |
| Cracked Diorite Bricks | one block | Smelt diorite bricks in a furnace. |
| Mossy Diorite Bricks | bricks, stairs, slab, wall | Diorite bricks with a moss block or a vine. Stonecutter from mossy diorite bricks. |
| Chiseled Diorite | one block | Stack 2 diorite brick slabs. Stonecutter from diorite or diorite bricks. |
| Diorite Tiles | tiles, stairs, slab, wall | 4 diorite bricks, like deepslate tiles. Stonecutter from diorite, diorite bricks or diorite tiles. |

All recipes unlock in the recipe book when you pick up the right item. Every block is in the **Cohesion** creative tab and also sits next to its vanilla relatives in the Building Blocks tab: Stone Tiles right after the Mossy Stone Brick Wall, and the diorite blocks right after the Polished Diorite Slab.

## Recipe changes

Cohesion changes some vanilla crafting recipes. Each change applies to every recipe of that kind, including the copper variants.

- **Stairs:** 3 blocks (top left, middle left and middle of the crafting grid) make 4 stairs. Before, 6 blocks made 4 stairs. This also applies to Cohesion's own stairs.
- **Chains:** iron chains and copper chains now come 8 at a time. Before, you got 1.
- **Trapdoors:** all trapdoors (every wood, iron and copper) now come 6 at a time. Before, wooden trapdoors gave 2 and iron and copper trapdoors gave 1.
- **Doors:** all doors (every wood, iron and copper) now come 2 at a time. Before, you got 3.

Stonecutter recipes are unchanged.

## Requirements

- Minecraft 26.3
- Fabric Loader 0.19.5 or newer
- Fabric API 0.162.0+26.3
- Java 25 (to build or test the mod)

## Try it

1. Install Java 25.
2. Download the project and open a terminal in its folder.
3. Run `.\gradlew.bat runClient` on Windows PowerShell, or `./gradlew runClient` on Mac and Linux.

The full steps, a checklist of what to try in the game and fixes for common errors are in [docs/testing.md](docs/testing.md).

## Documentation

- [docs/testing.md](docs/testing.md): install, run and test the mod
- [docs/adding-blocks.md](docs/adding-blocks.md): add a new block set
- [docs/publishing.md](docs/publishing.md): checklist for releasing the mod
- [CHANGELOG.md](CHANGELOG.md): what changed

## Project layout

| Path | What is in it |
|---|---|
| `src/main/java` | Mod code, including the block list in `ModBlocks` and the creative tabs in `ModItemGroups` |
| `src/client/java` | Client-only code, including the datagen classes in `datagen/` |
| `src/main/resources/assets/cohesion/textures/` | The block textures, the only files drawn by hand |
| `src/main/generated/` | Models, recipes, recipe unlocks, loot tables, tags and names written by datagen. Do not edit these by hand. |
| `src/main/resources/data/minecraft/recipe/` | The changed vanilla recipes: stairs, chains, doors and trapdoors |
| `scripts/` | The resource checker |
| `docs/` | Guides |

## Checks

Every push on GitHub builds the mod and runs two checks:

- **Resource checker** (`python3 scripts/check_resources.py`): every block has its model, texture, recipe, recipe unlock, loot table, name and tags, the changed vanilla recipes (stairs, chains, doors, trapdoors) have the right shape and amounts, and nothing points at a missing file.
- **Datagen check**: the committed files in `src/main/generated` are exactly what `runDatagen` makes.

## License

Not decided yet. All rights reserved for now.
