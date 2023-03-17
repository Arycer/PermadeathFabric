package me.arycer.permadeathfabric.DifficultyChanges.Day30;

import me.arycer.permadeathfabric.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class ModifyLootTables {
    private static final Identifier EMPTY_LOOT_TABLE = new Identifier(Main.MOD_ID, "global/empty");
    private static final Identifier CAVE_SPIDER_LOOT_TABLE = new Identifier("minecraft", "entities/cave_spider");

    public static List<EntityType> ModifiedLootTable = List.of(
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.GHAST,
            EntityType.CAVE_SPIDER
    );
    public static void register(CallbackInfoReturnable<Identifier> cir, LivingEntity entity) {
        var type = entity.getType();

        if (!ModifiedLootTable.contains(type)) return;

        if (type.equals(EntityType.CAVE_SPIDER)) {
            cir.setReturnValue(CAVE_SPIDER_LOOT_TABLE);
        } else {
            cir.setReturnValue(EMPTY_LOOT_TABLE);
        }
    }
}
