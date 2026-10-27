# Cohesion

A Fabric mod for Minecraft that adds new building blocks to help your builds look consistent.

## Blocks

Stone Tiles, Diorite Bricks, Mossy Diorite Bricks and Diorite Tiles each come with matching stairs, a slab and a wall. Cracked Diorite Bricks and Chiseled Diorite are single blocks.

- **Stone Tiles**: crafted from 4 stone bricks, the same way as deepslate tiles. In the stonecutter, stone, stone bricks and stone tiles all work.
- **Diorite Bricks**: crafted from 4 diorite, the same way as stone bricks. In the stonecutter, diorite and diorite bricks both work.
- **Cracked Diorite Bricks**: smelt diorite bricks in a furnace.
- **Mossy Diorite Bricks**: craft diorite bricks with a moss block or a vine. In the stonecutter, mossy diorite bricks give the stairs, slab and wall.
- **Chiseled Diorite**: stack two diorite brick slabs. In the stonecutter, diorite and diorite bricks both work.
- **Diorite Tiles**: crafted from 4 diorite bricks, the same way as deepslate tiles. In the stonecutter, diorite, diorite bricks and diorite tiles all work.

All recipes unlock in the recipe book when you pick up the right item. The diorite blocks sit next to the vanilla diorite in the creative inventory, and Stone Tiles are at the end of the Building Blocks tab.

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
