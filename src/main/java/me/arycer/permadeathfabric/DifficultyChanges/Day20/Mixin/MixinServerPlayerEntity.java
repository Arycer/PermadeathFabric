package me.arycer.permadeathfabric.DifficultyChanges.Day20.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day20.SleepingBehaviour;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
    @Inject(method = "sleep", at = @At("HEAD"), cancellable = true)
    private void onSleep(BlockPos pos, CallbackInfo ci) {
        int day = ModConfig.getServerDay();
        if (day < 20) return;
        SleepingBehaviour.register(ci, pos, (ServerPlayerEntity) (Object) this);
    }
}