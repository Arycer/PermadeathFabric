package me.arycer.permadeath.DifficultyChanges.Day30;

import me.arycer.permadeath.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

public class LootTables {
    private static final HashMap<EntityType<?>, Identifier> MOBS_TO_MODIFY = new HashMap<>(){{
        put(EntityType.SLIME, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.MAGMA_CUBE, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.GHAST, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.CAVE_SPIDER, new Identifier("minecraft", "entities/cave_spider"));
    }};

    public static void register(CallbackInfoReturnable<Identifier> cir, MobEntity entity) {
        EntityType<?> type = entity.getType();

        if (!MOBS_TO_MODIFY.containsKey(type)) return;

        cir.setReturnValue(MOBS_TO_MODIFY.get(type));
    }
}
