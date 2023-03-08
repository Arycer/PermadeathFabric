package me.arycer.permadeathfabric.DifficultyChanges.Global;

import me.arycer.permadeathfabric.Main;
import me.arycer.permadeathfabric.Util.ModConfig;
import me.arycer.permadeathfabric.Util.WorldUtils;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.ServerWorldProperties;

public class DeathTrain {
    static long stormTicks, stormHours;
    private static void loadTicks() {
        int day = ModConfig.getServerDay();

        if (day < 25) {
            stormTicks = 3600L * 20L * day;
            stormHours = stormTicks / 20L / 60L / 60L;
        } else if (day < 50) {
            int dayOffset = day - 24;
            stormTicks = 3600L * 20L * dayOffset;
            stormHours = stormTicks / 20L / 60L / 60L;
        } else if (day == 50) {
            stormTicks = 1800L * 20L;
            stormHours = 0;
        } else {
            int dayOffset = day - 49;
            stormTicks = 1800L * 20L * dayOffset;
            stormHours = stormTicks / 20L / 60L / 60L;
        }
    }
    public static void startDeathTrain() {
        ServerWorldProperties properties = WorldUtils.getProperties();
        ServerWorld world = WorldUtils.getOverworld();

        if (!properties.isThundering()) properties.setThunderTime(0);

        loadTicks();
        world.setWeather(0, (int) stormTicks + properties.getThunderTime(), true, true);

        Text msg = Text.literal("¡Comienza el Death Train con duración de " + stormHours + " hora" + (stormHours == 1 ? "!" : "s!"))
                .setStyle(Style.EMPTY.withColor(Formatting.RED));

        Main.server.getPlayerManager().broadcast(msg, false);

        int day = ModConfig.getServerDay();
        if (day < 50) return;

        Text msg2 = Text.literal("¡Ha comenzado el modo UHC!")
                .setStyle(Style.EMPTY.withColor(Formatting.YELLOW));

        Main.server.getPlayerManager().broadcast(msg2, false);
    }

    public static void deathTrainTimer() {
        ServerWorldProperties properties = WorldUtils.getProperties();

        if (!properties.isThundering()) return;

        Text msg = Text.literal("Quedan " + ticksToTime(properties.getThunderTime()) + " de tormenta")
                .setStyle(Style.EMPTY.withColor(Formatting.GRAY));

        Main.server.getPlayerManager().getPlayerList().forEach(p -> p.sendMessage(msg, true));
    }

    public static void naturalRegenerationManager() {
        ServerWorldProperties properties = WorldUtils.getProperties();
        int day = ModConfig.getServerDay();

        if (day < 50) {
            Main.server.getGameRules().get(GameRules.NATURAL_REGENERATION).set(true, Main.server);
        } else {
            Main.server.getGameRules().get(GameRules.NATURAL_REGENERATION).set(!properties.isThundering(), Main.server);
        }
    }

    private static String ticksToTime(long ticks) {
        int seconds = (int) (ticks / 20);
        int minutes = seconds / 60;
        int hours = minutes / 60;

        seconds %= 60;
        minutes %= 60;

        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
