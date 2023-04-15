package me.arycer.permadeath.DifficultyChanges.Day20.Mixin;

import com.mojang.datafixers.util.Either;
import me.arycer.permadeath.DifficultyChanges.Day20.SleepingRules;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerEntity.class)
public class MixinServerPlayerEntity {
    @Inject(at = @At("HEAD"), method = "trySleep", cancellable = true)
    public void trySleep(BlockPos pos, CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir) {
        int day = ModConfig.getServerDay();
        if (day < 20) return;

        SleepingRules.sleepingDisabled(cir, pos, (ServerPlayerEntity) (Object) this);
    }
}