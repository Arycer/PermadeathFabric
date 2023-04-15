package me.arycer.permadeath.DifficultyChanges.Global;

import me.arycer.permadeath.Main;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.fabricmc.fabric.api.loot.v2.LootTableEvents;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.PlacedFeature;

public class NetherModifications {
    private static final RegistryKey<PlacedFeature> LARGE_DEBRIS = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_ancient_debris_large"));
    private static final RegistryKey<PlacedFeature> SMALL_DEBRIS = RegistryKey.of(RegistryKeys.PLACED_FEATURE, new Identifier("minecraft", "ore_debris_small"));

    private static void modifyWorldGen() {
        BiomeModifications.create(new Identifier(Main.MOD_ID, "no_netherite"))
                .add(ModificationPhase.REMOVALS, BiomeSelectors.foundInTheNether(), ctx -> {
                    ctx.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, LARGE_DEBRIS);
                    ctx.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_DECORATION, SMALL_DEBRIS);
                    ctx.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, LARGE_DEBRIS);
                    ctx.getGenerationSettings().removeFeature(GenerationStep.Feature.UNDERGROUND_ORES, SMALL_DEBRIS);
                });
    }

    private static final Identifier HOGLIN_STABLE = new Identifier("minecraft", "chests/bastion_hoglin_stable");
    private static final Identifier BASTION_TREASURE = new Identifier("minecraft", "chests/bastion_treasure");
    private static final Identifier BASTION_OTHER = new Identifier("minecraft", "chests/bastion_other");

    private static final Identifier MODIFIED_HOGLIN_STABLE = new Identifier(Main.MOD_ID, "nether_modifications/bastion_hoglin_stable");
    private static final Identifier MODIFIED_BASTION_TREASURE = new Identifier(Main.MOD_ID, "nether_modifications/bastion_treasure");
    private static final Identifier MODIFIED_BASTION_OTHER = new Identifier(Main.MOD_ID, "nether_modifications/bastion_other");

    private static void modifyLootTables() {
        LootTableEvents.REPLACE.register((resourceManager, lootManager, id, tableBuilder, source) -> {
            if (id.equals(HOGLIN_STABLE)) {
                return lootManager.getTable(MODIFIED_HOGLIN_STABLE);
            } else if (id.equals(BASTION_TREASURE)) {
                return lootManager.getTable(MODIFIED_BASTION_TREASURE);
            } else if (id.equals(BASTION_OTHER)) {
                return lootManager.getTable(MODIFIED_BASTION_OTHER);
            } else {
                return null;
            }
        });
    }

    public static void register() {
        modifyWorldGen();
        modifyLootTables();
    }
}