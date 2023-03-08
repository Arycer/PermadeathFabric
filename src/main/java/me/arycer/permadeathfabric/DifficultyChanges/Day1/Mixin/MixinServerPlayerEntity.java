package me.arycer.permadeathfabric.DifficultyChanges.Day1.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day1.SleepingBehaviour;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity {
    @Inject(method = "sleep", at = @At("HEAD"), cancellable = true)
    public void sleep(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day >= 20) return;
        SleepingBehaviour.announceSleep((ServerPlayerEntity) (Object) this);
        SleepingBehaviour.deathTrainDisablesSleeping(ci, (ServerPlayerEntity) (Object) this);
    }
}
