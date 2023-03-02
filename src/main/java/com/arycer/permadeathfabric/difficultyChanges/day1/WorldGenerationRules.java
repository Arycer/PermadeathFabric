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
        RegistryKey<PlacedFeature> largeDebrisKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_ancient_debris_large"));
        RegistryKey<PlacedFeature> smallDebrisKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_debris_small"));

        BiomeModification biomeModification = BiomeModifications.create(new Identifier("permadeathfabric", "remove_ancient_debris"));

        biomeModification.add(ModificationPhase.REMOVALS, BiomeSelectors.foundInTheNether(), context -> {
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, largeDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, smallDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, largeDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, smallDebrisKey);
        });
    }
}