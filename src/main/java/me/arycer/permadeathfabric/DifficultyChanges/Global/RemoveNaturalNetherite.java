package me.arycer.permadeathfabric.DifficultyChanges.Global;

import me.arycer.permadeathfabric.Main;
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
    private static final RegistryKey<PlacedFeature> largeDebrisKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_ancient_debris_large"));
    private static final RegistryKey<PlacedFeature> smallDebrisKey = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_debris_small"));

    private static void modifyWorldGen() {
        BiomeModification biomeModification = BiomeModifications.create(new Identifier(Main.MOD_ID, "remove_ancient_debris"));
        biomeModification.add(ModificationPhase.REMOVALS, BiomeSelectors.foundInTheNether(), context -> {
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, largeDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, smallDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, largeDebrisKey);
            context.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, smallDebrisKey);
        });
    }

    public static final Identifier BASTION_HOGLIN_STABLE = new Identifier("minecraft", "chests/bastion_hoglin_stable");
    public static final Identifier BASTION_TREASURE = new Identifier("minecraft", "chests/bastion_treasure");
    public static final Identifier BASTION_OTHER = new Identifier("minecraft", "chests/bastion_other");

    private static void modifyLootTables() {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (BASTION_TREASURE.equals(id)) {
                return lootManager.getTable(new Identifier(Main.MOD_ID, "global/bastion_treasure"));
            } else if (id.equals(BASTION_OTHER)) {
                return lootManager.getTable(new Identifier(Main.MOD_ID, "global/bastion_other"));
            } else if (id.equals(BASTION_HOGLIN_STABLE)) {
                return lootManager.getTable(new Identifier(Main.MOD_ID, "global/bastion_hoglin_stable"));
            }
            return null;
        });
    }

    public static void register() {
        modifyLootTables();
        modifyWorldGen();
    }
}
