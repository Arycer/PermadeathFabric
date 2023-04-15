package me.arycer.permadeath.DifficultyChanges.Global.mixin;

import me.arycer.permadeath.DifficultyChanges.Global.DeathTrain;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class MixinServerWorld {
    @Inject(at = @At("HEAD"), method = "tickTime")
    public void tickTime(CallbackInfo info) {
        DeathTrain.naturalRegenerationManager();
        DeathTrain.timer();
    }
}
