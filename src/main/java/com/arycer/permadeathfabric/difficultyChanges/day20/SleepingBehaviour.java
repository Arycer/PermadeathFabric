package com.arycer.permadeathfabric.difficultyChanges.day20;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class SleepingBehaviour {
    public void sleepingDisabled(CallbackInfo ci, BlockPos pos, ServerPlayerEntity player) {
        ci.cancel();

        var world = player.getWorld();
        world.createExplosion(null, pos.getX(), pos.up().getY(), pos.getZ(), 0, World.ExplosionSourceType.NONE);

        // reset the phantom timer of the player
        player.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));

        var msg = Text.literal("Contador de phantoms reiniciado"); // Create the message
        msg = msg.setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.GRAY))); // Set the message's style

        player.sendMessage(msg, false);
    }

}
