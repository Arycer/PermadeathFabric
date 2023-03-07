package com.arycer.permadeathfabric.mixin;

import com.arycer.permadeathfabric.PermadeathConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpiderEntity.class)
public class MixinSpiderEntity {
    com.arycer.permadeathfabric.difficultyChanges.day10.MobSpawningRules day10MobSpawningRules = new com.arycer.permadeathfabric.difficultyChanges.day10.MobSpawningRules();
    com.arycer.permadeathfabric.difficultyChanges.day20.MobSpawningRules day20MobSpawningRules = new com.arycer.permadeathfabric.difficultyChanges.day20.MobSpawningRules();

    @Inject(method="<init>", at = @At("TAIL"))
    private void addSpawnEffects(EntityType entityType, World world, CallbackInfo ci) {
        var serverDay = PermadeathConfig.getServerDay();
        if (serverDay >= 10 && serverDay < 20) day10MobSpawningRules.summonOpSpider((SpiderEntity) (Object) this);
        else if (serverDay >= 20) day20MobSpawningRules.summonOpSpider((SpiderEntity) (Object) this);
    }
}