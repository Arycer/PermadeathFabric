package com.arycer.permadeathfabric.mixin;

import com.arycer.permadeathfabric.PermadeathConfig;
import net.minecraft.entity.SpawnGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.arycer.permadeathfabric.difficultyChanges.day10.MobSpawningRules;

@Mixin(SpawnGroup.class)
public class MixinMobSpawning {
    MobSpawningRules mobSpawningRules = new MobSpawningRules();

    @Inject(method = "getCapacity", at = @At("RETURN"), cancellable = true)
    private void getCapacity(CallbackInfoReturnable<Integer> cir) {
        if (PermadeathConfig.getServerDay() >= 10) mobSpawningRules.doubleMobcap(cir);
    }
}