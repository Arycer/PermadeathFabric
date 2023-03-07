package com.arycer.permadeathfabric.mixin;

import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.arycer.permadeathfabric.PermadeathConfig;

@Mixin(MobEntity.class)
public abstract class MixinMobEntity {
    com.arycer.permadeathfabric.difficultyChanges.day20.LootTableModifiers day20LootTableModifiers = new com.arycer.permadeathfabric.difficultyChanges.day20.LootTableModifiers();

    @Inject(method = "getLootTable", at = @At("RETURN"), cancellable = true)
    private void register(CallbackInfoReturnable<Identifier> cir) {
        var serverDay = PermadeathConfig.getServerDay();
        if (serverDay >= 20) day20LootTableModifiers.register(cir, (MobEntity) (Object) this);
    }
}