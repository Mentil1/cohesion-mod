# Publishing checklist

Rules on the download sites change, so read their upload pages when you get there.

## Before the first release

- [ ] Test everything in the game with [testing.md](testing.md).
- [ ] Pick a license, add a `LICENSE` file and change the `license` field in `src/main/resources/fabric.mod.json` (it says `All-Rights-Reserved` for now). Code and textures can have different licenses.
- [ ] Add a mod icon, a square PNG, for example `src/main/resources/assets/cohesion/icon.png`, and add `"icon": "assets/cohesion/icon.png"` to `fabric.mod.json`.
- [ ] Search Modrinth and CurseForge to check that nobody already uses the name Cohesion.
- [ ] Check the Minecraft, Fabric Loader and Fabric API versions in `gradle.properties` and `fabric.mod.json` are the ones you want to support.

## Every release

1. Set the new `version` in `gradle.properties`.
2. Move the entries in [CHANGELOG.md](../CHANGELOG.md) from unreleased to the new version.
3. Run `.\gradlew.bat build`.
4. Take the jar from `build/libs/`, the one without `-sources` in its name (for example `cohesion-0.1.0.jar`).
5. Test that exact jar: put it in the `mods` folder of a Fabric profile together with Fabric API and start the game.

## Download sites

On Modrinth or CurseForge (both are free) you create a project page with a short description, screenshots and the license. Each upload says:

- Minecraft version: 26.3
- Loader: Fabric
- Required dependency: Fabric API

New projects are reviewed before they go public, which can take a few days.

## Also on GitHub

You can attach the jar to a release on the repo's Releases page and paste the changelog entry into it.
