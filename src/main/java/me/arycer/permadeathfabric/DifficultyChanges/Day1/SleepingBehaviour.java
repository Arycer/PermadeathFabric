package me.arycer.permadeathfabric.DifficultyChanges.Day1;

import me.arycer.permadeathfabric.Main;
import me.arycer.permadeathfabric.Util.ModConfig;
import me.arycer.permadeathfabric.Util.WorldUtils;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.SleepManager;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class SleepingBehaviour {
    public static void deathTrainDisablesSleeping(CallbackInfo ci, ServerPlayerEntity player) {
        ServerWorldProperties properties = WorldUtils.getProperties();

        if (!properties.isThundering()) return;

        ci.cancel();

        Text msg = Text.literal("¡No puedes dormir durante la tormenta!").setStyle(Style.EMPTY.withColor(TextColor.fromFormatting(Formatting.RED)));
        player.sendMessage(msg, false);
    }

    public static void announceSleep(ServerPlayerEntity player) {
        ServerWorldProperties properties = WorldUtils.getProperties();

        if (properties.isThundering()) return;

        int day = ModConfig.getServerDay();

        if (day < 10) {
            Text msg = player.getDisplayName().copy().append(Text.literal(" fue a dormir. ¡Dulces sueños!")
                    .setStyle(Style.EMPTY.withColor(Formatting.YELLOW)));
            Main.server.getPlayerManager().broadcast(msg, false);
        } else {
            int sleepingPlayers = Main.server.getPlayerManager().getPlayerList().stream().filter(ServerPlayerEntity::isSleeping).toArray().length + 1;
            MutableText msg = player.getDisplayName().copy().append(Text.literal(" fue a dormir. (" + sleepingPlayers + "/4)")
                    .setStyle(Style.EMPTY.withColor(Formatting.YELLOW)));

            if (sleepingPlayers >= 4) {
                Main.server.getPlayerManager().broadcast(
                        msg.append(Text.literal(" ¡Dulces sueños!")
                                .setStyle(Style.EMPTY.withColor(Formatting.YELLOW))
                        ), false
                );
            } else {
                Main.server.getPlayerManager().broadcast(msg, false);
            }
        }
    }
}
