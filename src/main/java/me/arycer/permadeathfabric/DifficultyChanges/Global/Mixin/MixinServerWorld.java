package me.arycer.permadeathfabric.DifficultyChanges.Global.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Global.DeathTrain;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class MixinServerWorld {
    @Inject(method = "tick", at = @At("TAIL"))
    private void register(CallbackInfo ci) {
        DeathTrain.naturalRegenerationManager();
        DeathTrain.deathTrainTimer();
    }
}