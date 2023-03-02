package com.arycer.permadeathfabric.mixin;

import com.arycer.permadeathfabric.PermadeathConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.arycer.permadeathfabric.difficultyChanges.day10.MobSpawningRules;

@Mixin(SpiderEntity.class)
public class MixinSpiderEffects {
    MobSpawningRules mobSpawningRules = new MobSpawningRules();

    @Inject(method="<init>", at = @At("TAIL"))
    private void addSpawnEffects(EntityType entityType, World world, CallbackInfo ci) {
        if (PermadeathConfig.getServerDay() >= 10) mobSpawningRules.summonOpSpider((SpiderEntity) (Object) this);
    }
}