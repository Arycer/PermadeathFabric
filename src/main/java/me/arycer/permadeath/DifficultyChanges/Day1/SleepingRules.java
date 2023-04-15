package me.arycer.permadeath.DifficultyChanges.Day1;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.ServerWorldProperties;

import java.util.Objects;

import static me.arycer.permadeath.Util.ServerUtil.createText;
import static me.arycer.permadeath.Util.ServerUtil.getServerWorldProperties;

public class SleepingRules {
    public static void announceSleep(ServerPlayerEntity player) {
        MinecraftServer server = Objects.requireNonNull(player.getServer());
        ServerWorldProperties properties = getServerWorldProperties();

        if (properties.isThundering()) return;

        Text msg = player.getDisplayName().copy().append(createText(" fue a dormir. ¡Dulces sueños!", Formatting.YELLOW, false));
        server.getPlayerManager().broadcast(msg, false);
    }
}