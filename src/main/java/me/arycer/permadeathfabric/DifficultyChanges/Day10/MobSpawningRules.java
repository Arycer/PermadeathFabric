package me.arycer.permadeathfabric.DifficultyChanges.Day10;

import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class MobSpawningRules {
    public static void doubleMobcap(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 2);
    }
}