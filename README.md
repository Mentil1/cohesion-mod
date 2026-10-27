# Cohesion

A Fabric mod for Minecraft that adds new building blocks to help your builds look consistent.

## Blocks

Every block comes with matching stairs, a slab and a wall.

- **Stone Tiles**: crafted from 4 stone bricks, the same way as deepslate tiles. In the stonecutter, stone, stone bricks and stone tiles all work.
- **Diorite Bricks**: crafted from 4 diorite, the same way as stone bricks. In the stonecutter, diorite and diorite bricks both work.

All recipes unlock in the recipe book when you pick up the right item. Diorite Bricks sit next to the diorite blocks in the creative inventory, and Stone Tiles are at the end of the Building Blocks tab.

## Requirements

- Minecraft 26.3
- Fabric Loader 0.19.5 or newer
- Fabric API 0.162.0+26.3
- Java 25 (to build the mod)

## Project layout

- `src/main/java`: mod code
- `src/client/java`: client-only code
- `src/main/resources/assets/cohesion/`: textures, models and language files
- `src/main/resources/data/cohesion/`: recipes, loot tables and recipe unlocks

## Building and testing

Run `./gradlew build` (`gradlew.bat build` on Windows) to build the mod. The finished `.jar` ends up in `build/libs/`.

To try it in the game, run `./gradlew runClient`, or use the Minecraft Client run configuration in IntelliJ.

## Recipe changes

Stairs are cheaper to craft. Every stairs crafting recipe, vanilla ones and Cohesion's own, now takes just 3 blocks (top left, middle left and middle of the crafting grid) and makes 4 stairs. Stonecutter recipes are unchanged.

## License

Not decided yet. All rights reserved for now.
