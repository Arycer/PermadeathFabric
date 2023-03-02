package com.arycer.permadeathfabric.mixin;

import com.arycer.permadeathfabric.PermadeathConfig;
import net.minecraft.server.world.SleepManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.arycer.permadeathfabric.difficultyChanges.day10.SleepingBehaviour;

@Mixin(SleepManager.class)
public class MixinSleepManager {
    SleepingBehaviour sleepingBehaviour = new SleepingBehaviour();
    @Inject(method = "canSkipNight", at = @At("RETURN"), cancellable = true)
    private void trySleep(int percentage, CallbackInfoReturnable<Boolean> cir) {
        // Day 10
        if (PermadeathConfig.getServerDay() >= 10 && PermadeathConfig.getServerDay() < 20) sleepingBehaviour.setMinSleepingPlayers(cir, (SleepManager) (Object) this);
    }
}