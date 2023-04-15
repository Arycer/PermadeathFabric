package me.arycer.permadeath.DifficultyChanges.Day1.Mixin;

import me.arycer.permadeath.DifficultyChanges.Day1.SleepingRules;
import me.arycer.permadeath.DifficultyChanges.Global.DeathTrain;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
    @Inject(at = @At("HEAD"), method = "sleep", cancellable = true)
    public void sleep(CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day >= 10) return;

        SleepingRules.announceSleep((ServerPlayerEntity) (Object) this);
        DeathTrain.deathTrainDisablesSleeping(ci, (ServerPlayerEntity) (Object) this);
    }
}
