package me.arycer.permadeath.Util;

import me.arycer.permadeath.Main;
import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

public class ModConfig extends Config {
    public ModConfig() {
        super(ConfigOptions
                .mod(Main.MOD_ID)
                .fileHeader("Permadeath Config")
        );
    }

    public static void reload () {
        Main.config.save();
        Main.config.load();
    }

    @ConfigEntry
    private static long CREATION_TIME = System.currentTimeMillis();

    @ConfigEntry
    private static boolean BAN_ON_DEATH = true;

    @ConfigEntry
    private static boolean OP_BAN_IMMUNITY = true;

    public static int getServerDay() {
        long epoch = System.currentTimeMillis() - CREATION_TIME;
        int day = (int) (epoch / 86400000);
        return day + 1;
    }

    public static boolean getBanOnDeath () {
        return BAN_ON_DEATH;
    }

    public static boolean getOpBanImmunity () {
        return OP_BAN_IMMUNITY;
    }

    public static void setServerDay (int day) {
        CREATION_TIME = System.currentTimeMillis() - ((long) (day - 1) * 24 * 60 * 60 * 1000);
        reload();
    }

    public static void setBanOnDeath (boolean ban) {
        BAN_ON_DEATH = ban;
        reload();
    }

    public static void setOpBanImmunity (boolean opImmunity) {
        OP_BAN_IMMUNITY = opImmunity;
        reload();
    }
}