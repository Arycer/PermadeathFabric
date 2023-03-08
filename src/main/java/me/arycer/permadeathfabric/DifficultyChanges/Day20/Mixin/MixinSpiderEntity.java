package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day20.CustomEntities.OpSpiderJockey;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public class MixinSpiderEntity {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void init (EntityType entityType, World world, CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 20) return;
        OpSpiderJockey.summon((SpiderEntity) (Object) this);
    }

}
