package com.arycer.permadeathfabric.mixin;

import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.arycer.permadeathfabric.difficultyChanges.day1.*;

@Mixin(net.minecraft.server.world.ServerWorld.class)
public class MixinServerWorld {
    DeathHandler deathHandler = new DeathHandler();
    @Inject(method = "tick", at = @At("TAIL"))
    private void onTick(CallbackInfo ci) {
        deathHandler.DeathTrainTimer((ServerWorld) (Object) this);
    }
}