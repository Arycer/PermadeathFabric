package me.arycer.permadeathfabric.DifficultyChanges.Day30.Mixin;

import me.arycer.permadeathfabric.Util.ModConfig;
import net.minecraft.entity.SpawnGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpawnGroup.class)
public class MixinSpawnGroup {
    @Inject(method = "getCapacity", at = @At("RETURN"), cancellable = true)
    private void getCapacity(CallbackInfoReturnable<Integer> cir) {
        int day = ModConfig.getServerDay();
        if (day < 30) return;

    }
}
