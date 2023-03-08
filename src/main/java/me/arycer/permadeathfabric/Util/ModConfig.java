package me.arycer.permadeathfabric.Util;

import me.arycer.permadeathfabric.Main;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

public class ModConfig extends Config {
    public ModConfig() {
        super(ConfigOptions
                .mod("permadeathfabric")
                .fileHeader("PermadeathFabric configuration")
        );
    }

    @ConfigEntry // Date epoch of file creation
    private static long serverStartTimestamp = System.currentTimeMillis();

    @ConfigEntry // Ban players who die
    private static boolean banOnDeath = true;

    public static int getServerDay() {
        var epoch = System.currentTimeMillis() - serverStartTimestamp;
        var day = (int) (epoch / 86400000);
        return day + 1;
    }

    public static boolean getBanOnDeath() {
        return banOnDeath;
    }

    public static void setStartDay(int day) {
        serverStartTimestamp = System.currentTimeMillis() - ((long) (day - 1) * 24 * 60 * 60 * 1000);
        reload();
    }

    public static void setBanOnDeath(boolean ban) {
        banOnDeath = ban;
        reload();
    }

    public static void reload() {
        Main.config.save();
        Main.config.load();
    }
}