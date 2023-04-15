package me.arycer.permadeath.DifficultyChanges.Global.mixin;

import me.arycer.permadeath.DifficultyChanges.Global.DeathHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class MixinServerPlayerEntity {
    @Inject(at = @At("HEAD"), method = "onDeath")
    public void onDeath(CallbackInfo info) {
        DeathHandler.onDeath((ServerPlayerEntity) (Object) this);
    }
}