package me.arycer.permadeath.DifficultyChanges.Day10.Mixin;

import me.arycer.permadeath.DifficultyChanges.Day10.SleepingRules;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.server.world.SleepManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SleepManager.class)
public class MixinSleepManager {
    @Inject(at = @At("RETURN"), method = "canSkipNight", cancellable = true)
    public void canSkipNight(CallbackInfoReturnable<Boolean> cir) {
        int day = ModConfig.getServerDay();
        if (day < 10 || day >= 20) return;
        SleepingRules.setMinSleepingPlayers(cir, (SleepManager) (Object) this);
    }
}
