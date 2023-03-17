package me.arycer.permadeathfabric.DifficultyChanges.Day25;

import me.arycer.permadeathfabric.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class ModifyLootTables {
    private static final Identifier GIGASLIME_LOOT_TABLE = new Identifier(Main.MOD_ID, "day25/gigaslime");
    private static final Identifier GIGAMAGMACUBE_LOOT_TABLE = new Identifier(Main.MOD_ID, "day25/gigamagmacube");
    private static final Identifier GHASTDEMON_LOOT_TABLE = new Identifier(Main.MOD_ID, "day25/ghastdemon");
    private static final Identifier NETHERITE_CAVE_SPIDER_LOOT_TABLE = new Identifier(Main.MOD_ID, "day25/netheritecavespider");
    private static final Identifier RAVAGER_DAY25_LOOT_TABLE = new Identifier(Main.MOD_ID, "day25/ravager");

    public static List<EntityType> ModifiedLootTable = List.of(
            EntityType.SLIME,
            EntityType.MAGMA_CUBE,
            EntityType.GHAST,
            EntityType.CAVE_SPIDER,
            EntityType.RAVAGER
    );
    public static void register(CallbackInfoReturnable<Identifier> cir, LivingEntity entity) {
        var type = entity.getType();
        if (type.equals(EntityType.SLIME)) {
            cir.setReturnValue(GIGASLIME_LOOT_TABLE);
        } else if (type.equals(EntityType.MAGMA_CUBE)) {
            cir.setReturnValue(GIGAMAGMACUBE_LOOT_TABLE);
        } else if (type.equals(EntityType.GHAST)) {
            cir.setReturnValue(GHASTDEMON_LOOT_TABLE);
        } else if (type.equals(EntityType.CAVE_SPIDER)) {
            cir.setReturnValue(NETHERITE_CAVE_SPIDER_LOOT_TABLE);
        } else if (type.equals(EntityType.RAVAGER)) {
            cir.setReturnValue(RAVAGER_DAY25_LOOT_TABLE);
        }
    }
}
