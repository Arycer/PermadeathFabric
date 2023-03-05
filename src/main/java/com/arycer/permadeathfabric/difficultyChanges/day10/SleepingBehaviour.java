package com.arycer.permadeathfabric.difficultyChanges.day10;

import com.arycer.permadeathfabric.Initializer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.SleepManager;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class SleepingBehaviour {
    public void setMinSleepingPlayers(CallbackInfoReturnable<Boolean> cir, SleepManager sleepManager) {
        if (!cir.getReturnValue()) return;

        MinecraftServer server = Initializer.server;

        var playerSleepingCount = sleepManager.getSleeping();

        if (playerSleepingCount < 4) cir.setReturnValue(false);
        var msg = Text.literal("No se puede saltar la noche con menos de 4 jugadores durmiendo").setStyle(Style.EMPTY.withColor(Formatting.GRAY));

        // send the message to all players sleeping
        server.getPlayerManager().getPlayerList().stream()
                .filter(LivingEntity::isSleeping)
                .forEach(player -> player.sendMessage(msg, true));
    }
}
