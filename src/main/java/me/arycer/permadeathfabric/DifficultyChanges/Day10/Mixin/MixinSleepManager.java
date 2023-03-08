package me.arycer.permadeathfabric.DifficultyChanges.Day10.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Day10.SetMinSleepingPlayers;
import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.server.world.SleepManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SleepManager.class)
public class MixinSleepManager {
    @Inject(method = "canSkipNight", at = @At("RETURN"), cancellable = true)
    private void canSkipNight(int percentage, CallbackInfoReturnable<Boolean> cir) {
        int day = ModConfig.getServerDay();
        if (day < 10 || day >= 20) return;
        SetMinSleepingPlayers.register(cir, (SleepManager) (Object) this);
    }
}