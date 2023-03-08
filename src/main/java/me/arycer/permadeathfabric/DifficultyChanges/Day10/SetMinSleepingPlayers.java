package me.arycer.permadeathfabric.DifficultyChanges.Day10;

import me.arycer.permadeathfabric.Main;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.SleepManager;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class SetMinSleepingPlayers {
    public static void register(CallbackInfoReturnable<Boolean> cir, SleepManager sleepManager) {
        if (!cir.getReturnValue()) return;

        int sleepingPlayers = sleepManager.getSleeping();

        Text msg;
        if (sleepingPlayers < 4) {
            cir.setReturnValue(false);
            msg = Text.literal("No se puede saltar la noche con menos de 4 jugadores durmiendo")
                    .setStyle(Style.EMPTY.withColor(Formatting.GRAY));
        } else {
            msg = Text.literal("¡Dulces sueños!").setStyle(Style.EMPTY.withColor(Formatting.GRAY));
        }

        // send the message to all players sleeping
        Text finalMsg = msg;
        Main.server.getPlayerManager().getPlayerList().stream()
                .filter(LivingEntity::isSleeping)
                .forEach(player -> player.sendMessage(finalMsg, true));
    }
}