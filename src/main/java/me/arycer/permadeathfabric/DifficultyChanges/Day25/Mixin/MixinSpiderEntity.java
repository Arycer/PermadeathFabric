package me.arycer.permadeathfabric.DifficultyChanges.Day25.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day25.CustomEntities.OpSpiderJockey;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.mob.SpiderEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public class MixinSpiderEntity {
    @Inject(method = "<init>", at = @At("RETURN"))
    private void onInit(CallbackInfo info) {
        int day = ModConfig.getServerDay();
        if (day < 25) return;
        SpiderEntity spider = (SpiderEntity) (Object) this;
        OpSpiderJockey.summon(spider);
    }
}