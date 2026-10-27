# Cohesion

A Fabric mod for Minecraft that adds new building blocks to help your builds look consistent.

## Blocks

- Stone Tiles, with matching stairs, slab and wall

Stone Tiles are crafted from 4 stone bricks, or cut from stone, stone bricks or stone tiles in the stonecutter, the same way as deepslate tiles.

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
