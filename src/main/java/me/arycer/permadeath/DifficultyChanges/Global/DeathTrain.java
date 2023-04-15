package me.arycer.permadeath.DifficultyChanges.Global;

import me.arycer.permadeath.Main;
import me.arycer.permadeath.Util.ModConfig;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.ServerWorldProperties;

import static me.arycer.permadeath.Util.ServerUtil.*;

public class DeathTrain {
    private static long stormTicks, stormHours;

    private static void loadTicks() {
        int day = ModConfig.getServerDay();

        if (day < 25) {
            stormTicks = 3600L * 20L * day;
        } else if (day < 50) {
            int dayOffset = day - 24;
            stormTicks = 3600L * 20L * dayOffset;
        } else if (day == 50) {
            stormTicks = 1800L * 20L;
            stormHours = 0;
            return;
        } else {
            int dayOffset = day - 49;
            stormTicks = 1800L * 20L * dayOffset;
        }
        stormHours = stormTicks / 20L / 3600L;
    }

    public static void startDeathTrain() {
        ServerWorldProperties properties = getServerWorldProperties();
        ServerWorld overworld = getOverworld();
        loadTicks();

        if (!properties.isThundering()) properties.setThunderTime(0);
        overworld.setWeather(0, (int) stormTicks + properties.getThunderTime(), true, true);

        Text msg = createText(String.format("¡Comienza el Death Train con duración de %d %s!", stormHours, stormHours == 1 ? "hora" : "horas"), Formatting.RED, false);
        Main.server.getPlayerManager().broadcast(msg, false);

        int day = ModConfig.getServerDay();
        if (day < 50) return;

        Text msg2 = createText("¡Ha comenzado el modo UHC!", Formatting.YELLOW, false);
        Main.server.getPlayerManager().broadcast(msg2, false);
    }

    public static void timer() {
        ServerWorldProperties properties = getServerWorldProperties();

        if (!properties.isThundering()) return;

        Text msg;
        if (properties.getThunderTime() > 1) {
            msg = createText(String.format("Quedan %s de tormenta.", ticksToTime(properties.getThunderTime())), Formatting.GRAY, false);
        } else {
            msg = createText("La tormenta ha cesado.", Formatting.GRAY, false);
        }

        Main.server.getPlayerManager().getPlayerList().forEach(player -> player.sendMessage(msg, true));
    }

    public static void naturalRegenerationManager() {
        ServerWorldProperties properties = getServerWorldProperties();
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