package io.github.mentil1.cohesion.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class CohesionDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator generator) {
		FabricDataGenerator.Pack pack = generator.createPack();
		pack.addProvider(CohesionModelProvider::new);
		pack.addProvider(CohesionLanguageProvider::new);
		pack.addProvider(CohesionBlockLootProvider::new);
		pack.addProvider(CohesionBlockTagProvider::new);
		pack.addProvider(CohesionItemTagProvider::new);
		pack.addProvider(CohesionRecipeProvider::new);
	}
}
