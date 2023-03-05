package com.arycer.permadeathfabric.difficultyChanges.day1;

import com.arycer.permadeathfabric.Initializer;
import net.fabricmc.fabric.api.biome.v1.BiomeModification;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class RemoveNaturalNetherite {
    private static void modifyWorldgen() {
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

    public static final Identifier BASTION_TREASURE = new Identifier("minecraft", "chests/bastion_treasure");
    public static final Identifier BASTION_OTHER = new Identifier("minecraft", "chests/bastion_other");
    public static final Identifier BASTION_HOGLIN_STABLE = new Identifier("minecraft", "chests/bastion_hoglin_stable");

    private static void modifyLootTables() {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BASTION_TREASURE.equals(id)) {
                return lootManager.getTable(new Identifier(Initializer.MOD_ID, "chests/bastion_treasure"));
            } else if (id.equals(BASTION_OTHER)) {
                return lootManager.getTable(new Identifier(Initializer.MOD_ID, "chests/bastion_other"));
            } else if (id.equals(BASTION_HOGLIN_STABLE)) {
                return lootManager.getTable(new Identifier(Initializer.MOD_ID, "chests/bastion_hoglin_stable"));
            }
            return null;
        });
    }

    public void register() {
        modifyWorldgen();
        modifyLootTables();
    }
}