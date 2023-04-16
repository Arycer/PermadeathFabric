package me.arycer.permadeath.DifficultyChanges.Day25;

import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;

public class NetheriteMobs {
    public static final HashMap<EntityType<?>, Identifier> MOBS_TO_MODIFY = new HashMap<>(){{
        put(EntityType.SLIME, new Identifier(Main.MOD_ID, "day25/gigaslime"));
        put(EntityType.MAGMA_CUBE, new Identifier(Main.MOD_ID, "day25/gigamagmacube"));
        put(EntityType.GHAST, new Identifier(Main.MOD_ID, "day25/ghastdemon"));
        put(EntityType.CAVE_SPIDER, new Identifier(Main.MOD_ID, "day25/netheritecavespider"));
        put(EntityType.RAVAGER, new Identifier(Main.MOD_ID, "day25/ravager"));
    }};

    public static void register (CallbackInfoReturnable<Identifier> cir, MobEntity entity) {
        EntityType<?> type = entity.getType();

        if (!MOBS_TO_MODIFY.containsKey(type) || (ModConfig.getServerDay() >= 30 && !type.equals(EntityType.RAVAGER))) return;

        cir.setReturnValue(MOBS_TO_MODIFY.get(type));
    }

}
