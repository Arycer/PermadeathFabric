package me.arycer.permadeathfabric.DifficultyChanges.Global.Mixin;

import me.arycer.permadeathfabric.DifficultyChanges.Global.DeathHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity {
    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(CallbackInfo info) {
        DeathHandler.register((ServerPlayerEntity) (Object) this);
    }
}