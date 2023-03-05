package com.arycer.permadeathfabric;

import me.lortseam.completeconfig.api.ConfigEntry;
import me.lortseam.completeconfig.data.Config;
import me.lortseam.completeconfig.data.ConfigOptions;

//import logger
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PermadeathConfig extends Config {
    public PermadeathConfig() {
        super(ConfigOptions
                .mod("permadeathfabric")
                .fileHeader("PermadeathFabric configuration")
        );
    }

    private static final Logger LOGGER = LogManager.getLogger("PermadeathFabric");

    @ConfigEntry // Date epoch of file creation
    private static long serverStartTimestamp = System.currentTimeMillis();

    @ConfigEntry // Ban players who die
    private static boolean banOnDeath = true;

    public static int getServerDay() {
        var epoch = System.currentTimeMillis() - serverStartTimestamp;
        var day = (int) (epoch / 86400000);
        return day + 1;
    }

    public static void setInitialTime(long time) {
        serverStartTimestamp = time;
    }

    public static boolean getBanOnDeath() {
        return banOnDeath;
    }

    public static void setBanOnDeath(boolean ban) {
        banOnDeath = ban;
    }

    public static void reload() {
        LOGGER.info("Reloading config");
        Initializer.config.save();
        Initializer.config.load();
    }
}
