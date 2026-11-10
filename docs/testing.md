# Testing the mod

How to try Cohesion in the real game on your own computer. The first time takes 30 to 60 minutes, mostly waiting for downloads. You need internet and about 5 GB of free space. You do not need to log in to Minecraft: the test game starts with a test player.

## 1. Install Java 25

**Windows:** open PowerShell and run:

```
winget install Microsoft.OpenJDK.25
```

If `winget` is not available, download the Windows x64 installer for Microsoft Build of OpenJDK 25 from <https://learn.microsoft.com/java/openjdk/download> and make sure **Add to PATH** and **Set JAVA_HOME variable** are ticked in the installer.

Close PowerShell and open it again, then check:

```
java -version
```

The first line should mention **25**.

**Mac and Linux:** install any Java 25 JDK, for example Microsoft Build of OpenJDK or Eclipse Temurin.

## 2. Get the mod files

- **No Git:** on the GitHub page click **Code**, then **Download ZIP**, and unzip it.
- **With Git:** `git clone https://github.com/Mentil1/cohesion-mod.git`

Open the folder until you can see `build.gradle`, `gradlew.bat` and a folder called `src`. Unzipping sometimes puts the project in a folder inside a folder.

## 3. Open a terminal in that folder

**Windows:** in File Explorer, click the address bar at the top, delete the text, type `powershell` and press Enter. PowerShell opens in the right folder.

**Mac and Linux:** open a terminal in the folder.

## 4. Start the game

**Windows (PowerShell):**

```
.\gradlew.bat runClient
```

The `.\` at the start is needed in PowerShell.

**Mac and Linux:**

```
chmod +x gradlew
./gradlew runClient
```

The first run downloads Minecraft and the build tools, so lots of text scrolls past and it takes several minutes. Do not close the window. A Minecraft window opens by itself at the end.

## 5. What to check

Make a new world in **Creative** mode with **Allow Commands** turned on.

**Creative menu**

- [ ] The **Cohesion** tab exists, has a Stone Tiles icon and holds 19 blocks.
- [ ] In the **Building Blocks** tab the Stone Tiles set comes right after the Mossy Stone Brick Wall.
- [ ] In the **Building Blocks** tab the diorite blocks come right after the Polished Diorite Slab.

**Blocks**

- [ ] No block is purple and black (that means a missing texture).
- [ ] Stairs curve at corners, slabs can sit on the top or bottom, and walls connect to each other.
- [ ] In survival (`/gamemode survival`) a block drops nothing when broken by hand and drops itself with a pickaxe.
- [ ] A double slab drops 2 slabs.

**Crafting** (use `/give @s minecraft:diorite 64` and `/give @s minecraft:stone_bricks 64`)

- [ ] 4 stone bricks in a 2x2 square make 4 Stone Tiles.
- [ ] 4 diorite make 4 Diorite Bricks, and 4 Diorite Bricks make 4 Diorite Tiles.
- [ ] 6 polished diorite in two rows of 3 make 6 Polished Diorite Walls, and they look like polished diorite.
- [ ] 3 blocks in a small L (top left, middle left, middle) make 4 stairs. Try a Cohesion block and a vanilla one such as oak planks.
- [ ] 3 blocks in a row make 6 slabs, and 6 blocks in two rows of 3 make 6 walls.
- [ ] An iron ingot between two iron nuggets (in a column) makes 8 iron chains.
- [ ] 6 planks in two rows of 3 make 6 trapdoors, and 4 iron ingots in a 2x2 square make 6 iron trapdoors.
- [ ] 6 planks in two columns of 3 make 2 doors, and 6 iron ingots in two columns of 3 make 2 iron doors.
- [ ] 2 Diorite Brick Slabs stacked make Chiseled Diorite.
- [ ] Diorite Bricks plus a moss block or a vine make Mossy Diorite Bricks.
- [ ] Smelting Diorite Bricks in a furnace gives Cracked Diorite Bricks.

**Stonecutter and recipe book**

- [ ] A stonecutter turns stone, stone bricks and stone tiles into the Stone Tiles set.
- [ ] A stonecutter turns diorite, polished diorite and diorite bricks into the diorite sets, and diorite or polished diorite into the Polished Diorite Wall.
- [ ] New recipes appear in the recipe book once you hold the right item.

## 6. If something goes wrong

| What you see | What to do |
|---|---|
| `java` is not recognized | Java 25 is not installed, or the terminal has not picked it up. Install it (step 1), then close and reopen PowerShell. If that does not help, restart the computer. |
| `gradlew.bat` is not recognized | You are in the wrong folder, or the `.\` is missing. Run `dir`: the list must show `gradlew.bat` and `build.gradle`. If not, use `cd` to go into the right folder. |
| A download stops with `Read timed out` | The download server was slow. Run the same command again. |
| `BUILD FAILED` with red text | Copy the last 20 lines or take a screenshot and report it. |
| The game crashes | Send `run/logs/latest.log` and any file in `run/crash-reports/`. |
| A block is purple and black | The texture is missing or misnamed. Check `src/main/resources/assets/cohesion/textures/block/`. |

## 7. After the mod changes

Download the ZIP again (or run `git pull`) and repeat step 4. The big downloads are saved, so it is much faster the second time.

## Optional: IntelliJ IDEA

You do not need it to test. If you want an editor, install the free IntelliJ IDEA, open the project folder and wait for the Gradle sync to finish. It creates a **Minecraft Client** run configuration for starting the game.

## Other commands

| Command | What it does |
|---|---|
| `.\gradlew.bat build` | Builds the mod. The jar ends up in `build/libs/`. |
| `.\gradlew.bat runDatagen` | Regenerates the files in `src/main/generated`. |
| `python3 scripts/check_resources.py` | Checks that blocks, models, textures, recipes, unlocks, loot tables and tags line up. |

On Mac and Linux use `./gradlew` instead of `.\gradlew.bat`.
