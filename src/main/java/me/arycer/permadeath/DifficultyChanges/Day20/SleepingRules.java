package me.arycer.permadeath.DifficultyChanges.Day20;

import com.mojang.datafixers.util.Either;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.arycer.permadeath.Util.ServerUtil.createText;

public class SleepingRules {
    public static void sleepingDisabled(CallbackInfoReturnable<Either<PlayerEntity.SleepFailureReason, Unit>> cir, BlockPos pos, ServerPlayerEntity player) {
        cir.setReturnValue(Either.left(PlayerEntity.SleepFailureReason.OTHER_PROBLEM));

        World world = player.getWorld();
        world.createExplosion(null, pos.getX(), pos.up().getY(), pos.getZ(), 0, World.ExplosionSourceType.NONE);

        player.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));

        Text msg = createText("Contador de phantoms reseteado.", Formatting.GRAY, false);
        player.sendMessage(msg, false);
    }
}