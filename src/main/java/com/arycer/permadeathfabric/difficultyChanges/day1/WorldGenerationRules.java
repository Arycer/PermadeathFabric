package com.arycer.permadeathfabric.difficultyChanges.day1;

import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class WorldGenerationRules {
    public void removeAncientDebris() {
        // Define the keys for the ancient debris features
        RegistryKey<PlacedFeature> largeDebrisKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_ancient_debris_large"));
        RegistryKey<PlacedFeature> smallDebrisKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_debris_small"));

        // Define the biome modification context
        BiomeModification biomeModification = BiomeModifications.create(new Identifier("permadeathfabric", "remove_ancient_debris"));

        // Remove the ancient debris features from the nether
        biomeModification.add(ModificationPhase.REMOVALS, BiomeSelectors.foundInTheNether(), context -> {
            // For some reason the ore features are also registered as underground decoration features so we need to remove them from both
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, largeDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, smallDebrisKey);
            // Remove the ore features from the ore generation step
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, largeDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, smallDebrisKey);
        });
    }
}