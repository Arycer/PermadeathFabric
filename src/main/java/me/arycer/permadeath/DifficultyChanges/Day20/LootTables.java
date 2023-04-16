package me.arycer.permadeath.DifficultyChanges.Day20;

import me.arycer.permadeath.DifficultyChanges.Day25.NetheriteMobs;
import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

public class LootTables {
    private static final HashMap<EntityType<?>, Identifier> MOBS_TO_MODIFY = new HashMap<>(){{
        put(EntityType.IRON_GOLEM, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.MAGMA_CUBE, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.PHANTOM, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.ZOMBIFIED_PIGLIN, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.ENDERMAN, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.SLIME, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.GHAST, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.WITCH, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.DROWNED, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.GUARDIAN, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.WITHER_SKELETON, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.BLAZE, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.EVOKER, new Identifier(Main.MOD_ID, "empty"));
        put(EntityType.RAVAGER, new Identifier(Main.MOD_ID, "day20/ravager"));
    }};

    public static void register(CallbackInfoReturnable<Identifier> cir, MobEntity entity) {
        EntityType<?> type = entity.getType();

        if (!MOBS_TO_MODIFY.containsKey(type) || (ModConfig.getServerDay() >= 25 && NetheriteMobs.MOBS_TO_MODIFY.containsKey(type))) return;

        cir.setReturnValue(MOBS_TO_MODIFY.get(type));
    }
}
