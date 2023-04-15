package me.arycer.permadeath.DifficultyChanges.Day10.Mixin;

import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.SpawnGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnGroup.class)
public class MixinSpawnGroup {
    @Inject(at = @At("RETURN"), method = "getCapacity", cancellable = true)
    public void getCapacity(CallbackInfoReturnable<Integer> cir) {
        int day = ModConfig.getServerDay();
        if (day < 10) return;
        cir.setReturnValue(cir.getReturnValue() * 2);
    }
}
