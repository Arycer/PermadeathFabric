package com.arycer.permadeathfabric.mixin;

import com.arycer.permadeathfabric.PermadeathConfig;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity {
    // Day 1
    com.arycer.permadeathfabric.difficultyChanges.day1.DeathHandler deathHandler = new com.arycer.permadeathfabric.difficultyChanges.day1.DeathHandler();
    com.arycer.permadeathfabric.difficultyChanges.day1.SleepingBehaviour day1SleepingBehaviour = new com.arycer.permadeathfabric.difficultyChanges.day1.SleepingBehaviour();

    // Day 20
    com.arycer.permadeathfabric.difficultyChanges.day20.SleepingBehaviour day20SleepingBehaviour = new com.arycer.permadeathfabric.difficultyChanges.day20.SleepingBehaviour();

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(CallbackInfo info) {
        deathHandler.playerDeath((ServerPlayerEntity) (Object) this);
    }


    @Inject(method = "sleep", at = @At("HEAD"), cancellable = true)
    public void sleep(BlockPos pos, CallbackInfo ci) {
        if (PermadeathConfig.getServerDay() < 20) {
            day1SleepingBehaviour.announceSleep((ServerPlayerEntity) (Object) this);
            day1SleepingBehaviour.deathTrainSleepDisabled(ci, (ServerPlayerEntity) (Object) this);
        } else if (PermadeathConfig.getServerDay() >= 20) {
            day20SleepingBehaviour.sleepingDisabled(ci, pos, (ServerPlayerEntity) (Object) this);
        }

    }

}