package me.arycer.permadeath.DifficultyChanges.Day10;

import me.arycer.permadeath.Main;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.SleepManager;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static me.arycer.permadeath.Util.ServerUtil.createText;
import static me.arycer.permadeath.Util.ServerUtil.getServerWorldProperties;

public class SleepingRules {
    public static void setMinSleepingPlayers(CallbackInfoReturnable<Boolean> cir, SleepManager manager) {
        if (!cir.getReturnValue()) return;

        int sleepingPlayers = manager.getSleeping();

        Text msg;
        if (sleepingPlayers < 4) {
            cir.setReturnValue(false);
            msg = createText("No se puede saltar la noche con menos de 4 jugadores durmiendo.", Formatting.GRAY, false);
        } else {
            msg = createText("¡Dulces sueños!", Formatting.GRAY, false);
        }

        Main.server.getPlayerManager().getPlayerList().stream()
                .filter(LivingEntity::isSleeping)
                .forEach(p -> p.sendMessage(msg, false));
    }

    public static void announceSleep(ServerPlayerEntity player) {
        ServerWorldProperties properties = getServerWorldProperties();
        if (properties.isThundering()) return;

        int sleepingPlayers = Main.server.getPlayerManager().getPlayerList().stream()
                .filter(LivingEntity::isSleeping)
                .toArray().length + 1;

        Text msg = player.getDisplayName().copy().append(createText(String.format(" fue a dormir. (%d/4)", sleepingPlayers), Formatting.YELLOW, false));
        if (sleepingPlayers >= 4) {
            msg = msg.copy().append(createText(" ¡Dulces sueños!", Formatting.YELLOW, false));
        }

        Main.server.getPlayerManager().broadcast(msg, false);
    }
}