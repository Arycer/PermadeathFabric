package me.arycer.permadeath.DifficultyChanges.Day20;

import me.arycer.permadeath.Main;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

public class LootTables {
    private static final List<EntityType> MOBS_TO_MODIFY = List.of(
            EntityType.IRON_GOLEM,
            EntityType.MAGMA_CUBE,
            EntityType.PHANTOM,
            EntityType.ZOMBIFIED_PIGLIN,
            EntityType.ENDERMAN,
            EntityType.SLIME,
            EntityType.GHAST,
            EntityType.WITCH,
            EntityType.DROWNED,
            EntityType.GUARDIAN,
            EntityType.WITHER_SKELETON,
            EntityType.BLAZE,
            EntityType.EVOKER
    );

    public static void register(CallbackInfoReturnable<Identifier> cir, MobEntity entity) {
        EntityType<?> type = entity.getType();

        if (!MOBS_TO_MODIFY.contains(type)) return;

        if (type.equals(EntityType.RAVAGER)) {
            cir.setReturnValue(new Identifier(Main.MOD_ID, "day20/ravager"));
        } else {
            cir.setReturnValue(new Identifier(Main.MOD_ID, "empty"));
        }
    }
}
