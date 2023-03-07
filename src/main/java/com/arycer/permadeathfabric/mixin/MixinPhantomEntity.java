package com.arycer.permadeathfabric.mixin;

import com.arycer.permadeathfabric.PermadeathConfig;
import net.minecraft.entity.mob.PhantomEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PhantomEntity.class)
public class MixinPhantomEntity {
    com.arycer.permadeathfabric.difficultyChanges.day20.MobSpawningRules day20MobSpawningRules = new com.arycer.permadeathfabric.difficultyChanges.day20.MobSpawningRules();
    @Inject(method = "<init>", at = @At("TAIL"))
    private void register(CallbackInfo ci) {
        var serverDay = PermadeathConfig.getServerDay();
        if (serverDay >= 20) day20MobSpawningRules.summonOpPhantom((PhantomEntity) (Object) this);
    }
}
